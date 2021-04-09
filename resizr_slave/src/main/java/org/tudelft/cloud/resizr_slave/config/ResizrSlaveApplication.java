package org.tudelft.cloud.resizr_slave.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "org.tudelft.cloud.resizr_slave")
@EnableJpaRepositories("org.tudelft.cloud.resizr_slave.repository")
@EntityScan("org.tudelft.cloud.resizr_slave.model")
@EnableScheduling
public class ResizrSlaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResizrSlaveApplication.class, args);
	}

}
