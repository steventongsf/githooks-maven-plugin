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
import java.util.HashMap;
import java.util.Map;

public class JUnitResults {
    Map<String,TestSuite> results = new HashMap<String,TestSuite>();
    public void add(String key, TestSuite value) {
        this.results.put(key, value);
    }
    public Map<String,TestSuite> getResults() {
        return this.results;
    }
}
