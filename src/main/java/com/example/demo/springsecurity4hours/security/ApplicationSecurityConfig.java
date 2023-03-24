package com.example.demo.springsecurity4hours.security;

import com.example.demo.springsecurity4hours.auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.example.demo.springsecurity4hours.security.ApplicationUserPermission.*;
import static com.example.demo.springsecurity4hours.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationUserService applicationUserService;
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http    .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/","index","/css/*","/js/*")
                .permitAll()
                .requestMatchers("/api/**").hasRole(STUDENT.name())
                .requestMatchers(HttpMethod.DELETE,"/management/api/**")
                .hasAuthority(COURSE_WRITE.getPermission())
                .requestMatchers(HttpMethod.POST,"/management/api/**")
                .hasAuthority(COURSE_WRITE.getPermission())
                .requestMatchers(HttpMethod.PUT,"/management/api/**")
                .hasAuthority(COURSE_WRITE.getPermission())
                .requestMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .rememberMe();
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

   public DaoAuthenticationProvider daoAuthenticationProvider () {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
   }




}
