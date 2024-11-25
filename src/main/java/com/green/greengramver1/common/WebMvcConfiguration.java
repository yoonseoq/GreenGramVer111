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
   @Configuration: 해당 클래스가 빈 설정하는 클래스인것을 말한다.
    이 클래스는 컨테이너에 객체를 정의하고 설정하는데 사용된다.
    @Configuration 이 붙은 클래스는 주로 애플리케이션의 설정 정보를 포함하고 있으며 자바기반의 설정을 통해
    스프링의 빈들을 정의할 수 있다.
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
