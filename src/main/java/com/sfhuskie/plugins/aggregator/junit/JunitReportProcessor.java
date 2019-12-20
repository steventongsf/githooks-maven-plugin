package com.sfhuskie.plugins.aggregator.junit;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.shared.utils.io.FileUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sfhuskie.plugins.aggregator.io.FileFinder;
import com.sfhuskie.plugins.aggregator.io.FinderFilter;

public class JunitReportProcessor implements FinderFilter{
    File searchDir; 
    String directoryPattern; 
    File outputDir;
    String filePrefix = "TEST-";
    String fileExtension = ".xml";
    String targetFileName = "junit-results.xml";
    int returnCode = 0;
    XmlMapper xmlMapper = new XmlMapper();
    
    public JunitReportProcessor(File searchDir, String directoryPattern, File outputDir) {
        this.searchDir = searchDir;
        this.directoryPattern = directoryPattern;
        this.outputDir = outputDir;
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    public String getFilePrefix() {
        return filePrefix;
    }
    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }
    public String getFileExtension() {
        return fileExtension;
    }
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
    
    public File getSearchDir() {
        return searchDir;
    }
    public String getDirectoryPattern() {
        return directoryPattern;
    }
    public File getOutputDir() {
        return outputDir;
    }
    public String getTargetFileName() {
        return targetFileName;
    }
    public boolean isValidFile( File file) {
        String name = file.getName();
        return (!file.isDirectory() 
                && name.endsWith(this.fileExtension)
                && name.startsWith(this.filePrefix)
                && this.directoryPatternMatch(file)); 
    }
    public boolean directoryPatternMatch(File file) {
        String s = file.getAbsolutePath().replace("\\", "/");
        return (s.contains(this.directoryPattern));
    }
    public List<File> findJUnitXmlFiles() throws IOException {    
        Path basePath = Paths.get(searchDir.getAbsolutePath());
        int maxDepth = 20;
        FileFinder ff = new FileFinder(basePath.toFile());
        ff.setFinderFilter(this);
        List<File> files = ff.find();
        return files;
    }
    public TestSuite getTestSuite(String fileName) throws JsonParseException, JsonMappingException, IOException {
        File file = new File(fileName);
        String contents = FileUtils.fileRead(file);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return (TestSuite)xmlMapper.readValue(contents, TestSuite.class);
    }
    public void processJUnitResults(
                boolean copyFiles, 
                boolean summarize) throws IOException {
        
        // Find report files
        List<File> xmlFiles = findJUnitXmlFiles();
        // TODO Parse files
        if (copyFiles) {
            this.copyXmlFiles(xmlFiles);
        }
        if (summarize) {
            // TODO summarize and write file
            Map<String,TestSuite> testSuites = getTestSuitesFromFiles(xmlFiles);
            this.writeTestSuitesToFile(testSuites);
        }
        
    }
    public void copyXmlFiles(List<File> stream) {
        // TODO
        throw new RuntimeException("Not implemented yet");
    }

    public Map<String,TestSuite> getTestSuitesFromFiles(List<File> files) {
        Map<String,TestSuite> testSuites = new HashMap<String,TestSuite>();
//        // xml files to Pojo
        for (File file:files) {
            String f = file.getAbsolutePath();
            try {
                testSuites.put(f, this.getTestSuite(f));
            } 
            catch (Exception e) {
                //throw new RuntimeException("Failed transforming file "+f);
                console("ERROR Failed to convert XML to pojos from "+file.getAbsolutePath());
                this.returnCode++;
            } 
        }
        return testSuites;
    }
    void writeTestSuitesToFile(Map<String,TestSuite> testSuites) throws IOException {
        FileUtils.forceMkdir(this.outputDir);
        Path path = Paths.get(this.outputDir+"/"+this.targetFileName);
        FileUtils.forceDelete(path.toFile());
        Files.write(path, "".getBytes(), StandardOpenOption.CREATE);
        for (String fileName:testSuites.keySet()) {
            TestSuite t = testSuites.get(fileName);
            String str = this.xmlMapper.writeValueAsString(t);
            byte[] strToBytes = str.getBytes();
            Files.write(path, strToBytes, StandardOpenOption.APPEND);
        }
    }
    public int getReturnCode() {
        return this.returnCode;
    }
    static void console(String m) {
        System.out.println(m);
    }
}
