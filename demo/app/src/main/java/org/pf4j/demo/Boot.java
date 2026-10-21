/*
 * Copyright (C) 2012-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pf4j.demo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A Spring Boot 4 class that starts the demo.
 *
 * @author Decebal Suiu
 */
@SpringBootApplication
public class Boot implements CommandLineRunner {

    private final Greetings greetings;

    @Autowired
    public Boot(Greetings greetings) {
        this.greetings = greetings;
    }

    public static void main(String[] args) {
        // print logo
        printLogo();

        // start Spring Boot 4 application
        SpringApplication.run(Boot.class, args);
    }

    @Override
    public void run(String... args) {
        // retrieves automatically the extensions for the Greeting.class extension point
        greetings.printGreetings();
    }

    private static void printLogo() {
        System.out.println(StringUtils.repeat("#", 40));
        System.out.println(StringUtils.center("PF4J-SPRING (BOOT 4)", 40));
        System.out.println(StringUtils.repeat("#", 40));
    }

}
