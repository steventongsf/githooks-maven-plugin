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
    public static List<String> getLines(File f) throws MojoExecutionException {
        try {
            return FileUtils.readLines(f, encoding);
        } 
        catch (IOException e) {
            throw new MojoExecutionException("Failed to read lines from " + f.getAbsolutePath(), e);
        }
        
    }
    public static void writeLines(File file, List<String> lines) throws IOException {
        FileUtils.writeLines(file, encoding, lines);
    }
    public static void makeDirectoryFilesExecutable(File directory) {
        List<File> entries = Arrays.asList(directory.listFiles());
        for (File entry: entries) {
            if (entry.isFile()) {
                entry.setExecutable(true);
            }
        }
    }
}
