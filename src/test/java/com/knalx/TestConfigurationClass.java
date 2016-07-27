package com.knalx;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.knalx.dao","com.knalx.service" })
@Configuration
public class TestConfigurationClass {
}
