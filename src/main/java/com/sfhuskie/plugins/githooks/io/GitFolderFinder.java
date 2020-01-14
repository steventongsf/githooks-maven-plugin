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
    }
    
    public Map<String, Boolean> getSubFolderNames() {
        return subFolderNames;
    }

    public void setSubFolderNames(Map<String, Boolean> subFolderNames) {
        this.subFolderNames = subFolderNames;
    }

    public void overrideSearchFolder(String s) {
        this.searchFolder = s;
    }
    public boolean isValidGitDir(File dir) {
        if (!dir.exists()) {
            return false;
        }
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
    /** Check to see if .git path exists
     * @param folder    folder instance to verify
     * @return boolean  true or false
     */
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
        File gitPath = getGitPath(initialDir);
        if (this.isValidGitDir(gitPath)) {
            return gitPath;
        }
        String path = this.initialDir.getAbsolutePath();
        for (int i = 0; i < this.depth; i++) {
            path =  path + "/..";
            File dir = new File(path);
            gitPath = getGitPath(dir);
            if (this.isValidGitDir(gitPath)) {
                return gitPath;
            }
        }
        throw new RuntimeException("Unable to find git directory in any parent folder.");
    }
    static void console(String m) {
        System.out.println(m);
    }
}
