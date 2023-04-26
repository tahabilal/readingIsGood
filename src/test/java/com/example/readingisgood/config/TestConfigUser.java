package com.example.readingisgood.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
public class TestConfigUser {

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("$2a$12$FSyIuQ/Ptegk5O4ZDT1DxeUzIiPd/Xv8wUu75nZ/HBCknL90PDR12")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
