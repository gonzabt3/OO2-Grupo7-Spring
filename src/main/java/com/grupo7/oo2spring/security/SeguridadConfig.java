package com.grupo7.oo2spring.security;

import java.io.IOException;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import com.grupo7.oo2spring.handlers.CustomAuthenticationFailureHandler;
import com.grupo7.oo2spring.services.CustomUserDetailsService;
import com.grupo7.oo2spring.services.UsuarioService;

import jakarta.servlet.DispatcherType;
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
	private final  CustomAuthenticationFailureHandler failureHandler;
	private final CustomUserDetailsService customUserDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	@Bean
	public UserDetailsService userDetailsService() {
	    return customUserDetailsService;
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
    
    @Controller
    public class AuthErrorController {

        @GetMapping("/usuario/error_login")
        public String mostrarErrorLogin(HttpServletRequest request, Model model) {
            Object errorMessage = request.getAttribute("error_message");
            model.addAttribute("mensaje", errorMessage != null ? errorMessage : "Error desconocido");
            return "/error";
        }
    }
    
    
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .userDetailsService(customUserDetailsService)
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
                    "/usuario/token_invalido"
                ).permitAll()
                .requestMatchers("/panel").hasAnyRole("USER", "EMPLEADO", "MANAGER")
                .requestMatchers("/manager/**").hasRole("MANAGER")
                .anyRequest().authenticated()
            ).formLogin(form -> form
                .loginPage("/usuario/login")                
                .loginProcessingUrl("/usuario/login/process")
                .defaultSuccessUrl("/panel", true)
                .failureUrl("/usuario/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/usuario/login?logout")
                .permitAll()
            );
        

        return http.build();
    }
    
    
    
}
