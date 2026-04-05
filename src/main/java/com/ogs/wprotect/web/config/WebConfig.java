package com.ogs.wprotect.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private DeviceIdInterceptor deviceIdInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(deviceIdInterceptor)
            .addPathPatterns("/w/**")
            .excludePathPatterns(
                "/w/users/save",
                "/w/users/save/",
                "/w/users/login",
                "/w/users/email/**"
            ); // Permite registro, login y búsqueda por email sin validación de headers
    }
}