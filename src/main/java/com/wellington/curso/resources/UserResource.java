package com.wellington.curso.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

import com.wellington.curso.dto.UserDTO;
import com.wellington.curso.entities.User;
import com.wellington.curso.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/users")
@Api(value = "Application Status", tags = { "Users" })
public class UserResource {

	@Autowired
	private UserService service;
	
	@ApiOperation(value = "Lista todos os Usuarios", notes = "Lista todos os Usuarios", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll(){
		List<User> list = service.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@ApiOperation(value = "Lista um Usuario especifico", notes = "Lista um Usuario especifico", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Resource not found"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id){
		User obj =  service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "Cria um novo Usuario", notes = "Cria um novo Usuario", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "Bad Request"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User obj){
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
	
		return ResponseEntity.created(uri).body(obj);

	}
	
	@ApiOperation(value = "Exclui um Usuario", notes = "Exclui um Usuario", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Database error"),
			@ApiResponse(code = 404, message = "Resource not found"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@DeleteMapping
	public ResponseEntity<Void> delete(@RequestBody User obj) {
		service.delete(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Exclui um Usuario", notes = "Exclui um Usuario", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Database error"),
			@ApiResponse(code = 404, message = "Resource not found"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Edita uma Categoria", notes = "Edita uma Categoria", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@PutMapping(value = "/{id}")
	public ResponseEntity<User> updateById(@PathVariable Long id, @RequestBody User obj){
		obj = service.UpdateById(id , obj);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "Edita uma Categoria", notes = "Edita uma Categoria", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Resource not found"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@PutMapping()
	public ResponseEntity<User> updateById(@RequestBody User obj){

		obj = service.UpdateById(obj.getId() , obj);
		
		return ResponseEntity.ok().body(obj);
	}
}
