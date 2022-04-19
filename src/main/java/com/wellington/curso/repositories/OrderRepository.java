package com.wellington.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellington.curso.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{}
