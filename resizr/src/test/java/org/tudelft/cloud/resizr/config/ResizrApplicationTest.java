package org.tudelft.cloud.resizr.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@EnableJpaRepositories("org.tudelft.cloud.resizr.repository")
@EntityScan("org.tudelft.cloud.resizr.model")
@ComponentScan("org.tudelft.cloud.resizr.service")
public class ResizrApplicationTest {


    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
