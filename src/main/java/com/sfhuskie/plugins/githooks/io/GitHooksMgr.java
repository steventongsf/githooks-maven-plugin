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

/**  Manage git hook scripts located in the folder .git/hooks
 * @author Steven Tong
 *
 */
public class GitHooksMgr {
    MojoSettings settings;
    Log logger;
    /**
     * @param rootDir
     * @throws IOException
     */
    public GitHooksMgr(Log logger) throws IOException {
        this.logger = logger;
        this.settings = MojoSettings.getInstance();
        info("gitDir: "+this.settings.getGitDir().getAbsolutePath());
        info("hooksDir: "+this.settings.getHooksDir().getAbsolutePath());

    }
    /**  Used to override metadata name for testing.
     * @param s name of alternate directory
     */
    protected void overrideGitMetadataFolder(String s) {
        this.settings.overrideGitMetadataFolder(s);
    }
    /**
     * @throws IOException
     */
    public void updateHooks(List<String> tools, List<String> hookScripts) throws IOException {
        // Process hook files
        // Working through the logic
        for (String hookScript: hookScripts) {
            for (String tool:tools) {
                // Location of script to execute
                File script = new File(this.settings.getHooksDir()+"/tools/"+tool+"/"+hookScript);
                // Location of .git/hooks/<script>
                File gitHookScript = new File(this.settings.getHooksDir()+"/"+hookScript);
                // Write to .git/hooks/<script>
                FileIO.addCommandToHooksFile(script, gitHookScript);
                // make executable
                if (!script.canExecute()) {
                    script.setExecutable(true);
                }
                if (!gitHookScript.canExecute()) {
                    gitHookScript.setExecutable(true);
                }
            }
        }
    }
    public void info(String m) {
        if (this.logger  == null) {
            System.out.println(m);
        }
        else {
            this.logger.info(m);
        }
    }
}
