package com.microgram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.avatars:src/main/resources/static/avatars}")
    private String avatarsDir;

    @Value("${app.upload.posts:src/main/resources/static/posts}")
    private String postsDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("/avatars/**")
                .addResourceLocations(
                        "file:" + avatarsDir + "/",
                        // Дефолтный аватар берём из static
                        "classpath:/static/avatars/"
                );

        registry
                .addResourceHandler("/posts/**")
                .addResourceLocations(
                        "file:" + postsDir + "/"
                );

        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}