package com.sfhuskie.plugins.githooks.io;
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

import org.apache.maven.shared.utils.io.FileUtils;

import com.sfhuskie.plugins.githooks.MojoSettings;

public class GitHooksDeployer {
    MojoSettings settings;
    File gitDir = null;
    File hooksDir = null;
    String gitMetadataFolder = ".git";
    public GitHooksDeployer(String rootDir) throws IOException {
        this.settings = new MojoSettings();
        gitDir = new File(rootDir+"/"+this.gitMetadataFolder);
        if (gitDir.exists()) {
            // Found
            return;
        }
        // Find git directory. If not found, an exception is thrown
        GitFolderFinder gff = new GitFolderFinder(new File(rootDir));
        gitDir =  gff.find();
        hooksDir = new File(gitDir.getCanonicalPath()+this.settings.hooksRelativeDir);
    }
    public void overrideGitMetadataFolder(String s) {
        this.gitMetadataFolder = s;
    }

    public void deploy() throws IOException {
        File srcDir = this.settings.getHooksSourceDirectory();
        List<File> sourceFiles = Arrays.asList(srcDir.listFiles());
        for (File srcFile: sourceFiles) {
            this.processHookFile(srcFile);
        }
    }
    public void processHookFile(File file) throws IOException {
        File targetFile = new File(this.hooksDir.getCanonicalPath()+"/"+file.getName());
        // source exist in target?
        if (targetFile.exists()) {
            // TODO if so, append
            
        }
        else {
            // if not, copy
            FileUtils.copyFile(file, targetFile);
        }
        targetFile = new File(targetFile.getCanonicalPath());
        // make executable
        if (!targetFile.canExecute()) {
            targetFile.setExecutable(true);
        }
    }
}
