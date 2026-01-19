package com.example.demo.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //업로드경로
    @Value("${uploadPath}")
    String uploadPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

                                    // html상 보여지는 경로
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);    //실제 경로

    }
}
