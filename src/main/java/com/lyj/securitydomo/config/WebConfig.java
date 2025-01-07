package com.lyj.securitydomo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 업로드된 파일을 /view/ 경로로 접근 가능하도록 매핑
        registry.addResourceHandler("/view/**")
                .addResourceLocations("file:/Users/sin-inseon/2024Workspace/plzProject-main/src/main/resources/static/upload/");

        // 이미지 리소스를 /images/ 경로로 접근 가능하도록 매핑
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");

        // JavaScript 파일을 /js/ 경로로 접근 가능하도록 매핑
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }

}
