package com.sfhuskie.plugins.aggregator.junit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class TestJunitProcessor {
    JunitReportProcessor junitReportProcessor;
    @Before
    public void instanceBootstrap() {
        File search = new File(System.getProperty("user.dir")+"/src/test/resources/");
        File output = new File(System.getProperty("user.dir")+"/target/surefire-reports");
        String pattern = "target/surefire-reports";
        junitReportProcessor = new JunitReportProcessor(search,pattern,output);

    }
    @Test
    public void jacksonPojosAreJunitCompatibleSuccess() throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("<testsuite tests=\"3\" failures=\"0\" name=\"module1.MyTestCase\" time=\"0.002\" errors=\"0\" skipped=\"0\">");
        sb.append("<properties>"); 
        sb.append("<property name=\"java.runtime.name\" value=\"Java(TM) SE Runtime Environment\"/>");
        sb.append("<property name=\"MyName\" value=\"Steven Tong\"/>");
        sb.append("</properties>");
        sb.append("<testcase classname=\"module1.MyTestCase\" name=\"test1\" time=\"0.002\"/>");
        sb.append("<testcase classname=\"module1.MyTestCase\" name=\"test2\" time=\"0.002\"/>");
        sb.append("</testsuite>");
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        TestSuite testSuite = xmlMapper.readValue(sb.toString(), TestSuite.class);
        assertEquals(2, testSuite.getProperties().size());
        assertEquals(2, testSuite.getTestcase().size());
    }
    @Test
    public void jacksonPojosAreJunitCompatibleError() throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("<testsuite tests=\"3\" failures=\"0\" name=\"module1.MyTestCase\" time=\"0.002\" errors=\"0\" skipped=\"0\">");
        sb.append("<properties>"); 
        sb.append("<property name=\"java.runtime.name\" value=\"Java(TM) SE Runtime Environment\"/>");
        sb.append("<property name=\"MyName\" value=\"Steven Tong\"/>");
        sb.append("</properties>");
        sb.append("<testcase classname=\"module1.MyTestCase\" name=\"test1\" time=\"0.002\"/>");
        sb.append("<testcase classname=\"module1.MyTestCase\" name=\"test2\" time=\"0.002\">");
        sb.append("<error message=\"Error\" type=\"java.lang.RuntimeException\">java.lang.RuntimeException: Error</error>");
        sb.append("</testcase>");
        sb.append("</testsuite>");
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        TestSuite testSuite = xmlMapper.readValue(sb.toString(), TestSuite.class);
        assertEquals(2, testSuite.getProperties().size());
        assertEquals(2, testSuite.getTestcase().size());
    }
    @Test
    public void attributesAreValid() {
        File search = new File(System.getProperty("user.dir")+"/target/");
        File output = new File(System.getProperty("user.dir")+"/target/surefire-reports");
        String pattern = "target/surefire-reports";
        JunitReportProcessor j = new JunitReportProcessor(search,pattern,output);
        assertEquals(search, j.getSearchDir());
        assertEquals(output, j.getOutputDir());
        assertEquals(pattern, j.getDirectoryPattern());
        assertEquals("TEST-",j.getFilePrefix());
        assertEquals(".xml", j.getFileExtension());
        assertEquals("junit-results.xml", j.getTargetFileName());
    }
    @Test
    public void patternMatch() {
        File file = new File("target/surefire-reports");
        assertTrue(this.junitReportProcessor.directoryPatternMatch(file));
    }
    @Test
    public void validFile() {
        File f = new File(this.junitReportProcessor.getSearchDir()+"/src/test/resources/module1/target/surefire-reports/TEST-module1.MyTestCase.xml");
        assertTrue(junitReportProcessor.isValidFile(f));
    }
    @Test
    public void notValidFile() {
        File f = new File(this.junitReportProcessor.getSearchDir()+"/src/test/resources/module1/target/surefire-reports/TET-module1.MyTestCase.xml");
        assertFalse(junitReportProcessor.isValidFile(f));
    }    
    @Test
    public void findFiles() throws IOException {
        List<File> files = this.junitReportProcessor.findJUnitXmlFiles();
        assertEquals(2, files.size());
    }
    @Test
    public void xmlFileToPojo() throws JsonParseException, JsonMappingException, IOException {
        String fileName = this.junitReportProcessor.getSearchDir()+"/module1/target/surefire-reports/TEST-module1.MyTestCase.xml";
        TestSuite testSuite = this.junitReportProcessor.getTestSuite(fileName);
        assertEquals("module1.MyTestCase", testSuite.name);
    }
    @Test
    public void summarize() throws IOException {
        Map<String,TestSuite> testSuites = this.junitReportProcessor.getTestSuitesFromFiles(this.junitReportProcessor.findJUnitXmlFiles());
        assertEquals(2, testSuites.size());
    }
    @Test
    public void copyJUnitXmlFiles() {
        
    }
    @Test
    public void processJUnitResults() throws IOException {
        this.junitReportProcessor.processJUnitResults(false, true);
        Path path = Paths.get(this.junitReportProcessor.getOutputDir()+"/"+this.junitReportProcessor.getTargetFileName());
        assertTrue(path.toFile().exists());
    }
}
