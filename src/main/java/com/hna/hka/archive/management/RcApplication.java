package com.hna.hka.archive.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.hna.hka.archive.management.*.dao")
@EnableOpenApi
@EnableScheduling
public class RcApplication {

    public static void main(String[] args) {
        SpringApplication.run(RcApplication.class, args);
    }

}
