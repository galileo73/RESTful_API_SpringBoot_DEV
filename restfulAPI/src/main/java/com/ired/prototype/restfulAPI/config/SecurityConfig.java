package com.ired.prototype.restfulAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/**").authenticated() // Secure all /api/** endpoints
                )
                .httpBasic(Customizer.withDefaults()) // Enable basic authentication
                .formLogin(form -> form.disable()) // Disable form-based login
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for secure password hashing
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        // Define a user with a securely hashed password
        UserDetails user = User.withUsername("admin")
                .password(encoder.encode("admin1234")) // Hash the password using BCrypt
                .roles("USER") // Assign a role
                .build();

        return new InMemoryUserDetailsManager(user); // Add the user to the in-memory manager
    }
}