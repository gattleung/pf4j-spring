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

import org.junit.jupiter.api.Test;
import org.pf4j.PluginManager;
import org.pf4j.RuntimeMode;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class Pf4jAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(Pf4jAutoConfiguration.class));

    @Test
    void defaultConfigurationCreatesPluginManager() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(PluginManager.class);
            assertThat(context).hasSingleBean(SpringPluginManager.class);

            SpringPluginManager pluginManager = context.getBean(SpringPluginManager.class);
            assertThat(pluginManager.getPluginsRoots()).contains(Paths.get("plugins"));
            assertThat(pluginManager.getApplicationContext()).isSameAs(context.getSourceApplicationContext());
        });
    }

    @Test
    void customPropertiesAreRespected() {
        contextRunner
                .withPropertyValues("pf4j.plugins-dir=custom-plugins", "pf4j.mode=DEVELOPMENT")
                .run(context -> {
                    assertThat(context).hasSingleBean(SpringPluginManager.class);
                    SpringPluginManager pluginManager = context.getBean(SpringPluginManager.class);
                    assertThat(pluginManager.getPluginsRoots()).contains(Paths.get("custom-plugins"));
                    assertThat(pluginManager.getRuntimeMode()).isEqualTo(RuntimeMode.DEVELOPMENT);
                });
    }

    @Test
    void userDefinedPluginManagerBacksOffAutoConfiguration() {
        contextRunner
                .withUserConfiguration(CustomPluginManagerConfig.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(PluginManager.class);
                    assertThat(context.getBean(PluginManager.class)).isSameAs(context.getBean("customPluginManager"));
                });
    }

    @Configuration(proxyBeanMethods = false)
    static class CustomPluginManagerConfig {
        @Bean
        public SpringPluginManager customPluginManager() {
            return new SpringPluginManager(Paths.get("my-custom-path"));
        }
    }
}
