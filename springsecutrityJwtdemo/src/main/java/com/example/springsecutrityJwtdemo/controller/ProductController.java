package com.example.springsecutrityJwtdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecutrityJwtdemo.model.ErrorClazz;
import com.example.springsecutrityJwtdemo.model.Product;
import com.example.springsecutrityJwtdemo.services.ProductService;



@CrossOrigin("http://localhost:30001")
@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired

	private ProductService productService;

	@GetMapping("/allproduct")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProduct(@PathVariable int id) {
		Product product = productService.getProduct(id);
		if (product == null) {
			ErrorClazz errorclazz = new ErrorClazz(202, "Product doesnt exist");
			return new ResponseEntity<ErrorClazz>(errorclazz, HttpStatus.OK);
		} else
			return new ResponseEntity<Product>(product, HttpStatus.OK);

	}

	@GetMapping("/search/{name}")
	public Product getProductName(@PathVariable String name) {
		return productService.getProductByName(name);

	}

	@PostMapping("/addproduct")
	public ResponseEntity<?> saveProduct(@RequestBody Product product){
		try {
		
		productService.saveProduct(product);
		return new ResponseEntity<Void>(HttpStatus.OK);
		}catch (Exception e) {
			ErrorClazz err=new ErrorClazz(500,e.getMessage());
			if(e.getMessage().contains("name_unique"))
				err=new ErrorClazz(500,"Product name already exists...");
			return new ResponseEntity<ErrorClazz>(err,HttpStatus.INTERNAL_SERVER_ERROR);
				
			
	}
	}
//	@GetMapping("/search/{price}")
//	public Product getProductPrice(@PathVariable int price) {
//		return productService.getProductByPrice(price);
//
//	}

	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable int id)
	{
	
		  productService.deleteProduct(id);
	}
	
	@PutMapping("/update")
	
	public void updateProduct(@RequestBody Product product)
	{
		productService.updateProduct(product);
	}
	
	
	

}