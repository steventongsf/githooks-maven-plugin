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
        if (this.tools.contains(MojoSettings.CHECKSTYLE)) {
            List<String> commands = new ArrayList<String>();
            List<String> javaCheck = FileIO.readFileFromPath("java-check");
            Collections.copy(commands, javaCheck);
            commands.add(getCommandToAdd(script));
            return commands;
        }
        else {
            throw new RuntimeException("Tool not supported.");
        }
    }
    /**
     * @param destDir       Directory to write files to
     * @throws IOException
     */
    public void generate(File destDir) throws IOException {
        if (this.hooks.contains(MojoSettings.PRECOMMIT)) {
            // TODO Write file
            File precommit = new File(destDir.getAbsolutePath()+"/bin/"+MojoSettings.PRECOMMIT);
            List<String> lines = getCommands(precommit);
            FileUtils.forceMkdir(precommit);
            FileIO.writeLines(precommit, lines);
        }
        if (this.hooks.contains(MojoSettings.PREPUSH)) {
            // TODO Write file
            File prepush = new File(destDir.getAbsolutePath()+"/bin/"+MojoSettings.PREPUSH);
            List<String> lines = getCommands(prepush);
            FileUtils.forceMkdir(prepush);
            FileIO.writeLines(prepush, lines);
        }
    }
    /**
     * @param file
     * @return
     * @throws IOException
     */
    public static String getCommandToAdd(File file) throws IOException {
        return "bash "+file.getCanonicalPath();
    }
}
