
package com.ra.sercurity.config;

import com.ra.sercurity.jwt.AccessDenied;
import com.ra.sercurity.jwt.JWTEntryPoint;
import com.ra.sercurity.jwt.JWTProvider;
import com.ra.sercurity.jwt.JWTTokenFilter;
import com.ra.sercurity.user_principle.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  // phân quyền trong url
@EnableMethodSecurity // phân quyền được ở trong phương phức (method)
public class SecurityConfig {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private JWTEntryPoint jwtEntryPoint;
    @Autowired
    private JWTTokenFilter jwtTokenFilter;
    @Autowired
    private AccessDenied accessDenied;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**", "/api/v1/hotel/**", "/api/v1/*").permitAll()
                        .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/user/**").hasAuthority("USER")
                        .anyRequest().authenticated()
                ).exceptionHandling(auth -> auth.authenticationEntryPoint(jwtEntryPoint)
                        .accessDeniedHandler(accessDenied)
                ).addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}

