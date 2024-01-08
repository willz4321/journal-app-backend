package com.facundosuarez.journal.journalapp.models.entity.security;

import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private jwt jwtUtiles;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtRequestFilter  filter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception{

            jwtAuthenticationFilter authenticationFilter = new jwtAuthenticationFilter(jwtUtiles);
            System.out.println(" /////////////////////////////atutenticad: " + authenticationFilter);
            authenticationFilter.setAuthenticationManager(authenticationManager);
            authenticationFilter.setFilterProcessesUrl("/auth/login");

            return http
                    .cors() // Agrega esto para habilitar la configuración CORS
                    .and()
                    .csrf().disable()
                    .authorizeRequests()
                    .requestMatchers("/auth/**")
                    .permitAll()
                    .requestMatchers("/journal/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilter(authenticationFilter)
                    .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("https://willz4321.github.io"));
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
            configuration.setAllowedHeaders(Arrays.asList("*"));
            configuration.setAllowCredentials(true);
        
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
        
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception{
        
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(customUserDetailsService)
                   .passwordEncoder(passwordEncoder)
                   .and()
                   .build();                   
    }  
}