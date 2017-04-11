package com.bao.config;

/*
 * #%L
 * ff4j-sample-springboot
 * %%
 * Copyright (C) 2013 - 2016 FF4J
 * %%
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
 * #L%
 */


import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.web.ApiConfig;
import org.ff4j.web.embedded.ConsoleServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FF4jConfiguration {
 
    @Value("${ff4j.webapi.authentication}")
    private boolean authentication = false;
    
    @Value("${ff4j.webapi.authorization}")
    private boolean authorization = false;
    
    @Bean
    public FF4j ff4j() {

        return new FF4j().createFeature("test1",true).createFeature("test2",false);

    //    return new FF4j(getClass().getClassLoader().getResourceAsStream("ff4j.xml"));
//            .createFeature("f1")
//            .createFeature("f2").createFeature("f3")
//            .createProperty(new PropertyString("SampleProperty", "go!"))
//            .createProperty(new PropertyInt("SamplePropertyIn", 12));
    }

    
    @Bean
    public ConsoleServlet consoleServlet() {
        ConsoleServlet cs = new ConsoleServlet();
        cs.setFf4j(ff4j());
        return cs;
    }
//
//    @Bean
//    public ApiConfig getApiConfig() {
//        ApiConfig apiConfig = new ApiConfig();
//        apiConfig.setAuthenticate(authentication);
//        apiConfig.setAutorize(authorization);
//        apiConfig.setFF4j(getFF4j());
//        return apiConfig;
//    }

}
