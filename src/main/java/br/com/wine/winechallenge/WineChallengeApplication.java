package br.com.wine.winechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@ImportResource("classpath:META-INF/spring/*.xml")
public class WineChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WineChallengeApplication.class, args);
	}

}
