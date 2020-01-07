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
    MojoSettings s = new MojoSettings();
    File targetDir = new File(MojoSettings.userDir+"/target"); 
    
    /**
     * @throws Exception
     */
    @Test 
    public void readDeployedPreCommitFile() throws Exception {
        File file = new File(s.getHooksSourceDirectory().getAbsolutePath()+"/pre-commit");
        List<String> lines = FileIO.getLines(file);
        assertTrue(lines.size() > 0);
    }
    /**
     * @throws IOException
     * @throws MojoExecutionException
     */
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
    /**
     * @throws IOException
     */
    @Test 
    public void deployFile() throws IOException {
        
        File srcFile = new File(this.s.getHooksSourceDirectory()+"/pre-commit");
        File targetFile = new File(targetDir.getCanonicalPath()+"/pre-commit");
        if (targetFile.exists()) {
            FileUtils.forceDelete(targetFile);
        }
        FileIO.appendToHookFile(srcFile, targetFile);
        assertEquals(FileIO.getLines(srcFile),FileIO.getLines(targetFile));
    }
    /**
     * 
     */
    @Test
    public void doesContainLine() {
        String searchString = "#!/bin/bash";
        List<String> lines = new ArrayList<String>();
        lines.add("#!/bin/bash");
        for (int i = 0; i < 10; i++) {
            lines.add("String: "+i);
        }
        assertTrue(FileIO.doesFileContainLine(lines, searchString));
    }
    /**
     * 
     */
    @Test
    public void doesNotContainLine() {
        String searchString = "#!/bin/bash";
        List<String> lines = new ArrayList<String>();
        lines.add("#!/bin/csh");
        for (int i = 0; i < 10; i++) {
            lines.add("String: "+i);
        }
        assertTrue(!FileIO.doesFileContainLine(lines, searchString));
    }
    @Test
    public void mergeLists() {
        List<String> expected = new ArrayList<String>();
        expected.add("#!/bin/sh");
        expected.add("echo \"steven was here!\"");
        expected.add("echo \"steven went to New York!\"");
        List<String> src = new ArrayList<String>();
        src.add("#!/bin/sh");
        src.add("echo \"steven was here!\"");
        List<String> target = new ArrayList<String>();
        expected.add("#!/bin/sh");
        expected.add("echo \"steven went to New York!\"");
        target = FileIO.mergeLists(src, target);
        assertEquals(expected, target);
    }
    /**
     * @throws IOException
     */
    @Test 
    public void appendToFile() throws IOException {
        
        File srcFile = new File(this.s.getHooksSourceDirectory()+"/pre-commit");
        File targetFile = new File(targetDir.getCanonicalPath()+"/pre-commit");
        List<String> lines = new ArrayList<String>();
        lines.add("#!/bin/sh\n");
        lines.add("echo 'Steven was here!'\n");
        if (targetFile.exists()) {
            FileUtils.forceDelete(targetFile);
        }
        FileIO.writeLines(targetFile, lines);
        FileIO.appendToHookFile(srcFile, targetFile);
        // Create expected list
        List<String> expected = FileIO.getLines(srcFile);
            // add to beginning
            expected.add(0,"#!/bin/sh\n");
            // add to end
            expected.add(expected.size(),"echo 'Steven was here!'\n");
        List<String> actual = FileIO.getLines(srcFile);   
        assertEquals(expected,actual);
    }

}
