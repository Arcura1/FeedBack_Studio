package org.example.feedbackstudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FeedBackStudioApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedBackStudioApplication.class, args);
	}

}
