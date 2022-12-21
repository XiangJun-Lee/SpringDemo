package com.example.springdemo.a04;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author leelixiangjun
 * @date 2022/12/21 17:31
 */
/*
 java.home =
 java.version =
 */
@ConfigurationProperties(prefix = "java")
public class Bean4 {

    private String home;

    private String version;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Bean4{" +
                "home='" + home + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
