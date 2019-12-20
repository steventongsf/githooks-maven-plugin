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
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class TestSuite {
    @JacksonXmlProperty(isAttribute = true)
    int tests;
    @JacksonXmlProperty(isAttribute = true)
    int failures;
    @JacksonXmlProperty(isAttribute = true)
    String name;
    @JacksonXmlProperty(isAttribute = true)
    int errors;
    @JacksonXmlProperty(isAttribute = true)
    int skipped;
    @JacksonXmlProperty(isAttribute = true)
    float time;
    List<Property> properties;
    
    @JacksonXmlElementWrapper(useWrapping = false)   
    @JacksonXmlProperty(localName="testcase")
    List<TestCase> testcase;

    public TestSuite() {
        
    }
    
    public List<Property> getProperties() {
        return properties;
    }
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
    
    public List<TestCase> getTestcase() {
        return testcase;
    }
    public void setTestcase(List<TestCase>testcase) {
        this.testcase = testcase;
    }

    public int getFailures() {
        return failures;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getErrors() {
        return errors;
    }
    public void setErrors(int errors) {
        this.errors = errors;
    }
    public int getSkipped() {
        return skipped;
    }
    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }
    public float getTime() {
        return time;
    }
    public void setTime(float time) {
        this.time = time;
    }
    public void setFailures(int failures) {
        this.failures = failures;
    }
    public int getTests() {
        return tests;
    }
    public void setTests(int tests) {
        this.tests = tests;
    }
    
}
