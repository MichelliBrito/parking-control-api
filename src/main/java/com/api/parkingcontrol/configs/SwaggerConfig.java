package com.api.parkingcontrol.configs;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.api"))
				.paths(regex("/api.*"))
				.build()
				.apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {
		
		ApiInfo apiInfo = new ApiInfo(
				"PARKING CONTROL API", 
				"api parking spot control demo", 
				"0.0.1-SNAPSHOT", 
				"Contact US", 
				new Contact(
						"GNU Community development",
						"www.google.com", 
						"user@mail.com"), 
						"Spring Project Framework", 
						"https://spring.io", 
						new ArrayList<VendorExtension>()
				);
		return apiInfo;
	}
}
