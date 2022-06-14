package com.epam.postrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.epam.postrest.service.UserService;

@SpringBootApplication
public class PostRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostRestApplication.class, args);
	}

}
