package com.libcentro.demo.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class mainapp {

	public static void main(String[] args) {
		SpringApplication.run(mainapp.class, args);

		System.out.println("Testing, launching the application...");
		System.out.println("If u read this message, means mainapp.java is running correctly");
		
	}

}
