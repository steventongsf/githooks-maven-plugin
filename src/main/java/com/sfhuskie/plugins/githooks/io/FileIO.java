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
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import com.sfhuskie.plugins.githooks.MojoSettings;

import java.io.BufferedReader;
import java.io.InputStream;
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
public class FileIO {
    public static String encoding = "UTF-8";
    /** Read lines from file into a List
     * @param f         File instance to get lines from
     * @return List     List of strings
     */
    public static List<String> getLines(File f) throws IOException {
        return FileUtils.readLines(f, encoding);
    }
    /** Write List of lines to a file
     * @param file  output file
     * @param lines List of lines to write
     * @throws IOException  file io issue
     */
    public static void writeLines(File file, List<String> lines) throws IOException {
        FileUtils.writeLines(file, encoding, lines);
    }
    /**  Append command to call external script to target script (git hooks script)
     * @param srcFile       template file
     * @param targetFile    target file to append to
     * @return  boolean     If successful.
     * @throws IOException  file read write issue
     */
    public static boolean addCommandToHooksFile(File srcFile, File targetFile) throws IOException {
        String newLine = MojoSettings.getCommandToCallScript(srcFile);

        if (targetFile.exists()) {
            // add command
            List<String> targetLines = getLines(targetFile);
            targetLines = addCommand(newLine, targetLines);
            writeLines(targetFile, targetLines);
        }
        else {
            // No append. Write new file
            writeInitialHooksFile(srcFile, targetFile);
        }
        return true;
    }
    /**
     * @param script script path name create command
     * @return List         list of lines
     * @throws IOException  error creating file object
     */
    public static List<String> getInitialScriptLines(File script) throws IOException {
        List<String> lines = new ArrayList<String>();
        lines.add("#!/bin/sh");
        lines.add("");
        lines.add(MojoSettings.getCommandToCallScript(script));
        return lines;
    }
    /**  
     * @param script    script path name to add to command
     * @param out       file to write to
     * @throws IOException  error creating file object
     */
    public static void writeInitialHooksFile(File script, File out) throws IOException {
        List<String> lines = getInitialScriptLines(script);
        FileIO.writeLines(out,  lines);
    }
    /** Add command to 2nd line in List
     * @param newLine   line to add
     * @param target    file to write to
     * @return List         list of lines
     */
    public static List<String> addCommand(String newLine,List<String> target) {
        if (!doesFileContainLine(target, newLine)) {
            target.add(1, newLine);
        }
        return target;
    }
    /**
     * @param lines         List of lines
     * @param searchString  search term
     * @return List         list of lines
     */
    public static boolean doesFileContainLine(List<String> lines, String searchString) {
        for (String line:lines) {
            if (line.contains(searchString)) {
                return true;
            }
        }
        return false;
 
    }
    /**
     * @param url           url for download
     * @param localFile     file to write to
     * @throws MalformedURLException    error downloading file
     * @throws IOException              error creating file object
     */
    public static void download(String url, String localFile) throws MalformedURLException, IOException {
        final int timeout = 60000;
        FileUtils.copyURLToFile(
                new URL(url), 
                new File(localFile), 
                timeout, 
                timeout);
    }
    /** Read named file template found in path.  
     * @param fileName      template name to read
     * @return              List of lines
     * @throws IOException  error creating file object
     */
    public static List<String> readFileTemplateFromPath(String fileName) throws IOException {
        List<String> lines = new ArrayList<String>();
        InputStream is = (InputStream) FileIO.class.getResourceAsStream("/templates/"+fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
    public static void downloadJar(String url, File jarFile) throws MalformedURLException, IOException {
        if (! jarFile.exists()) {
            FileIO.download(url, jarFile.getCanonicalPath());
        }
    }
}
