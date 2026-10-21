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
package org.pf4j.spring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

public class SpringPluginManagerTest {

    private AnnotationConfigApplicationContext context;

    @Configuration
    static class TestConfig {
        @Bean
        public SpringPluginManager pluginManager() {
            return new SpringPluginManager();
        }
    }

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.register(TestConfig.class);
        context.refresh();
    }

    @AfterEach
    void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    @Test
    void testPluginManagerLifecycle() {
        SpringPluginManager pluginManager = context.getBean(SpringPluginManager.class);
        assertNotNull(pluginManager);
        assertNotNull(pluginManager.getApplicationContext());
        assertSame(context, pluginManager.getApplicationContext());
    }

    @Test
    void testDestroyClosesPluginsGracefully() {
        SpringPluginManager pluginManager = context.getBean(SpringPluginManager.class);
        assertDoesNotThrow(pluginManager::destroy);
    }
}
