package com.ssafy.ibalance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IbalanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbalanceApplication.class, args);
	}

}
