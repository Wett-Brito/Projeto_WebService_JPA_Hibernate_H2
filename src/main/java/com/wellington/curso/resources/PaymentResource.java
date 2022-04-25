package com.wellington.curso.resources;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellington.curso.entities.Payment;
import com.wellington.curso.services.PaymentService;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {

	@Autowired
	PaymentService paymentService;
	
	@GetMapping
	public ResponseEntity<List<Payment>> findAll(){
		List<Payment> list = paymentService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Payment> findById(@PathVariable Long id) {
		Payment obj = paymentService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
	
}
