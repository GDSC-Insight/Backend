package com.example.GDSC_insight.config;

import com.example.GDSC_insight.config.jwt.JwtAuthorizationFilter;
import com.example.GDSC_insight.config.jwt.JwtTokenProvider;
import com.example.GDSC_insight.repository.CorporateRepository;
import com.example.GDSC_insight.repository.IndividualRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private CorsConfig corsConfig;

    @Autowired
    private CorporateRepository corporateRepository;

    @Autowired
    private IndividualRepository individualRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilter(corsConfig.corsFilter())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers("/api/main/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/corporate").hasRole("CORPORATE")
                        .requestMatchers("/api/user").hasRole("INDIVIDUAL")
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(
                        new JwtAuthorizationFilter(authenticationManagerBean(), corporateRepository, individualRepository, jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class) ;
        return http.build();
    }


}