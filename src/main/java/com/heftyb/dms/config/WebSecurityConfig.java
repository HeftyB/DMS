package com.heftyb.dms.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.MatcherSecurityWebFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.time.Duration;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

//        @Bean
//        public UserDetailsService userDetailsService() {
//            UserDetails userDetails = User.withDefaultPasswordEncoder()
//                    .username("user")
//                    .password("password")
//                    .roles("USER")
//                    .build();
//
//            return new InMemoryUserDetailsManager(userDetails);
//        }
}
