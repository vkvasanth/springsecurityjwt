package com.example.springsecutrityJwtdemo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springsecutrityJwtdemo.Dao.ProductDao;
import com.example.springsecutrityJwtdemo.model.Product;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDao productDao; 

 @Autowired
 public ProductServiceImpl(ProductDao productDao)
 {
	 super();
	 this.productDao=productDao;
 }

@Override
public List<Product> getAllProducts() {
	// TODO Auto-generated method stub
	return productDao.findAll();
}

@Override
public Product getProduct(int id) {
	// TODO Auto-generated method stub
	Optional<Product> optional = productDao.findById(id);
	if(optional.isPresent())
		return optional.get();
	else {
		return null;
	}
	
}

@Override
public Product saveProduct(Product product) {
	// TODO Auto-generated method stub
	return productDao.save(product);
}

@Override
public void updateProduct(Product product) {
	// TODO Auto-generated method stub
	productDao.save(product);
	
}

@Override
public void deleteProduct(int id) {
	// TODO Auto-generated method stub
	productDao.deleteById(id);
	
}

@Override
public Product getProductByName(String name) {
	// TODO Auto-generated method stub
	return productDao.findByName(name);
}

 
}

