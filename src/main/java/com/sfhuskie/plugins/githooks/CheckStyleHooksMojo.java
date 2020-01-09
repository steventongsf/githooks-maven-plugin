package com.sfhuskie.plugins.githooks;
import java.io.IOException;

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

import com.sfhuskie.plugins.githooks.io.GitHooksDeployer;

@Mojo(name = "enable-checkstyle-hooks", defaultPhase = LifecyclePhase.COMPILE, requiresProject = true, threadSafe = true)
public class CheckStyleHooksMojo	extends BaseMojo {

	public void execute() throws MojoExecutionException, MojoFailureException{
	    super.execute();
	    GitHooksDeployer deployer;
        try {
            deployer = new GitHooksDeployer(this.mavenLog, this.rootDirectory, this.targetDirectory);
            deployer.deploy(this.tools, this.hooks );
        } 
        catch (IOException e) {
            throw new MojoExecutionException("Failed to configure pre-commit.",e);
        }
	    
	}
}
