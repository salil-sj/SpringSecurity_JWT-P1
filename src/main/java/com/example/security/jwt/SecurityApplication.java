package com.example.security.jwt;

import com.example.security.jwt.entity.User;
import com.example.security.jwt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication implements CommandLineRunner
{

	@Autowired
	private IUserService userService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Run method started..........");

		User user =  userService.findByUserId(1);
		System.out.println(user);

	}

	public static void main(String[] args)
	{
		SpringApplication.run(SecurityApplication.class, args);


	}

}
