package com.example.family_ording;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.family_ording.mapper")
public class FamilyOrdingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FamilyOrdingApplication.class, args);
    }

}
