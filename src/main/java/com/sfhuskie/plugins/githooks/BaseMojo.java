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
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Parameter;
import java.util.Arrays;

abstract class BaseMojo extends AbstractMojo {
    Log mavenLog = getLog();

    @Parameter(defaultValue = "${basedir}", property = "root.directory", required = true)
    protected File rootDirectory;

    @Parameter(defaultValue = "${project.build.directory}", property = "target.directory", required = true)
    protected File targetDirectory;

    @Parameter(defaultValue = MojoSettings.default_checkstyle_url, property = "checkstyle.url", required = true)
    protected String checkstyleUrl;

    @Parameter(defaultValue = MojoSettings.default_checkstyle_xml_url, property = "checkstyle.xml.url", required = true)
    protected String checkstyleXmlUrl;

    @Parameter(defaultValue = MojoSettings.HOOKS, property = "hooks", required = true)
    protected List<String> hooks;

    @Parameter(defaultValue = MojoSettings.CHECKSTYLE, property = "tools", required = true)
    protected List<String> tools;

    
   public void execute() throws MojoExecutionException, MojoFailureException {
       mavenLog.info("rootDirectory: "+rootDirectory);
       mavenLog.info("targetDirectory: "+targetDirectory);
       mavenLog.info("checkstyleUrl: "+checkstyleUrl);
       mavenLog.info("hooks: "+hooks.get(0)+","+hooks.get(1));
   }
}
