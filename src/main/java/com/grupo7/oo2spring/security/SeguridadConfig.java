package com.grupo7.oo2spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.grupo7.oo2spring.services.UsuarioService;

@Configuration
public class SeguridadConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(){
    	return new UsuarioService();
    }
    /*
    @Bean
    
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
    */
    @Bean 
    public DaoAuthenticationProvider authenticationProvider(){
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    	authenticationProvider.setUserDetailsService(userDetailsService());
    	authenticationProvider.setPasswordEncoder(passwordEncoder());
    	return authenticationProvider;
    }
    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {
    	http.csrf(csrf->csrf.disable()).cors(cors->cors.disable())
    	.authorizeHttpRequests(req->req
    			.requestMatchers("/registro").permitAll()
    			.anyRequest().authenticated()
    			)
    	.formLogin(from->from.loginPage("/sigin")
    			.loginProcessingUrl("/login")
    			.permitAll()
    			.usernameParameter("nombreUsuario")
				.passwordParameter("contraseña")
				.defaultSuccessUrl("/inicio")
				
    			)
    	.formLogin( logout -> {
			logout.permitAll();
		})			
		.httpBasic(Customizer.withDefaults());		
    	return http.build();
    }
}
