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
//  - Create CustomUserDetails
//  - Username(email) verification
//  - CSRF 403 handling
//  - Autocomplete of username and password
//  - I18N
//  - Add DB configuration management tool
