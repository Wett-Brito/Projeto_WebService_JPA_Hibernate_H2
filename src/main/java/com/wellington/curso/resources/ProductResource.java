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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/products")
@Api(value = "Application Status", tags = { "Products" })
public class ProductResource {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@ApiOperation(value = "Lista todos os Produtos", notes = "Lista todos os Produtos", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@GetMapping
	public ResponseEntity<List<Product>> findAll(){
		List<Product> listObj = productService.findAll();
		return ResponseEntity.ok().body(listObj);
	}
	
	@ApiOperation(value = "Lista um Produto especifico", notes = "Lista um Produto especifico", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Resource not found"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product obj = productService.findById(id);
		
		return ResponseEntity.ok().body(obj);
		

	}
	
	@ApiOperation(value = "Edita uma Produto", notes = "Edita uma Produto", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product){
		Product obj = productService.update(id, product);
		
		return ResponseEntity.ok().body(obj);

	}
	
	@ApiOperation(value = "Exclui um Produto", notes = "Exclui um Produto", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Database error"),
			@ApiResponse(code = 404, message = "Resource not found"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		productService.remove(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Cria um novo Produto", notes = "Cria um novo Produto", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "Bad Request"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@PostMapping
	public ResponseEntity<Product> insert(@RequestBody Product product){
		
		Set<Category> list = categoryService.insertAllCategories(product);
		
		product.getCategories().clear();
		product.getCategories().addAll(list);
		
		Product obj = productService.insert(product);
		
		return ResponseEntity.ok().body(obj);
	}
	
}
