package com.hrabit64.hrabit64s_spring_note.config;

import com.hrabit64.hrabit64s_spring_note.config.Security.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable()
        .and()
                .authorizeRequests()
                .antMatchers("/","/css/**","/images/**","/js/**","/error","/registration","/posts/**","/categories/**").permitAll()
                .antMatchers("/api/v1/**","/post/**").hasRole("OWNER")
                .anyRequest().authenticated()
        .and()
                .logout()
                .logoutSuccessUrl("/")
        .and()
            .oauth2Login()
            .defaultSuccessUrl("/")
            .userInfoEndpoint()
            .userService(customOAuth2UserService);

    }

}
