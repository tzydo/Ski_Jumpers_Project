package com.pl.ski_jumping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class SkiJumpingPageApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkiJumpingPageApplication.class, args);
	}
}
