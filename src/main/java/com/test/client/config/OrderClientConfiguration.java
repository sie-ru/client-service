package com.test.client.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class OrderClientConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
