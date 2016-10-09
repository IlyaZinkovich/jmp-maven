package com.epam.jmp.maven;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.epam.jmp.maven.service")
@Import(RepositoryConfig.class)
public class ServiceConfig {
}
