package io.github.admachiaveli.divideaifrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DivideAiFrontendApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DivideAiFrontendApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DivideAiFrontendApplication.class);
	}

}
