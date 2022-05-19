package com.wellington.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.wellington.curso.entities.Payment;
import com.wellington.curso.repositories.PaymentRepository;
import com.wellington.curso.services.exceptions.OrderAlreadyWithPaymentException;
import com.wellington.curso.services.exceptions.ResourceNotFoundException;

@Service
public class PaymentService {

	@Autowired
	PaymentRepository paymentRepository;
	
	public List<Payment> findAll(){
		return paymentRepository.findAll();
	}
	
	public Payment findById(Long id) {
		
		try {
			Optional<Payment> obj = paymentRepository.findById(id);
			return obj.orElseThrow(() -> new ResourceNotFoundException(id));
		} catch (RuntimeException e) {
			throw new ResourceNotFoundException(id);
		}

	}
	
	public Payment insert(Payment payment) {
		try {
		return paymentRepository.save(payment);
		}catch (DataIntegrityViolationException e) {
			if(payment.getId() == null) {
				throw new OrderAlreadyWithPaymentException();

			} else {
				throw new OrderAlreadyWithPaymentException(payment.getId());
				
			}
		}
	}
	
	public void remove(Long id) {
		paymentRepository.deleteById(id);

	}

}
