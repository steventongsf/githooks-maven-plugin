package com.sfhuskie.plugins.githooks;
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

public class MojoSettings {
    public static final String PRECOMMIT = "pre-commit";
    public static final String PREPUSH = "pre-push";
    public static final String HOOKS = PRECOMMIT+","+PREPUSH;
    public static final String CHECKSTYLE = "check-style";
    public static final String default_checkstyle_url = "https://github.com/checkstyle/checkstyle/releases/download/checkstyle-8.27/checkstyle-8.27-all.jar";
    public static final File userDir = new File(System.getProperty("user.dir"));
    //File hooksSourceDirectory = new File(this.userDir.getAbsolutePath()+"/src/main/hooks");
    File hooksDir;
    File rootDir;
    public String hooksRelativeDir = "/hooks";

    public MojoSettings() {
    }

}
