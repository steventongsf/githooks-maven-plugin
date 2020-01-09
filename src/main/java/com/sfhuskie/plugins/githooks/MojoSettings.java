package com.sfhuskie.plugins.githooks;
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * @author Steven Tong
 * 
 */
import java.io.File;
import java.io.IOException;

import com.sfhuskie.plugins.githooks.io.GitFolderFinder;

public class MojoSettings {
    public static final String PRECOMMIT = "pre-commit";
    public static final String PREPUSH = "pre-push";
    public static final String HOOKS = PRECOMMIT+","+PREPUSH;
    public static final String CHECKSTYLE = "check-style";
    public static final String default_checkstyle_url = "https://github.com/checkstyle/checkstyle/releases/download/checkstyle-8.27/checkstyle-8.27-all.jar";
    public static final String default_checkstyle_xml_url = "https://github.com/sfhuskie/tools-configurations/blob/master/checkstyle/java/whitespace.xml";
    public static final File userDir = new File(System.getProperty("user.dir"));
    public static final String hooksRelativeDir = "/hooks";
    String gitMetadataFolder = ".git";
    File gitDir;
    File hooksDir;
    File rootDir;
    File toolsBaseDir;

    static MojoSettings mojoSettings = null;
    
    public static MojoSettings getInstance() throws IOException {
        if (mojoSettings == null) {
            mojoSettings = new MojoSettings();
        }
        return mojoSettings;
    }
    
    private MojoSettings() throws IOException {
        gitDir = new File(rootDir+"/"+this.gitMetadataFolder);
        hooksDir = new File(gitDir.getCanonicalPath()+"/"+this.hooksRelativeDir);
        if (!gitDir.exists()) {
            // Find git directory. If not found, an exception is thrown
            GitFolderFinder gff = new GitFolderFinder(new File(userDir.getAbsolutePath()));
            this.gitDir =  gff.find();
            this.hooksDir = new File(gitDir.getCanonicalPath()+hooksRelativeDir);
        }
        this.toolsBaseDir = new File(this.getHooksDir().getCanonicalPath()+"/tools");
    }
    /**
     * @param s
     */
    public void overrideGitMetadataFolder(String s) {
        this.gitMetadataFolder = s;
    }
    public File getGitDir() {
        return gitDir;
    }
    public File getHooksDir() {
        return hooksDir;
    }
    public File getToolsBaseDir() {
        return toolsBaseDir;
    }
    /**
     * @param file
     * @return
     * @throws IOException
     */
    public static String getCommandToAdd(File file) throws IOException {
        MojoSettings s = new MojoSettings();
        // TODO Add argument for configuration file
        return "bash "+file.getCanonicalPath();
    }

}
