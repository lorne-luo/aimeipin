package com.meidi.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class PomVersion {
//    final private static Logger LOGGER = LogManager.getLogger(PomVersion.class);

    final static String VERSION = loadVersion();

    private static String loadVersion() {
        Properties properties = new Properties();
        try {
            InputStream inStream = PomVersion.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(inStream);
        } catch (Exception e){
//            LOGGER.warn("Unable to load version.properties using PomVersion.class.getClassLoader().getResourceAsStream(...)", e);
            return "null";
        }
        return properties.getProperty("build.version");
    }

    public static String getVersion(){
        return VERSION;
    }
}
