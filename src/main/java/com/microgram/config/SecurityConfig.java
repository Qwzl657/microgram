package com.microgram.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(
                                PathPatternRequestMatcher
                                        .withDefaults()
                                        .matcher("/auth/logout")
                        )
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/auth/login?logout=true")
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/avatars/**",
                                "/posts/**",
                                "/h2-console/**"
                        ).permitAll()

                        .requestMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/auth/forgot-password",
                                "/auth/reset-password"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/users/**",
                                "/search"
                        ).permitAll()

                        .anyRequest().authenticated()
                );

        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}