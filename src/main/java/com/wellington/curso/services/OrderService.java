package com.wellington.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellington.curso.entities.Order;
import com.wellington.curso.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository reporistory;
	
	public List<Order> findAll(){
		return reporistory.findAll();
	}
	
	public Order findById(Long id) {
		
		Optional<Order> obj = reporistory.findById(id);
		return obj.get();
	}
	
}
