package com.sis.car;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Congiguration {

        @Bean
        public <SpringDataDialect> SpringDataDialect springDataDialect() {
            return new SpringDataDialect();
        }
    }
}
