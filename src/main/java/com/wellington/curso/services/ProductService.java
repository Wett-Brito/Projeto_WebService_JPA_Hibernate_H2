package com.wellington.curso.services;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wellington.curso.entities.Order;
import com.wellington.curso.entities.OrderItem;
import com.wellington.curso.entities.Product;
import com.wellington.curso.repositories.ProductRepository;
import com.wellington.curso.services.exceptions.DatabaseException;
import com.wellington.curso.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
		
	public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	public Product findById(Long id) {
		
		try{
			Optional<Product> obj = productRepository.findById(id);
			return obj.orElseThrow(() -> new ResourceNotFoundException(id));
		}catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}

	}
	
	public void remove(Long id) {
		try {
			productRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Product update(Long id, Product product) {
		Product entity = findById(id);
		return updataData(entity, product);
	}
	
	public Product insert(Product product) {
		return productRepository.save(product);
	}
	
	public Set<OrderItem> insertAllProducts(Order order){
		
		Set<OrderItem> listOrder = order.getItems();
		Set<OrderItem> list = new HashSet<>();
		
		for (OrderItem orderItem : listOrder) {
			Product product = findById(orderItem.getProduct().getId());
			list.add(new OrderItem(order, product, orderItem.getQuantity(), orderItem.getPrice()));
		}
		
		return list;
		
	}
	
	public Product updataData(Product entity, Product product) {
		entity.setName(product.getName());
		entity.setPrice(product.getPrice());
		entity.setDescription(product.getDescription());
		entity.setImgUrl(product.getImgUrl());
		
		return entity;
	}
	
}
