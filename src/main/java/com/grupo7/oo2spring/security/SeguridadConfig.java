package com.grupo7.oo2spring.security;

import java.io.IOException;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import com.grupo7.oo2spring.handlers.RestAuthenticationFailureHandler;
import com.grupo7.oo2spring.services.CustomUserDetailsService;
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
    private final CustomUserDetailsService customUserDetailsService;
    private final RestAuthenticationFailureHandler restAuthenticationFailureHandler;
    private final PasswordEncoder passwordEncoder;
    

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> loggingFilter() {
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
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        FiltroAutenticacionJson filtro = new FiltroAutenticacionJson();
        filtro.setAuthenticationManager(authManager);
        filtro.setFilterProcessesUrl("/api/auth/login");

        http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                        "/", "/index", "/usuario/login", "/usuario/login/process",
                        "/error", "/css/**", "/js/**", "/usuario/formulario", "/usuario/registro_form",
                        "/usuario/registro", "/usuario/registro/**", "/usuario/registro_exito",
                        "/usuario/confirmar", "/usuario/confirmar/**", "/usuario/confirmacion_exitosa",
                        "/usuario/token_invalido", "/api/auth/**", "/swagger-ui/**",
                        "/v3/api-docs/**", "/swagger-ui.html"
                    ).permitAll()
                    .requestMatchers("/api/**").authenticated() // Protegés tus endpoints API
                    .requestMatchers("/panel").hasAnyRole("USER", "EMPLEADO", "MANAGER")
                    .requestMatchers("/api/usuario/getUsuario").authenticated()
                    .requestMatchers("/manager/**").hasRole("MANAGER")
                    .requestMatchers("/api/manager/**").hasRole("MANAGER")
                    .anyRequest().authenticated()
                    ).exceptionHandling(ex -> ex
                            .authenticationEntryPoint((request, response, authException) -> {
                                String uri = request.getRequestURI();
                                if (uri.startsWith("/api")) {
                                    // Si es API, respondemos con JSON
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    response.setContentType("application/json");
                                    response.getWriter().write("{\"error\": \"Sesión expirada o no autenticado\"}");
                                } else {
                                    // Si es página (como panel.html), redirigimos al login
                                    response.sendRedirect("/usuario/login?expired=true");
                                }
                            })
                        )
            .userDetailsService(customUserDetailsService)
            .addFilterBefore(filtro, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .formLogin(form -> form.disable())
            .logout(logout -> logout.disable())
            .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}