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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitFolderFinder {
    File initialDir;
    String searchFolder = ".git";
    int depth = 6;
    protected Map<String,Boolean> subFolderNames = new HashMap<String, Boolean>();
    
    public GitFolderFinder(File dir) {
        this.initialDir = dir;
        this.subFolderNames.put("hooks",false);
        this.subFolderNames.put("refs",false);
        this.subFolderNames.put("objects",false);
        this.subFolderNames.put("info",false);
        this.subFolderNames.put("logs",false);
    }
    public void overrideSearchFolder(String s) {
        this.searchFolder = s;
    }
    public boolean isValidGitDir(File dir) {
        List<File> entries = Arrays.asList(dir.listFiles());
        for (File entry:entries) {
            if (entry.isDirectory()) {
                for (String folder:this.subFolderNames.keySet()) {
                    String name = entry.getName();
                    if (name.contains(folder)) {
                        this.subFolderNames.put(folder, true);
                    }
                }
            }
        }
        for (Boolean contains:this.subFolderNames.values()) {
            if (!contains) {
                return false;
            }
        }
        return true;
    }
    public boolean doesGitFolderExist(File folder) {
        List<File> files = Arrays.asList(folder.listFiles());
        for (File file:files) {
            if (file.getName().contains(this.searchFolder)) {
                return true;
            }
        }
        return false;
    }
    File getGitPath(File basePath) throws IOException {
        return new File(basePath.getCanonicalPath()+"/"+this.searchFolder);
    }
    public File find() throws IOException {
        if (this.doesGitFolderExist(this.initialDir) 
                && this.isValidGitDir(getGitPath(initialDir))) {
            return getGitPath(initialDir);
        }
        String path = this.initialDir.getAbsolutePath();
        for (int i = 0; i < this.depth; i++) {
            path =  path + "/..";
            File dir = new File(path);
            if (this.doesGitFolderExist(dir) 
                    && this.isValidGitDir(getGitPath(dir))) {
                return getGitPath(dir);
            }
        }
        throw new RuntimeException("");
    }
    static void console(String m) {
        System.out.println(m);
    }
}
