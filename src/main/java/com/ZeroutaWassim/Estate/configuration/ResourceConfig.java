package com.ZeroutaWassim.Estate.configuration;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

 

@Configuration

public class ResourceConfig implements WebMvcConfigurer {

    @Override

    // Serves uploaded files via "/uploads/**"
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:src/main/resources/static/uploads/")
                .setCachePeriod(3600);

    }

}