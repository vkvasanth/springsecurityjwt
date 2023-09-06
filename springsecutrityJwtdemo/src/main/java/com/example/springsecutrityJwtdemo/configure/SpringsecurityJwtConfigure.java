package com.example.springsecutrityJwtdemo.configure;

import static org.springframework.security.config.Customizer.withDefaults;

import java.io.ObjectInputFilter.Config;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springsecutrityJwtdemo.services.CustomerUserDetailsServices;


@Configuration

public class SpringsecurityJwtConfigure {
	@Autowired
	private CustomerUserDetailsServices customerUserDetailsServices;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement((config) ->config.sessionCreationPolicy(
				SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests((requests)->{
			requests
			.requestMatchers("/public/**").permitAll()
			.requestMatchers(HttpMethod.OPTIONS).permitAll()
			.anyRequest().authenticated();
		});
		
		http.addFilterBefore(new JWTAuthenticationFilter(customerUserDetailsServices,jwtTokenHelper), 
				UsernamePasswordAuthenticationFilter.class);
		http.csrf((csrf)->csrf.disable());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
 	}
	
//	@Bean
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
////		InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
////		manager.createUser(User.withDefaultPasswordEncoder()
////				.username("vasanth").password("123").roles("USER").build());
////		return manager;
//	} 
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		//return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
		
	}
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
	}

}
