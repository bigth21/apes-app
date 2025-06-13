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
//  - Username(email) verification
//      - When add authenticationFailureHandler, make custom exception extend AccountStatusException
//  - Session timeout handling
//  - Autocomplete of username and password
//  - Add DB configuration management tool
