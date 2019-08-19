package com.zxz.freedomgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class FreedomGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreedomGatewayApplication.class, args);
    }

}
