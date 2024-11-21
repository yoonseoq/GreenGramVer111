package com.green.greengramver1.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 빈등록을 위해서 객체화가 됨 .. @Component 만 해도됨
/*
   @Configuration:
 */
public class WebMvcConfiguration  implements WebMvcConfigurer {
    private final String uploadPath;

    public WebMvcConfiguration(@Value("${file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //우리가 가지고 있는 자료를 핸들링해서 추가한다는 말
        registry.addResourceHandler("/pic/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
    // RestController 의 모든 URl에 "/api" prefix 를 설정
        //  싱글톤 객체를 하나만 생성하도록 보장하는것
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
    } // addPathPrefix "api"를 앞쪽에다가 고정해라, RestController 애노테이션을 가지고 있는 클라스에다가 .class
}
