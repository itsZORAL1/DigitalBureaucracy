package com.JEEProjects.QuickFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = "com.JEEProjects.QuickFlow")

public class QuickFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickFlowApplication.class, args);
	}

}
