package com.wellington.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellington.curso.entities.Payment;
import com.wellington.curso.repositories.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	PaymentRepository paymentRepository;
	
	public List<Payment> findAll(){
		return paymentRepository.findAll();
	}
	
	public Payment findById(Long id) {
		Optional<Payment> obj = paymentRepository.findById(id);
		
		return obj.get();
	}
}
