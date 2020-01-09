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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Before;
import org.junit.Test;

import com.sfhuskie.plugins.githooks.MojoSettings;


public class TestFileIO {
    MojoSettings settings;
    File targetDir = new File(MojoSettings.userDir+"/target"); 
    File scriptFile = new File(targetDir.getAbsoluteFile()+"/bin/pre-commit");
    @Before
    public void before() throws IOException {
        settings = MojoSettings.getInstance();
    }
    /**
     * @throws Exception
     */
    @Test 
    public void readDeployedPreCommitFile() throws Exception {
        List<String> lines = FileIO.readFileFromPath("java-check");
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
        File file = new File(settings.userDir + "/target/test.sh");
        FileIO.writeLines(file, lines);
        List<String> lines2 = FileIO.getLines(file);
        assertEquals(lines,lines2);
    }
    /**
     * @throws IOException
     */
    @Test 
    public void deployFile() throws IOException {
        
        File targetFile = new File(targetDir.getCanonicalPath()+"/pre-commit");
        if (targetFile.exists()) {
            FileUtils.forceDelete(targetFile);
        }
        targetFile = new File(targetDir.getCanonicalPath()+"/pre-commit");
        FileIO.addCommandToHooksFile(this.scriptFile, targetFile);
        List<String> expected = FileIO.getInitialScriptLines(this.scriptFile);
        List<String> actual = FileIO.getLines(targetFile);
        assertEquals(expected,actual);
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
    public void addCommand() {
        String newLine = "bash ~/myscript";
        List<String> expected = new ArrayList<String>();
            expected.add("#!/bin/sh");
            expected.add(newLine);
            expected.add("echo \"steven went to New York!\"");

        List<String> target = new ArrayList<String>();
            target.add("#!/bin/sh");
            target.add("echo \"steven went to New York!\"");

        List<String> actual = FileIO.addCommand(newLine, target);
        assertEquals(expected, actual);
    }
    /**
     * @throws IOException
     */
    @Test 
    public void addCommandToExistingHooksFile() throws IOException {
        
        File targetFile = new File(targetDir.getCanonicalPath()+"/pre-commit");

        // Command line to add
        String newLine = MojoSettings.getCommandToAdd(scriptFile);

        // Create initial target file
        List<String> targetLines = new ArrayList<String>();
            targetLines.add("#!/bin/sh");
            targetLines.add("echo 'Steven was here!'");

            if (targetFile.exists()) {
                FileUtils.forceDelete(targetFile);
            }
            // Write target file
            FileIO.writeLines(targetFile, targetLines);
        // Create expected
        List<String> expected = new ArrayList<String>();
            expected.add(0, targetLines.get(0));
            expected.add(1, newLine);
            for (int i = 1; i < targetLines.size(); i++) {
                expected.add(targetLines.get(i));
            }
        // Add command
        FileIO.addCommandToHooksFile(scriptFile, targetFile);
        List<String> actual = FileIO.getLines(targetFile);   
        assertEquals(expected,actual);
    }
    /**
     * @throws IOException
     */
    @Test 
    public void notAddCommandToExistingHooksFile() throws IOException {
        
        File targetFile = new File(targetDir.getCanonicalPath()+"/pre-commit");

        // Command line to add
        String newLine = MojoSettings.getCommandToAdd(scriptFile);

        // Create initial target file
        List<String> targetLines = new ArrayList<String>();
            targetLines.add("#!/bin/sh");
            targetLines.add(newLine);
            targetLines.add("echo 'Steven was here!'");

            if (targetFile.exists()) {
                FileUtils.forceDelete(targetFile);
            }
            // Write target file
            FileIO.writeLines(targetFile, targetLines);
        // Create expected
        List<String> expected = new ArrayList<String>();
            expected.add("#!/bin/sh");
            expected.add(newLine);
            expected.add("echo 'Steven was here!'");
        // Add command
        FileIO.addCommandToHooksFile(scriptFile, targetFile);
        List<String> actual = FileIO.getLines(targetFile);   
        assertEquals(expected,actual);
    }
    @Test
    public void testReadFileFromStream() throws Exception {
        List<String> actual = FileIO.readFileFromPath("java-check");
        assertEquals(FileIO.getLines(new File("src/main/resources/templates/java-check")), actual);
    }
    @Test
    public void downloadJar() throws MalformedURLException, IOException {
        String url = MojoSettings.default_checkstyle_url;
        File jarFile = new File(MojoSettings.userDir+"/tmp/downloadJarTest.jar");
        if (jarFile.exists()) {
            FileUtils.forceDelete(jarFile);
        }
        FileIO.downloadJar(url, jarFile);
        assertTrue(jarFile.exists());
        FileUtils.forceDelete(jarFile);
    }
    @Test
    public void downloadXml() throws MalformedURLException, IOException {
        String url = MojoSettings.default_checkstyle_xml_url;
        String xml = MojoSettings.userDir+"/tmp/whitespace.xml";
        File file = new File(xml);
        if (file.exists()) {
            FileUtils.forceDelete(file);
        }
        FileIO.download(url, xml);
        assertTrue(file.exists());
    }
}
