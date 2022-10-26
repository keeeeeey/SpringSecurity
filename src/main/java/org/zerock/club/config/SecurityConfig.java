package org.zerock.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.zerock.club.security.handler.ClubLoginSuccessHandler;
import org.zerock.club.security.service.ClubUserDetailsService;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private ClubUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user1")
//                .password(passwordEncoder().encode("1111"))
//                .roles("USER")
//                .build();
//        log.info("userDetailsService.....................");
//        log.info(user);
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http.authorizeHttpRequests((auth) -> {
//            auth.antMatchers("/sample/all").permitAll();
//            auth.antMatchers("/sample/member").hasRole("USER");
//        });

        http.formLogin();
        http.csrf().disable();
        http.logout();

        http.oauth2Login().successHandler(successHandler());

        http.rememberMe()
                .tokenValiditySeconds(60*60*24*7)
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public ClubLoginSuccessHandler successHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }
}
