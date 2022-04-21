package com.wellington.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellington.curso.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
