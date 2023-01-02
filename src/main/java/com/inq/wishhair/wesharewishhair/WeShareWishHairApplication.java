package com.inq.wishhair.wesharewishhair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WeShareWishHairApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeShareWishHairApplication.class, args);
	}

}
