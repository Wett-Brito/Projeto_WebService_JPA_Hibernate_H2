package com.wellington.curso.resources;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wellington.curso.entities.Order;
import com.wellington.curso.entities.Payment;
import com.wellington.curso.entities.enums.OrderStatus;
import com.wellington.curso.services.OrderService;
import com.wellington.curso.services.PaymentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/payments")
@Api(value = "Application Status", tags = { "Payments" })
public class PaymentResource {

	@Autowired
	PaymentService paymentService;
	
	@Autowired
	OrderService orderService;
	
	@ApiOperation(value = "Lista todos os Pagamentos", notes = "Lista todos os Pagamentos", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@GetMapping
	public ResponseEntity<List<Payment>> findAll(){
		List<Payment> list = paymentService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@ApiOperation(value = "Lista um Pagamento especifico", notes = "Lista um Pagamento especifico", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Resource not found"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/{id}")
	public ResponseEntity<Payment> findById(@PathVariable Long id) {
		Payment obj = paymentService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "Cria um novo Pagamento", notes = "Cria um novo Pagamento", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "Bad Request"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@PostMapping()
	public ResponseEntity<Payment> insert(@RequestBody Order order){
		order = orderService.findById(order.getId());

		Payment payment = new Payment();
		payment.setOrder(order);
		payment.setDate(Instant.now());
		payment = paymentService.insert(payment);
		
		order.setOrderStatus(OrderStatus.PAID);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(payment.getId()).toUri();
		return ResponseEntity.created(uri).body(payment);

	}
	
	@ApiOperation(value = "Exclui um Pagamento", notes = "Exclui um Pagamento", responseContainer = "List" )
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Database error"),
			@ApiResponse(code = 404, message = "Resource not found"),
		    @ApiResponse(code = 500, message = "The resource you were trying to reach is not found") })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		Payment payment = paymentService.findById(id);
		Order order = payment.getOrder();
		order.setOrderStatus(OrderStatus.CANCELED);
		
		paymentService.remove(id);
		orderService.insert(order);
		
		return ResponseEntity.noContent().build();
	}
	
	
}
