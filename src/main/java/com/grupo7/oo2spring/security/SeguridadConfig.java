package com.grupo7.oo2spring.security;

import java.io.IOException;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import com.grupo7.oo2spring.services.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity 
@EnableMethodSecurity 
@RequiredArgsConstructor
public class SeguridadConfig {
	
	private final UsuarioService usuarioService;
    
    
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authBuilder.build();
    }
    
    @Bean
    FilterRegistrationBean<OncePerRequestFilter> loggingFilter() {
        FilterRegistrationBean<OncePerRequestFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                System.out.println("➡️ Request URI: " + request.getRequestURI() +
                                   " | Authenticated: " + (request.getUserPrincipal() != null));
                filterChain.doFilter(request, response);
            }
        });

        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        FiltroAutenticacionJson filtro = new FiltroAutenticacionJson();
        filtro.setAuthenticationManager(authManager);
        filtro.setFilterProcessesUrl("/api/auth/login");

        http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/index",
                    "/usuario/login",
                    "/usuario/login/process",
                    "/error",
                    "/css/**",
                    "/js/**",
                    "/usuario/formulario",
                    "/usuario/registro_form",
                    "/usuario/registro",
                    "/usuario/registro/*",
                    "/usuario/registro_exito",
                    "/usuario/confirmar",
                    "/usuario/confirmar/**",
                    "/usuario/confirmacion_exitosa",
                    "/usuario/token_invalido",
                    "/api/auth/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(filtro, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .formLogin(form -> form.disable()) // 🚫 Deshabilita el login por formulario
            .httpBasic(httpBasic -> httpBasic.disable()); // 🚫 Deshabilita HTTP Basic login

        return http.build();
    }
    }
    
    
    
    
