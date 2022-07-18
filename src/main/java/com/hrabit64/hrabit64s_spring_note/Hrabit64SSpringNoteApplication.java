package com.hrabit64.hrabit64s_spring_note;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;


@SpringBootApplication
public class Hrabit64SSpringNoteApplication {
    //http://localhost:8080/swagger-ui/index.html
    public static void main(String[] args) {
        SpringApplication.run(Hrabit64SSpringNoteApplication.class, args);
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return p -> {
            p.setOneIndexedParameters(true);
        };
    }

}
