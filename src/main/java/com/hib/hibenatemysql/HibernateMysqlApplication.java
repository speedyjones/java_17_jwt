package com.hib.hibenatemysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HibernateMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateMysqlApplication.class, args);
    }

}