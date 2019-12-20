package com.sfhuskie.plugins.aggregator.junit;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

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
public class TestCase {
    @JacksonXmlProperty(isAttribute = true)
    String classname;
    @JacksonXmlProperty(isAttribute = true)
    String name;
    @JacksonXmlProperty(isAttribute = true)
    Float time;

    //@JacksonXmlElementWrapper(useWrapping = false)   
    //@JacksonXmlProperty(localName="error")
    @JacksonXmlProperty(isAttribute = true)
    Error error;
    
    //@JacksonXmlElementWrapper(useWrapping = false)   
    //@JacksonXmlProperty(localName="failure")\
    @JacksonXmlProperty(isAttribute = true)
    Failure failure;
    
    public String getClassname() {
        return classname;
    }
    public void setClassname(String classname) {
        this.classname = classname;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Float getTime() {
        return time;
    }
    public void setTime(Float time) {
        this.time = time;
    }
    public Error getError() {
        return error;
    }
    public void setError(Error error) {
        this.error = error;
    }
    public Failure getFailure() {
        return failure;
    }
    public void setFailure(Failure failure) {
        this.failure = failure;
    }
    
}
