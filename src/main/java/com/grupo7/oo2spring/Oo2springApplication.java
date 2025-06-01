package com.grupo7.oo2spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.grupo7.oo2spring")
@EnableJpaRepositories(basePackages = "com.grupo7.oo2spring.repositories")
@EntityScan(basePackages = "com.grupo7.oo2spring.models")
public class Oo2springApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oo2springApplication.class, args);
	}

}
