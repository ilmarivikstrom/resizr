package org.tudelft.cloud.resizr.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "org.tudelft.cloud.resizr")
@EnableJpaRepositories("org.tudelft.cloud.resizr.repository")
@EntityScan("org.tudelft.cloud.resizr.model")
@EnableScheduling
public class ResizrApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResizrApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
