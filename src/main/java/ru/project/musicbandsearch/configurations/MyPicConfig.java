package ru.project.musicbandsearch.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
public class MyPicConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println( "Файл конфигурации вступил в силу");
        registry.addResourceHandler("/img/**").addResourceLocations("file:/tmp/images/").setCacheControl(CacheControl.noCache());
        super.addResourceHandlers(registry);
    }
}
