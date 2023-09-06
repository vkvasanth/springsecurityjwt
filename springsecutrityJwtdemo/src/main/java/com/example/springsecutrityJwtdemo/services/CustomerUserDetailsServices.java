package com.example.springsecutrityJwtdemo.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springsecutrityJwtdemo.Dao.CustomerDao;
import com.example.springsecutrityJwtdemo.model.Customer;
@Service
public class CustomerUserDetailsServices implements UserDetailsService {

		@Autowired
		private CustomerDao customerDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated 
		Customer customer =customerDao.findByEmail(username);
		if(customer==null)
			throw new UsernameNotFoundException("user details are not found");
		else {
			List<GrantedAuthority> authorities=new LinkedList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(customer.getRole()));
			return new User(customer.getEmail(),customer.getPassword(), authorities);
		}
	}
	public Customer registerCustomer(Customer customer)
	{
		return customerDao.save(customer);
		
	}

}
