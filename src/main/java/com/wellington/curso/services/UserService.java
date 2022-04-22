package com.wellington.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellington.curso.entities.User;
import com.wellington.curso.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository reporistory;
	
	public List<User> findAll(){
		return reporistory.findAll();
	}
	
	public User findById(Long id) {
		
		Optional<User> obj =  reporistory.findById(id);
		return obj.get();
	}
	
	public User insert(User obj) {
		return reporistory.save(obj);
	}
	
	public void delete(User obj) {
		reporistory.delete(obj);
	}
	
	public void deleteById(Long id) {
		reporistory.deleteById(id);
	}
}
