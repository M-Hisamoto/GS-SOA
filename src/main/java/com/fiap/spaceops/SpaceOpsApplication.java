package com.fiap.spaceops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpaceOpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceOpsApplication.class, args);
    }
}
