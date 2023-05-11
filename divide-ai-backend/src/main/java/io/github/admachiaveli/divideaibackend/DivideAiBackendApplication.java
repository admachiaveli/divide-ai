package io.github.admachiaveli.divideaibackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DivideAiBackendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DivideAiBackendApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DivideAiBackendApplication.class);
    }

//    @Bean
//    public Filter getCharacterEncodingFilter() {
//
//        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
//
//        encodingFilter.setEncoding("UTF-8");
//        encodingFilter.setForceEncoding(true);
//
//        return encodingFilter;
//
//    }

}
