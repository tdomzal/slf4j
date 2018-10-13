/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
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

package org.apache.log4j;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * <p>
 * This class is a minimal implementation of the original
 * <code>org.apache.log4j.Logger</code> class (as found in log4j 1.2) 
 * delegating all calls to a {@link org.slf4j.Logger} instance.
 * </p>
 *
 * @author Ceki G&uuml;lc&uuml; 
 * */
@SuppressWarnings("rawtypes")
public class Logger extends Category {

    private static final String LOGGER_FQCN = Logger.class.getName();

    protected Logger(String name) {
        super(name);
    }

    public static Logger getLogger(String name) {
        return Log4jLoggerFactory.getLogger(name);
    }

    public static Logger getLogger(String name, LoggerFactory loggerFactory) {
        return Log4jLoggerFactory.getLogger(name, loggerFactory);
    }

    public static Logger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }

    /**
     * Does the obvious.
     * 
     * @return
     */
    public static Logger getRootLogger() {
        return Log4jLoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
    }

    /**
     * Delegates to {@link org.slf4j.Logger#isTraceEnabled} 
     * method of SLF4J.
     */
    public boolean isTraceEnabled() {
        return slf4jLogger.isTraceEnabled();
    }

    /**
     * Delegates to {@link org.slf4j.Logger#trace(String)} method in SLF4J.
     */
    public void trace(Object message) {
        differentiatedLog(null, LOGGER_FQCN, LocationAwareLogger.TRACE_INT, message, null);
    }

    /**
     * Delegates to {@link org.slf4j.Logger#trace(String,Throwable)} 
     * method in SLF4J.
     */
    public void trace(Object message, Throwable t) {
        differentiatedLog(null, LOGGER_FQCN, LocationAwareLogger.TRACE_INT, message, null);
    }

    /**
     * (Imported from log4j source code and adapted)
     */
    public ResourceBundle getResourceBundle() {
        for (Category c = this; c != null; c = c.getParent()) {
            if (c.resourceBundle != null) {
                return c.resourceBundle;
            }
        }
        // It might be the case that there is no resource bundle
        return null;
    }

    /**
     * (Imported from log4j source code and adapted)
     */
    protected String getResourceBundleString(String key) {
        ResourceBundle rb = getResourceBundle();
        // This is one of the rare cases where we can use logging in order
        // to report errors from within log4j.
        if (rb == null) {
            //if(!hierarchy.emittedNoResourceBundleWarning) {
            //error("No resource bundle has been set for category "+name);
            //hierarchy.emittedNoResourceBundleWarning = true;
            //}
            return null;
        } else {
            try {
                return rb.getString(key);
            } catch (MissingResourceException mre) {
                error("No resource is associated with key \"" + key + "\".");
                return null;
            }
        }
    }

    /**
     * (Imported from log4j source code and adapted)
     */
    public void l7dlog(Priority priority, String key, Object[] params, Throwable t) {
        if (isEnabledFor(priority)) {
            String pattern = getResourceBundleString(key);
            String msg;
            if (pattern == null) {
                msg = key;
            } else {
                msg = java.text.MessageFormat.format(pattern, params);
            }
            forcedLog(LOGGER_FQCN, priority, msg, t);
        }
    }

}
