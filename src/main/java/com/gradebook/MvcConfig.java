package com.gradebook;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("layouts/home");
        registry.addViewController("/").setViewName("layouts/home");
        registry.addViewController("/login").setViewName("fragments/forms/login");
        registry.addViewController("/mainpage").setViewName("layouts/mainpage");
        registry.addViewController("/info").setViewName("fragments/userprofile");
        registry.addViewController("/grades").setViewName("fragments/grades");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");
    }
}