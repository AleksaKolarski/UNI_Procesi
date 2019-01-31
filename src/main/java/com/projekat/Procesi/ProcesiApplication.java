package com.projekat.Procesi;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class ProcesiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcesiApplication.class, args);
	}

}

