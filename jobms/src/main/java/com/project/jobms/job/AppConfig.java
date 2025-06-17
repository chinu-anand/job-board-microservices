package com.project.jobms.job;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/*
* Using this instead of normal RestTemplate because this load balanced bean can
* communicate with the Eureka server's registry and resolve names properly.
*
*
* Update - Replaced its uses with the OpenFeign Client which has much better integration with
* Eureka server and works seamless with lot less boilerplate codes
*
* */


@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
