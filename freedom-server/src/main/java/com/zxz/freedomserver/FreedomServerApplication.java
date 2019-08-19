package com.zxz.freedomserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class FreedomServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreedomServerApplication.class, args);
    }

}
