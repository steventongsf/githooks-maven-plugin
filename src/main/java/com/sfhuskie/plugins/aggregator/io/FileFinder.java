package com.sfhuskie.plugins.aggregator.io;
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
import java.util.ArrayList;
import java.util.List;

public class FileFinder implements FinderFilter {
    File searchDir;
    String filePrefix = "TEST-";
    String fileExt = ".java";
    FinderFilter finderFilter = this;
    public FileFinder(File searchDir) {
        this.searchDir = searchDir;
    }

    public FinderFilter getFinderFilter() {
        return finderFilter;
    }

    public void setFinderFilter(FinderFilter finderFilter) {
        this.finderFilter = finderFilter;
    }

    public List<File> find() {
        return this.find(this.searchDir);
    }
    List<File> find(File searchDir) {
        List<File> files = new ArrayList<File>();
        String[] fileNames = searchDir.list();
        for (String fileName:fileNames) {
            File f = new File(searchDir+"/"+fileName);
            if (f.isDirectory()) {
                files.addAll(this.find(f));
            }
            else {
                if (this.finderFilter.isValidFile(f)) {
                    files.add(f);
                }
            }
        }
        return files;
    }

    @Override
    public boolean isValidFile(File file) {
        throw new RuntimeException("Not implemented");
    }
}
