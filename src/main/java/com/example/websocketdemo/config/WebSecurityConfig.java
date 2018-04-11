package com.example.websocketdemo.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends
        WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .csrf()
            .ignoringAntMatchers("/ws/**")
            .and()
            .headers()
            .frameOptions().sameOrigin()
            .and()
            .authorizeRequests()
            .antMatchers("/ws/**").permitAll()
    ;
  }
}