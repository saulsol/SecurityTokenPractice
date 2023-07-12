package com.example.securitytokenpractice.config;

import com.example.securitytokenpractice.security.APIUserDetailsService;
import com.example.securitytokenpractice.security.filter.APILoginFilter;
import com.example.securitytokenpractice.security.handler.APILoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class CustomSecurityConfig {

    private final APIUserDetailsService apiUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        log.info("__________________web configure___________________");

        return (web) -> web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception{
        log.info("__________________configuration___________________");

        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(apiUserDetailsService)
                        .passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager
                    = authenticationManagerBuilder.build();

        http.authenticationManager(authenticationManager);

        //APILogInFilter
        APILoginFilter apiLoginFilter = new APILoginFilter("/generateToken");
        apiLoginFilter.setAuthenticationManager(authenticationManager);

        APILoginSuccessHandler successHandler = new APILoginSuccessHandler();
        apiLoginFilter.setAuthenticationSuccessHandler(successHandler);

        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);


        http.csrf().disable(); // CSRF 토큰 비활성화
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션을 사용하지 않음

        return http.build();
    }

}
