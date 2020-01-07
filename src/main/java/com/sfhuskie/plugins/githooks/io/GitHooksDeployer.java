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

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.shared.utils.io.FileUtils;

import com.sfhuskie.plugins.githooks.MojoSettings;

public class GitHooksDeployer {
    MojoSettings settings;
    File gitDir = null;
    File hooksDir = null;
    File targetDir = null;
    String gitMetadataFolder = ".git";
    Log logger;
    /**
     * @param rootDir
     * @throws IOException
     */
    public GitHooksDeployer(Log logger, File rootDir, File targetDir) throws IOException {
        this.logger = logger;
        this.targetDir = targetDir;
        this.settings = new MojoSettings();
        gitDir = new File(rootDir+"/"+this.gitMetadataFolder);
        hooksDir = new File(gitDir.getCanonicalPath()+this.settings.hooksRelativeDir);
        if (!gitDir.exists()) {
            // Find git directory. If not found, an exception is thrown
            GitFolderFinder gff = new GitFolderFinder(new File(rootDir.getAbsolutePath()));
            gitDir =  gff.find();
            hooksDir = new File(gitDir.getCanonicalPath()+this.settings.hooksRelativeDir);
        }
        logger.info("gitDir: "+gitDir.getAbsolutePath());
        logger.info("hooksDir: "+hooksDir.getAbsolutePath());

    }
    /**
     * @param s
     */
    public void overrideGitMetadataFolder(String s) {
        this.gitMetadataFolder = s;
    }
    /**
     * @throws IOException
     */
    public void deploy(List<String> tools, List<String> hooks) throws IOException {
//        File srcDir = this.settings.getHooksSourceDirectory();
//        List<File> sourceFiles = Arrays.asList(srcDir.listFiles());
//        for (File srcFile: sourceFiles) {
//            this.processHookFile(srcFile);
//        }
        // TODO Determine what files need to be created from configuration
        // TODO create the files
        // TODO Deploy to git hooks
    }
    /**
     * @param srcFile
     * @throws IOException
     */
    public void processHookFile(File srcFile) throws IOException {
        File targetFile = new File(this.hooksDir.getCanonicalPath()+"/"+srcFile.getName());
        // source exist in target?
        this.logger.info("Deploying to "+targetFile.getAbsolutePath());
        FileIO.addCommandToHooksFile(srcFile, targetFile);
        
        targetFile = new File(targetFile.getCanonicalPath());
        // make executable
        if (!targetFile.canExecute()) {
            targetFile.setExecutable(true);
        }
    }
}
