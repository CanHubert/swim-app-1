package com.can.app.swim.swimapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SwimAppApplication {

	public static void main(String[] args) {
		try
		{
		SpringApplication.run(SwimAppApplication.class, args);

		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
