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
package org.pf4j.spring.boot;

import org.pf4j.PluginManager;
import org.pf4j.RuntimeMode;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot AutoConfiguration for PF4J.
 *
 * @author Decebal Suiu
 */
@AutoConfiguration
@ConditionalOnClass(SpringPluginManager.class)
@EnableConfigurationProperties(Pf4jProperties.class)
public class Pf4jAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(PluginManager.class)
    public SpringPluginManager pluginManager(Pf4jProperties properties) {
        if (properties.getMode() != null) {
            System.setProperty("pf4j.mode", properties.getMode().toString());
        }

        return new SpringPluginManager(properties.getPluginsPath()) {
            @Override
            public RuntimeMode getRuntimeMode() {
                if (properties.getMode() != null) {
                    return properties.getMode();
                }
                return super.getRuntimeMode();
            }
        };
    }
}
