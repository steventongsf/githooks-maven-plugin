package com.sfhuskie.plugins.githooks;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class CommandBuilder {
    List<String> tools;
    List<String> hooks;
    public CommandBuilder(List<String> tools, List<String> hooks) {
        this.tools = tools;
        this.hooks = hooks;
    }
    public List<String> getCommands() throws IOException {
        if (this.tools.contains(MojoSettings.CHECKSTYLE)) {
            // Continue
        }
        else {
            throw new RuntimeException("Tool not supported.");
        }
        List<String> commands = new ArrayList<String>();
        List<String> javaCheck = FileIO.readFileFromPath("java-check");
        if (this.hooks.contains(MojoSettings.PRECOMMIT)) {
            // TODO
        }
        if (this.hooks.contains(MojoSettings.PREPUSH)) {
            // TODO 
        }
        return commands;
    }
    public static String getCommandToAdd(File file) throws IOException {
        return "bash "+file.getCanonicalPath();
    }
}
