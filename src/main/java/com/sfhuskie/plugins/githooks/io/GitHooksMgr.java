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

public class GitHooksMgr {
    MojoSettings settings;
    File targetDir = null;
    Log logger;
    /**
     * @param rootDir
     * @throws IOException
     */
    public GitHooksMgr(Log logger, File targetDir) throws IOException {
        this.logger = logger;
        this.targetDir = targetDir;
        this.settings = MojoSettings.getInstance();
        logger.info("gitDir: "+this.settings.getGitDir().getAbsolutePath());
        logger.info("hooksDir: "+this.settings.getHooksDir().getAbsolutePath());

    }
    /**
     * @param s
     */
    public void overrideGitMetadataFolder(String s) {
        this.settings.overrideGitMetadataFolder(s);
    }
    /**
     * @throws IOException
     */
    public void deploy(List<String> tools, List<String> hookScripts) throws IOException {
        // Process hook files
        for (String hookScript: hookScripts) {
            File script = new File(hookScript);
            File gitHook = new File(this.settings.getHooksDir()+"/"+hookScript);
            FileIO.addCommandToHooksFile(script, gitHook);
        }
    }
    /**
     * @param srcFile
     * @throws IOException
     */
    public void processHookFile(File srcFile) throws IOException {
        File targetFile = new File(settings.getHooksDir().getCanonicalPath()+"/"+srcFile.getName());
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
