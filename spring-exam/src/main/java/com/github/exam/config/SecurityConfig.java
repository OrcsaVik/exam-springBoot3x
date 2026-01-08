package com.github.exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // CSRF is for sessions. We are testing an API. Kill it.
            .csrf(csrf -> csrf.disable())
            
            // The Rule: /hello is private. Everything else is public (or decide otherwise).
            // Do not complicate this with overly specific matchers yet.
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/hello").authenticated()
                .anyRequest().permitAll()
            )
            
            // Standard Basic Auth. Simple headers.
            .httpBasic(withDefaults())
            .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Hardcoding credentials is bad practice in prod, but per your spec 
        // for this "Hello World" integration test, it is the simplest path.
        var user = User.withUsername("test")
            .password("{noop}123456") // {noop} tells Spring "don't decode this".
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}