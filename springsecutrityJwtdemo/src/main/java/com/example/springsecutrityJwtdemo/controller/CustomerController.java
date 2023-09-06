package com.example.springsecutrityJwtdemo.controller;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecutrityJwtdemo.configure.JwtTokenHelper;
import com.example.springsecutrityJwtdemo.model.Customer;
import com.example.springsecutrityJwtdemo.model.LoginCredentials;
import com.example.springsecutrityJwtdemo.services.CustomerUserDetailsServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin("http://localhost:30001")
	@RestController
	public class CustomerController {
		@Autowired
		CustomerUserDetailsServices customerUserDetailService;
		@Autowired
		PasswordEncoder passwordEncoder;
		@Autowired
		AuthenticationManager authenticationManager;
		@Autowired
		JwtTokenHelper jwtTokenHelper;
		private SecurityContextRepository securityContextRepository=
				new HttpSessionSecurityContextRepository();
		@PostMapping("/public/register")
		public ResponseEntity<?> registerCustomer(@RequestBody Customer customer){

			String password=customer.getPassword();
			String hashpwd=passwordEncoder.encode(password);
			customer.setPassword(hashpwd);
			customer.setRole("Role_User");
			customer=customerUserDetailService.registerCustomer(customer);
			try {
				if(customer.getId()>0){
				return new ResponseEntity<String>("Register successful",HttpStatus.OK);
				}else {
					return new ResponseEntity<String>("user not registered",HttpStatus.INTERNAL_SERVER_ERROR);
				}
			 }catch (Exception e) {
				 return new ResponseEntity<String> (e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
		
			
		}
		@PostMapping("/public/Login")
		public ResponseEntity<?> login(@RequestBody LoginCredentials lc ) throws InvalidKeySpecException,NoSuchFieldException{
			System.out.println("Email "+lc.getEmail());
			System.out.println("password "+lc.getPassword());
			Authentication authentication=authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(lc.getEmail(), lc.getPassword()));
			Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwtToken=jwtTokenHelper.generateToken(lc.getEmail(), authorities);
			return ResponseEntity.ok(jwtToken);
			
		}
		
		public ResponseEntity<?> getUserInfo(Principal user){
			UserDetails userDetails=customerUserDetailService.loadUserByUsername(user.getName());
			return ResponseEntity.ok(userDetails);
			
			
		}

}
