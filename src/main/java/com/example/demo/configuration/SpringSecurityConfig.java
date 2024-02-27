package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService ;
	
	@Bean
	/*BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
	NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, NoOpPasswordEncoder passwordEncoder) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder)
				.and().build();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole("ADMIN","USER");
			auth.requestMatchers(HttpMethod.GET, "/users/home").hasRole("ADMIN");
			//auth.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN");
			//auth.requestMatchers(HttpMethod.POST, "/users/**").permitAll();
			auth.requestMatchers("/login").permitAll();
			auth.anyRequest().authenticated();
		}).httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
