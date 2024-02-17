package com.bazin.test.config;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

 @Slf4j
public abstract class BatchUtils {

    static String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
    static String appConfigPath = rootPath + "application.properties";


    public static String getProperty(String propertyName) throws IOException {
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
        String property = appProps.getProperty(propertyName);
        if (property == null) {
            log.error("Propriété introuvable : {}", propertyName);
        }
        return property;
    }



}
