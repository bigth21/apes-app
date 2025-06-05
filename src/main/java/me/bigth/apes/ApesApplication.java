package me.bigth.apes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApesApplication.class, args);
    }

}
// TODO
//  - CSRF 403 handling
//  - Add sign-up funnels
//  - Autocomplete of username and password
//  - I18N
//  - Add DB configuration management tool
