package com.wellington.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellington.curso.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
