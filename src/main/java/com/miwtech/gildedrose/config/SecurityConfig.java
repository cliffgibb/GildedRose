package com.miwtech.gildedrose.config;

import com.miwtech.gildedrose.entity.UserEntity;
import com.miwtech.gildedrose.security.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DetailsService detailsService;

    public SecurityConfig(DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(UserEntity.PASSWORD_ENCODER);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
        .antMatchers("/hello").permitAll()
                    .antMatchers("/inventory").permitAll()
                    .antMatchers("/purchase/**").authenticated()
                .and()
                    .httpBasic()
                .and()
                    .csrf().disable();
    }
}