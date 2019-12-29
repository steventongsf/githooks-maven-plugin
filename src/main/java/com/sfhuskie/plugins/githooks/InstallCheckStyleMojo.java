package com.sfhuskie.plugins.githooks;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

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
import org.apache.maven.plugin.AbstractMojo;

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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import com.sfhuskie.plugins.githooks.io.FileIO;

@Mojo(name = "install-checkstyle", defaultPhase = LifecyclePhase.COMPILE, requiresProject = true, threadSafe = true)
public class InstallCheckStyleMojo	extends BaseMojo {

	public void execute() throws MojoExecutionException, MojoFailureException {
	    super.execute();
	    File targetJar;
	    File url = new File(this.checkstyleUrl);
        try {
            targetJar = new File(this.targetDirectory.getCanonicalPath()+"/"+url.getName());
        } 
        catch (IOException e) {
            throw new MojoExecutionException("Failed to create File instance",e);
        }
	    if (! targetJar.exists()) {
	        try {
                FileIO.download(this.checkstyleUrl, targetJar.getCanonicalPath());
            } 
	        catch (IOException e) {
                throw new MojoExecutionException("Failed to download file",e);
            }
	    }
	}
}
