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
import java.util.List;

import org.junit.Test;

import com.sfhuskie.plugins.githooks.MojoSettings;

public class TestGitHooksMgr {
    @Test
    public void updateHooks() throws IOException {
        GitHooksMgr mgr = new GitHooksMgr(null);
        mgr.overrideGitMetadataFolder("git.bak");
        List<String> tools = new ArrayList<String>();
        tools.add(MojoSettings.CHECKSTYLE);
        List<String> hookScripts = new ArrayList<String>();
        hookScripts.add(MojoSettings.PRECOMMIT);
        mgr.updateHooks(tools, hookScripts);
    }
}
