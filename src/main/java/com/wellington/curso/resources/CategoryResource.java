package com.wellington.curso.resources;

import java.net.URI;
import java.util.List;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wellington.curso.entities.Category;
import com.wellington.curso.services.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/categories")
@Api(value = "Application Status", tags = { "Categories" })
public class CategoryResource {

	@Autowired
	private CategoryService categoryService;
	
	@ApiOperation(value = "Lista todas as Categorias", notes = "Lista todas as Categorias", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> list = categoryService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@ApiOperation(value = "Lista uma categoria especifica Categorias", notes = "Lista uma categoria especifica Categorias", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Resource not found"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id){
		Category obj = categoryService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
	@ApiOperation(value = "Cria uma nova Categoria", notes = "Cria uma nova Categoria", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "Bad Request"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@PostMapping
	public ResponseEntity<Category> insert(@RequestBody Category category){
		Category obj = categoryService.insert(category);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@ApiOperation(value = "Edita uma Categoria", notes = "Edita uma Categoria", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@PutMapping(value = "/{id}")
	public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category){
		Category obj = categoryService.update(id, category);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "Exclui uma Categoria", notes = "Exclui uma Categoria", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Database error"),
			@ApiResponse(code = 404, message = "Resource not found"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id){
		categoryService.remove(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
