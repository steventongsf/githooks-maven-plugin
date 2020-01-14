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
import java.util.Arrays;
import java.util.List;

import com.sfhuskie.plugins.githooks.io.GitFolderFinder;

public class MojoSettings {
    public static final String PRECOMMIT = "pre-commit";
    public static final String PREPUSH = "pre-push";
    public static final String HOOKS = PRECOMMIT+","+PREPUSH;
    public static final String CHECKSTYLE = "check-style";
    public static final String TOOLS = CHECKSTYLE;
    public static final String default_checkstyle_url = "https://github.com/checkstyle/checkstyle/releases/download/checkstyle-8.27/checkstyle-8.27-all.jar";
    public static final String default_checkstyle_xml_url = "https://raw.githubusercontent.com/sfhuskie/tools-configurations/master/checkstyle/java/whitespace.xml";
    public static final File userDir = new File(System.getProperty("user.dir"));
    public static final String hooksRelativeDir = "/hooks";
    public static final String checkstyleJar = "checkstyle.jar";
    public static final String checkstyleXml = "whitespace.xml";
    String gitMetadataFolder = ".git";
    File gitDir = null;
    File hooksDir;
    File rootDir;
    File toolsBaseDir;
    List<String> hooksList = Arrays.asList(HOOKS.split(","));
    List<String> tools = Arrays.asList(TOOLS.split(","));
    String checkstyle_url = default_checkstyle_url;
    String checkstyle_xml_url = default_checkstyle_xml_url;

    static MojoSettings mojoSettings = null;
    
    public static MojoSettings getInstance() throws IOException {
        if (mojoSettings == null) {
            mojoSettings = new MojoSettings();
        }
        return mojoSettings;
    }
    
    private MojoSettings() throws IOException {
    }
    /**
     * @param s
     */
    public void overrideGitMetadataFolder(String s) {
        this.gitMetadataFolder = s;
    }
    public File getGitDir() throws IOException {
        this.initGitDirs();
        return gitDir;
    }
    protected void initGitDirs() throws IOException {
        if (this.gitDir == null) {
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
    }
    public File getHooksDir() throws IOException {
        this.initGitDirs();
        return hooksDir;
    }
    public File getToolsBaseDir() throws IOException {
        this.initGitDirs();
        return toolsBaseDir;
    }
    
    public List<String> getHooks() {
        return this.hooksList;
    }

    public void setHooks(List<String> hooks) {
        this.hooksList = hooks;
    }

    public List<String> getTools() {
        return tools;
    }

    public void setTools(List<String> tools) {
        this.tools = tools;
    }

    public String getCheckstyleUrl() {
        return checkstyle_url;
    }

    public void setCheckstyleUrl(String checkstyle_url) {
        this.checkstyle_url = checkstyle_url;
    }

    public String getCheckstyleXmlUrl() {
        return checkstyle_xml_url;
    }

    public void setCheckstyleXmlUrl(String checkstyle_xml_url) {
        this.checkstyle_xml_url = checkstyle_xml_url;
    }

    /**  Create command to add to actual script that is executed by the git hook script
     * @param script  Script path name 
     * @return
     * @throws IOException
     */
    public static String getCommandToCallScript(File script) throws IOException {
        MojoSettings s = new MojoSettings();
        // TODO Add argument for configuration file
        String scriptPath = script.getCanonicalPath();
        scriptPath = scriptPath.replace("\\", "/");
        String cmd = String.format("%s %s","bash", scriptPath);
        return cmd;
    }
    /** Create command for adding to deployed git hook script
     * @param file  Script path name to execute from hook script
     * @return
     * @throws IOException
     */
    public static String getCheckstyleCommandToAdd() throws IOException {
        MojoSettings s = new MojoSettings();
        // Add argument for configuration file
        String jar = s.getHooksDir()+"/tools/"+s.CHECKSTYLE+"/"+checkstyleJar;
        String xml = s.getHooksDir()+"/tools/"+s.CHECKSTYLE+"/"+checkstyleXml;
        jar = jar.replace("\\", "/");
        xml = xml.replace("\\", "/");
        String cmd = String.format("$JAVACMD -jar %s -c %s .",jar, xml);
        return cmd;
    }
}
