package com.sfhuskie.plugins.githooks.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.maven.shared.utils.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.sfhuskie.plugins.githooks.MojoSettings;

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
public class TestScriptMaker {
    List<String> hooks;
    List<String> tools;
    @Before
    public void before() {
        hooks = new ArrayList<String>();
        tools = new ArrayList<String>();
        tools.add(MojoSettings.CHECKSTYLE);
    }
    @Test
    public void getCommandToAdd() throws IOException {
        File script = new File(MojoSettings.userDir+"/"+MojoSettings.PRECOMMIT);
        assertEquals("bash "+script.getCanonicalPath(), MojoSettings.getCommandToAdd(script));
    }
    @Test
    public void getCommandsPrecommit() throws IOException {
        hooks.add(MojoSettings.PRECOMMIT);
        ScriptMaker sm = new ScriptMaker(tools, hooks);
        File precommit = new File(MojoSettings.userDir+"/"+MojoSettings.PRECOMMIT);
        File prepush = new File(MojoSettings.userDir+"/"+MojoSettings.PREPUSH);
        List<String> cmds = sm.getCommands(precommit);
        assertTrue(cmds.contains(MojoSettings.getCommandToAdd(precommit)));
        assertTrue(cmds.get(0).equals("#!/bin/sh"));
        assertTrue(!cmds.contains(prepush.getAbsolutePath()));
    }
    @Test
    public void generateScripts() throws IOException {
        hooks.add(MojoSettings.PRECOMMIT);
        ScriptMaker sm = new ScriptMaker(tools, hooks);
        File destDir = new File(MojoSettings.userDir+"/target/bin");
        File precommit = new File(MojoSettings.userDir+"/target/bin/"+MojoSettings.PRECOMMIT);
        if (precommit.exists()) {
            FileUtils.delete(precommit);
        }
        sm.generateScripts(destDir);
        assertTrue(precommit.exists());
        List<String> cmds = sm.getCommands(precommit);
        assertEquals(cmds, FileIO.getLines(precommit));
    }
}
