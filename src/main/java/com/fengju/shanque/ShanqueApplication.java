package com.fengju.shanque;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.fengju.shanque.mapper")
@SpringBootApplication
public class ShanqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShanqueApplication.class, args);
    }

}
