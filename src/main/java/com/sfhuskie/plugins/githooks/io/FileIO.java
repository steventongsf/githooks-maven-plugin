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
    /**
     * @param srcFile
     * @param targetFile
     * @return
     * @throws IOException 
     */
    public static boolean appendToHookFile(File srcFile, File targetFile) throws IOException {
        if (targetFile.exists()) {
            // append contents as needed
            List<String> srcLines = getLines(srcFile);
            List<String> targetLines = getLines(targetFile);
            String newLine = "bash "+srcFile.getCanonicalPath();
            if (doesFileContainLine(targetLines, newLine)) {
                return true;
            }
            else {
                targetLines.add(1, newLine);
            }
            writeLines(targetFile, targetLines);
        }
        else {
            FileUtils.copyFile(srcFile, targetFile);
        }
        return true;
    }
    public static boolean doesFileContainLine(List<String> lines, String searchString) {
        for (String line:lines) {
            if (line.contains(searchString)) {
                return true;
            }
        }
        return false;
 
    }
}
