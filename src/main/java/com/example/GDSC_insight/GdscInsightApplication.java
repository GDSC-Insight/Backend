package com.example.GDSC_insight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GdscInsightApplication {

	public static void main(String[] args) {
		SpringApplication.run(GdscInsightApplication.class, args);
	}

}
