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
	private OrderRepository repository;
	
	public List<Order> findAll(){
		return repository.findAll();
	}
	
	public Order findById(Long id) {
		
		Optional<Order> obj = repository.findById(id);
		return obj.get();
	}
	
	public Order insert(Order order) {
		
		return repository.save(order);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Order update(Long id, Order order) {
		Order entity = repository.getById(id);
		updateData(entity, order);
		return repository.save(entity);
	}
	
	public void updateData(Order entity, Order order) {
		entity.setOrderStatus(order.getOrderStatus());
	}
}
