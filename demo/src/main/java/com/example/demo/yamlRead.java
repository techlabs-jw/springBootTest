package com.example.demo;

import com.example.demo.loadYamlClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:cert.yml", factory = loadYamlClass.class)
@ConfigurationProperties(prefix = "cert")
public class yamlRead {
    private String path;

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
