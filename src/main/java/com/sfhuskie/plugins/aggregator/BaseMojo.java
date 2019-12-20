package com.sfhuskie.plugins.aggregator;
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

abstract class BaseMojo extends AbstractMojo {
    Log mavenLog = getLog();

    //@Parameter(defaultValue = "${basedir}", property = "search.directory", required = true)
    protected File searchDirectory;

    //@Parameter(defaultValue = "target/surefire-reports", property = "directory.pattern", required = true)
   protected String directoryPattern;

   //@Parameter(defaultValue = "target/surefire-reports", property = "output.directory", required = true)
   protected File outputDirectory;

   //@Parameter(defaultValue = "junit-results.xml", property = "report.name", required = true)
   protected File reportName;
   
   public void execute() throws MojoExecutionException, MojoFailureException {
       mavenLog.info("searchDirectory: "+searchDirectory);
       mavenLog.info("directoryPattern: "+directoryPattern);
       mavenLog.info("outputDirectory: "+outputDirectory);
       mavenLog.info("reportName: "+reportName);
   }
}
