/*
 *	Copyright Technophobia Ltd 2012
 *
 *   This file is part of Substeps.
 *
 *    Substeps is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    Substeps is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with Substeps.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.technophobia.substeps.model;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;

import java.net.URL;
import java.util.function.Supplier;

/**
 * @author jbacon
 */
public enum Configuration {

    INSTANCE;

    private final Supplier<Config> configuration;

    Configuration() {

        configuration = () -> {

            // TODO: Remove the following block once the old format as been deprecated long enough.
            final Config systemProperties = ConfigFactory.systemProperties();

            // enable the old format by default, so the new style has to be explicitly enabled.
            if (!(systemProperties.hasPath("config.resource") || systemProperties.hasPath("config.file") || systemProperties.hasPath("config.url"))) {

                if (systemProperties.hasPath("environment")) {

                    // if we have a property set for 'environment' then use it
                    System.setProperty("config.resource", systemProperties.getString("environment"));

                } else {

                    // else we will not default to using localhost
                    System.setProperty("config.resource", "localhost");

                }
            }

            return ConfigFactory.load();
        };
    }

    /**
     * @return a formatted output of all properties including file origins in json format.
     */
    public String getConfigurationInfo() {
        return get().root().render();
    }


    /**
     * @return a configuration object containing all the properties.
     */
    public Config get() {
        return configuration.get();
    }

    /**
     * This method no longer has any affect due to immutability introduced into the new configuration style.
     *
     * @since 2.0.0
     * @deprecated due to the move to the introduction of property immutability.
     */
    @Deprecated
    public void addDefaultProperties(final URL url, final String name) {
    }

    /**
     * This method no longer has any affect due to immutability introduced into the new configuration style.
     *
     * @since 2.0.0
     * @deprecated due to the move to the introduction of property immutability.
     */
    @Deprecated
    public void addDefaultProperty(final String key, final Object value) {
    }


    /**
     * @param key property key
     * @return the property value as a String
     * @throws ConfigException.Missing   if value is absent or null
     * @throws ConfigException.WrongType if value is not convertible to a String
     * @since 2.0.0
     * @deprecated use {@link #get()} instead, there are many better getters available.
     */
    @Deprecated
    public String getString(final String key) {
        return get().getString(key);
    }


    /**
     * @param key property key
     * @return the property value as an int
     * @throws ConfigException.Missing   if value is absent or null
     * @throws ConfigException.WrongType if value is not convertible to an int
     * @since 2.0.0
     * @deprecated use {@link #get()} instead, there are many better getters available.
     */
    @Deprecated
    public int getInt(final String key) {
        return get().getInt(key);
    }


    /**
     * @param key property key
     * @return the property value as a long
     * @throws ConfigException.Missing   if value is absent or null
     * @throws ConfigException.WrongType if value is not convertible to a long
     * @since 2.0.0
     * @deprecated use {@link #get()} instead, there are many better getters available.
     */
    @Deprecated
    public long getLong(final String key) {
        return get().getLong(key);
    }


    /**
     * @param key property key
     * @return the property value as a boolean
     * @throws ConfigException.Missing   if value is absent or null
     * @throws ConfigException.WrongType if value is not convertible to a boolean
     * @since 2.0.0
     * @deprecated use {@link #get()} instead, there are many better getters available.
     */
    @Deprecated
    public boolean getBoolean(final String key) {
        return get().getBoolean(key);
    }
}
