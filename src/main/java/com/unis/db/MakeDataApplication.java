package com.unis.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xuli
 * @date 2019/3/12
 */
@EnableScheduling
@SpringBootApplication
public class MakeDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakeDataApplication.class, args);
    }

}
