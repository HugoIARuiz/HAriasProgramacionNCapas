package com.digis01.HAriasProgramacionNCapas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.digis01.HAriasProgramacionNCapas.*"})
@SpringBootApplication
public class HAriasProgramacionNCapasApplication {

	public static void main(String[] args) {
		SpringApplication.run(HAriasProgramacionNCapasApplication.class, args);
	}

}
