package com.sfhuskie.plugins.githooks.io;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import java.util.List;

import org.apache.maven.shared.utils.io.FileUtils;

import com.sfhuskie.plugins.githooks.MojoSettings;

/**  Create standalone executable scripts
 * @author Steven Tong
 *
 */
public class ScriptMaker {
    List<String> tools;
    List<String> hooks;
    /**
     * @param tools
     * @param hooks
     */
    public ScriptMaker(List<String> tools, List<String> hooks) {
        this.tools = tools;
        this.hooks = hooks;
    }
    /**
     * @return
     * @throws IOException
     */
    public List<String> getCommands(File script) throws IOException {
        // Checkstyle configured so get commands for checkstyle
        if (this.tools.contains(MojoSettings.CHECKSTYLE)) {
            List<String> commands = new ArrayList<String>();
            List<String> javaCheck = FileIO.readFileTemplateFromPath("java-check");
            for (String line: javaCheck) {
                commands.add(line);
            }
            commands.add(MojoSettings.getCheckstyleCommandToAdd());
            return commands;
        }
        throw new RuntimeException("Tool not supported.");
    }
    /** Write scripts based on the values in the hooks attribute
     * @param destDir       Directory to write files to
     * @throws IOException
     */
    public void generateScripts(File destDir) throws IOException {
        if (this.hooks.contains(MojoSettings.PRECOMMIT)) {
            File precommit = new File(destDir.getAbsolutePath()+"/"+MojoSettings.PRECOMMIT);
            createScript(precommit, destDir);
        }
        if (this.hooks.contains(MojoSettings.PREPUSH)) {
            File prepush = new File(destDir.getAbsolutePath()+"/"+MojoSettings.PREPUSH);
            createScript(prepush, destDir);
        }
    }
    protected void createScript(File script, File destDir) throws IOException {
        List<String> lines = getCommands(script);
        FileUtils.forceMkdir(destDir);
        FileIO.writeLines(script, lines);
        if (!script.canExecute()) {
            script.setExecutable(true);
        }
    }
}
