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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;

public class FileIO {
    public static String encoding = "UTF-8";
    /**
     * @param f         File instance to get lines from
     * @return List     List of strings
     * @throws MojoExecutionException  Failure to read file
     */
    public static List<String> getLines(File f) throws IOException {
        return FileUtils.readLines(f, encoding);
    }
    /**
     * @param file
     * @param lines
     * @throws IOException
     */
    public static void writeLines(File file, List<String> lines) throws IOException {
        FileUtils.writeLines(file, encoding, lines);
    }
    public static String getCommandToAdd(File file) throws IOException {
        return "bash "+file.getCanonicalPath();
    }
    /**
     * @param srcFile
     * @param targetFile
     * @return  boolean If successful.
     * @throws IOException 
     */
    public static boolean addCommandToHooksFile(File srcFile, File targetFile) throws IOException {
        String newLine = getCommandToAdd(srcFile);

        if (targetFile.exists()) {
            // add command
            List<String> targetLines = getLines(targetFile);
            targetLines = addCommand(newLine, targetLines);
            writeLines(targetFile, targetLines);
        }
        else {
            // No append. Copy file
            FileUtils.copyFile(srcFile, targetFile);
        }
        return true;
    }
    public static List<String> addCommand(String newLine,List<String> target) {
        // TODO 
        if (!doesFileContainLine(target, newLine)) {
            target.add(1, newLine);
        }
        return target;
    }
    public static boolean doesFileContainLine(List<String> lines, String searchString) {
        for (String line:lines) {
            if (line.contains(searchString)) {
                return true;
            }
        }
        return false;
 
    }
    public static void download(String url, String localFile) throws MalformedURLException, IOException {
        final int timeout = 60000;
        FileUtils.copyURLToFile(
                new URL(url), 
                new File(localFile), 
                timeout, 
                timeout);
    }
}
