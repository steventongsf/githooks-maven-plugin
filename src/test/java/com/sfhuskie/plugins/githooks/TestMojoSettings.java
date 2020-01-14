package com.sfhuskie.plugins.githooks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

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
public class TestMojoSettings {
    @Test
    public void getCommandToAdd() throws IOException {
        MojoSettings s = MojoSettings.getInstance();
        File script = new File(s.getToolsBaseDir()+"/"+s.CHECKSTYLE+"/"+MojoSettings.PRECOMMIT);
        String actual =  MojoSettings.getCommandToCallScript(script);
        String expected = "bash "+script.getCanonicalPath();
        expected = expected.replace("\\", "/");
        assertEquals(expected, actual);
    }
    @Test
    public void getCheckstyleCommandToAdd() throws IOException {
        MojoSettings s = MojoSettings.getInstance();
        String jar = (new File(s.getHooksDir()+"/tools/"+s.CHECKSTYLE+"/"+s.checkstyleJar)).getCanonicalPath().replace("\\", "/");
        String xml = (new File(s.getHooksDir()+"/tools/"+s.CHECKSTYLE+"/"+s.checkstyleXml)).getCanonicalPath().replace("\\", "/");
        String expected = String.format("$JAVACMD -jar %s -c %s .",jar,xml);
        String actual = MojoSettings.getCheckstyleCommandToAdd();
        assertEquals(expected,actual);
    }
    @Test 
    public void thisIsValidGitProject() throws IOException {
        MojoSettings s = MojoSettings.getInstance();
        assertNotNull(s.getHooksDir());
        
    }
    @Test 
    public void defaultHooks() throws IOException {
        MojoSettings s = MojoSettings.getInstance();
        assertEquals(2,s.getHooks().size());
        assertTrue(s.getHooks().contains(s.PRECOMMIT));
    }
}
