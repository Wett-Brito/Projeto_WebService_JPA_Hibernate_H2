package com.wellington.curso.resources;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellington.curso.entities.Category;
import com.wellington.curso.entities.Product;
import com.wellington.curso.services.CategoryService;
import com.wellington.curso.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<Product>> findAll(){
		List<Product> listObj = productService.findAll();
		return ResponseEntity.ok().body(listObj);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product obj = productService.findById(id);
		
		return ResponseEntity.ok().body(obj);
		

	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product){
		Product obj = productService.update(id, product);
		
		return ResponseEntity.ok().body(obj);

	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		productService.remove(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Product> insert(@RequestBody Product product){
		
		Set<Category> list = categoryService.insertAllCategories(product);
		
		product.getCategories().clear();
		product.getCategories().addAll(list);
		
		Product obj = productService.insert(product);
		
		return ResponseEntity.ok().body(obj);
	}
	
}
