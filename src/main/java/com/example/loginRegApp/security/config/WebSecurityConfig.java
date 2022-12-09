package com.example.loginRegApp.security.config;

import com.example.loginRegApp.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@AllArgsConstructor

public class WebSecurityConfig{

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //SecurityFilterChain
        http
                .csrf().disable()
                .authorizeRequests()
                    .requestMatchers("/api/v*/registration/**")
                    .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin();

        http.authenticationProvider(authenticationProvider());

        // http....;

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(appUserService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return authProvider;
    }


}
