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

import org.pf4j.RuntimeMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration properties for PF4J Spring Boot integration.
 *
 * @author Decebal Suiu
 */
@ConfigurationProperties(prefix = "pf4j")
public class Pf4jProperties {

    /**
     * Path to the directory where plugins are located.
     * Defaults to "plugins".
     */
    private String pluginsDir = "plugins";

    /**
     * Whether to automatically start plugins when initialized.
     * Defaults to true.
     */
    private boolean autoStartPlugins = true;

    /**
     * PF4J runtime mode: DEVELOPMENT or DEPLOYMENT.
     */
    private RuntimeMode mode;

    public String getPluginsDir() {
        return pluginsDir;
    }

    public void setPluginsDir(String pluginsDir) {
        this.pluginsDir = pluginsDir;
    }

    public Path getPluginsPath() {
        return Paths.get(pluginsDir);
    }

    public boolean isAutoStartPlugins() {
        return autoStartPlugins;
    }

    public void setAutoStartPlugins(boolean autoStartPlugins) {
        this.autoStartPlugins = autoStartPlugins;
    }

    public RuntimeMode getMode() {
        return mode;
    }

    public void setMode(RuntimeMode mode) {
        this.mode = mode;
    }
}
