package com.wellington.curso.resources;

import java.net.URI;
import java.time.Instant;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wellington.curso.entities.Order;
import com.wellington.curso.entities.OrderItem;
import com.wellington.curso.entities.Product;
import com.wellington.curso.entities.User;
import com.wellington.curso.entities.enums.OrderStatus;
import com.wellington.curso.services.OrderService;
import com.wellington.curso.services.PaymentService;
import com.wellington.curso.services.ProductService;
import com.wellington.curso.services.UserService;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

	@Autowired
	private OrderService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping
	public ResponseEntity<List<Order>> findAll(){
		List<Order> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id){
		Order obj =  service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Order> insert(@RequestBody Order order){
		
		order.setMoment(Instant.now());
		order.setOrderStatus(OrderStatus.WAINTING_PAYMENT);		
		
		User user = userService.findById(order.getClient().getId());
		Set<OrderItem> products = productService.insertAllProducts(order);
		order.getItems().clear();
		order.getItems().addAll(products);
		
		order.setClient(user);
			
		Order obj =  service.insert(order);		
		
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		
		return ResponseEntity.noContent().build();
	}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order order){
		Order obj = service.update(id, order);
		
		return ResponseEntity.ok().body(obj);
		
	}
	
}
