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

import java.io.File;
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
import java.io.IOException;

import org.apache.maven.shared.utils.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.sfhuskie.plugins.githooks.MojoSettings;

public class TestGitFolderFinder {
    File targetDir = new File(MojoSettings.userDir+"/target");
    @Test
    public void findGitTestFolderCurrentDir() throws IOException {
        File projectDir = new File(targetDir.getAbsolutePath()+"/folderA/folderB/folderC/");
        FileUtils.forceMkdir(projectDir);
        GitFolderFinder gff = new GitFolderFinder(projectDir);
        for (String folder: gff.subFolderNames.keySet()) { 
            File dir = new File(targetDir.getAbsolutePath()+"/folderA/folderB/folderC/.git.bak/"+folder);
            FileUtils.forceMkdir(dir);
        }
        gff.overrideSearchFolder(".git.bak");
        assertEquals(new File(targetDir.getCanonicalFile()+"/folderA/folderB/folderC/.git.bak"),gff.find());
    }
    @Test
    public void findGitTestFolderParentDir() throws IOException {
        File projectDir = new File(targetDir.getAbsolutePath()+"/folderX/folderY/folderZ/");
        FileUtils.forceMkdir(projectDir);
        GitFolderFinder gff = new GitFolderFinder(projectDir);
        for (String folder: gff.subFolderNames.keySet()) { 
            File dir = new File(targetDir.getAbsolutePath()+"/folderX/.git.bak/"+folder);
            FileUtils.forceMkdir(dir);
        }
        gff.overrideSearchFolder(".git.bak");
        File expected = new File(targetDir.getCanonicalFile()+"/folderX/.git.bak");
        File actual = gff.find();
        assertEquals(expected,actual);
    }
}
