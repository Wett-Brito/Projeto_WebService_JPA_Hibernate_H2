package com.wellington.curso.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wellington.curso.entities.Order;
import com.wellington.curso.entities.OrderItem;
import com.wellington.curso.repositories.OrderRepository;
import com.wellington.curso.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	public List<Order> findAll(){
		return repository.findAll();
	}
	
	public Order findById(Long id) {
		
		try{
			Optional<Order> obj = repository.findById(id);
			return obj.get();
		}catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public Order insert(Order order) {
		
		return repository.save(order);
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public Order update(Long id, Order order) {
		Order entity = repository.getById(id);
		updateData(entity, order);
		return repository.save(entity);
	}
	
	public Order update(Long id, Order order, Set<OrderItem> items) {
		Order entity = repository.getById(id);
		updateData(entity, order, items);
		return repository.save(entity);
	}
	
	public void updateData(Order entity, Order order, Set<OrderItem> items) {
		entity.setOrderStatus(order.getOrderStatus());
		entity.getItems().clear();
		entity.getItems().addAll(items);
	}
	
	public void updateData(Order entity, Order order) {
		entity.setOrderStatus(order.getOrderStatus());
	}

}
