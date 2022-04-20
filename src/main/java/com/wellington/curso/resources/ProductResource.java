package com.wellington.curso.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellington.curso.entities.Product;
import com.wellington.curso.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<Product> findAll(){
		return productService.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public Product findById(@PathVariable Long id) {
		return productService.findById(id);

	}
	
}
