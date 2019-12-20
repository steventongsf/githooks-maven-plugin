package com.sfhuskie.plugins.githooks.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
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
public class TestFileIO {
    MojoSettings s = new MojoSettings(MojoSettings.userDir.getAbsolutePath());
    
    @Test 
    public void readDeployedPreCommitFile() throws Exception {
        File file = new File(s.getHooksSourceDirectory().getAbsolutePath()+"/pre-commit");
        List<String> lines = FileIO.getLines(file);
        assertTrue(lines.size() > 0);
    }
    @Test 
    public void writeLines() throws IOException, MojoExecutionException {
        List<String> lines = new ArrayList<String>();
        lines.add("#!/bin/sh");
        lines.add("echo 'steven was here'");
        File file = new File(s.userDir + "/target/test.sh");
        FileIO.writeLines(file, lines);
        List<String> lines2 = FileIO.getLines(file);
        assertEquals(lines,lines2);
    }
    @Test
    public void isFileExecutable() throws IOException {
        File file = new File(s.userDir + "/target/exectest.sh");
        if (file.exists()) {
            FileUtils.forceDelete(file);
        }
        List<String> lines = new ArrayList<String>();
        lines.add("#!/bin/sh");
        file = new File(s.userDir + "/target/exectest.sh");
        FileIO.writeLines(file, lines);
        file = new File(s.userDir + "/target");
        FileIO.makeDirectoryFilesExecutable(file);
        file = new File(s.userDir + "/target/exectest.sh");
        assertTrue(file.canExecute());
    }
}
