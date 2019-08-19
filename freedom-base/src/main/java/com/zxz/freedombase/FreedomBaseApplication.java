package com.zxz.freedombase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.zxz.freedombase", "com.zxz.freedomlogging"})
public class FreedomBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreedomBaseApplication.class, args);
    }

}
