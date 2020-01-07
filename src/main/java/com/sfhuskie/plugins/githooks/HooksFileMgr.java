package com.sfhuskie.plugins.githooks;
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

import com.sfhuskie.plugins.githooks.io.FileIO;

public class HooksFileMgr {
    List<String> tools;
    List<String> hooks;
    /**
     * @param tools
     * @param hooks
     */
    public HooksFileMgr(List<String> tools, List<String> hooks) {
        this.tools = tools;
        this.hooks = hooks;
    }
    /**
     * @return
     * @throws IOException
     */
    public List<String> getCommands() throws IOException {
        List<String> commands = new ArrayList<String>();
        List<String> javaCheck = FileIO.readFileFromPath("java-check");
        if (this.tools.contains(MojoSettings.CHECKSTYLE)) {
            // Continue
            Collections.copy(commands, javaCheck);
            commands.add(getCommandToAdd(null));
        }
        else {
            throw new RuntimeException("Tool not supported.");
        }
        return commands;
    }
    /**
     * @param lines
     * @throws IOException
     */
    public void writeFiles(List<String> lines) throws IOException {
        if (this.hooks.contains(MojoSettings.PRECOMMIT)) {
            // TODO Write file
            File precommit = new File("");
            FileIO.writeLines(precommit, lines);
        }
        if (this.hooks.contains(MojoSettings.PREPUSH)) {
            // TODO Write file
            File prepush = new File("");
            FileIO.writeLines(prepush, lines);
        }
    }
    /**
     * @throws IOException
     */
    public void deployScripts() throws IOException {
        List<String> commands = getCommands();
        writeFiles(commands);
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
