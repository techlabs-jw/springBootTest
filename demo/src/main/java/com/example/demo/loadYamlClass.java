package com.example.demo;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class loadYamlClass implements PropertySourceFactory {
    String sourceName = "";
    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        Properties propertiesFromYaml = loadYamlProperties(resource);
        sourceName = (name != null) ? name: resource.getResource().getFilename();
        return new PropertiesPropertySource(sourceName, propertiesFromYaml);
    }

    public  Properties loadYamlProperties(EncodedResource resource) throws  FileNotFoundException {
        try {
            YamlPropertiesFactoryBean facotry = new YamlPropertiesFactoryBean();
            facotry.setResources(resource.getResource());
            facotry.afterPropertiesSet();
            return facotry.getObject();
        } catch (IllegalStateException e) {
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException)
                throw (FileNotFoundException) e.getCause();
            throw e;
        }

    }
}
