package com.wellington.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellington.curso.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
