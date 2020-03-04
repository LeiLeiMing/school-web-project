package com.secondhand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by LeiMing on 2020/3/2 13:59
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class TransactionApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class);
    }
}
