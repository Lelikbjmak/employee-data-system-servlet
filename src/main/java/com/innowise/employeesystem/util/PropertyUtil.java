package com.innowise.employeesystem.util;

import lombok.experimental.UtilityClass;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

@UtilityClass
public class PropertyUtil {

    private static final Yaml instance = new Yaml();
    private static final String PROPERTY_SOURCE = "application.yaml";

    private static final Map<String, Object> properties;

    static {
        properties = loadProperties();
    }

    private static Map<String, Object> loadProperties() {
        try (var inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(PROPERTY_SOURCE)) {
            return instance.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getProperty(String propertyName) {
        Map<String, Object> certainProperties = (Map<String, Object>) properties.get(propertyName.split("\\.")[0]);
        String propertyKey = propertyName.split("\\.")[1];
        return certainProperties.get(propertyKey);
    }
}
