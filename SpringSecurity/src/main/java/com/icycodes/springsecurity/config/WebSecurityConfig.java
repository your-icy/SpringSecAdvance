package com.icycodes.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig{

    private static final String[] WHITE_LIST_URLS = {
            "/register", "/verifyRegistration*" , "/resendVerifyToken*", "/changePassword",
            "/savePassword", "/password/reset"
    };


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(Customizer.withDefaults())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> {
                    try {
                        request.requestMatchers(WHITE_LIST_URLS).permitAll()
                                .requestMatchers("/api/**").authenticated()
                                .and().oauth2Login(oAuth2Login ->
                                        oAuth2Login.loginPage("oauth2/authorization/api-client-oidc")
                                )
                                .oauth2Login(Customizer.withDefaults());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

            return http.build();
    }



}
