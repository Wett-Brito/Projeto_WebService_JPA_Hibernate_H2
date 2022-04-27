package com.wellington.curso.services;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellington.curso.entities.Order;
import com.wellington.curso.entities.OrderItem;
import com.wellington.curso.entities.Product;
import com.wellington.curso.repositories.ProductRepository;
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
	
	public Set<OrderItem> insertAllProducts(Set<OrderItem> listOrder, Order order){
		
		Set<OrderItem> list = new HashSet<>();
		
		for (OrderItem orderItem : listOrder) {
			Product product = findById(orderItem.getProduct().getId());
			list.add(new OrderItem(order, product, orderItem.getQuantity(), orderItem.getPrice()));
		}
		
		return list;
		
	}
	
}
