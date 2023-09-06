package com.example.springsecutrityJwtdemo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecutrityJwtdemo.model.Product;



@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
		Product findByName( String name);
	  
}