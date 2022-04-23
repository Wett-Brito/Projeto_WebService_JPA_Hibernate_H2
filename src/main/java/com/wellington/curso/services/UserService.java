package com.wellington.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellington.curso.entities.User;
import com.wellington.curso.repositories.UserRepository;
import com.wellington.curso.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository reporistory;
	
	public List<User> findAll(){
		return reporistory.findAll();
	}
	
	public User findById(Long id) {
		
		Optional<User> obj =  reporistory.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
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
	
	public User UpdateById(Long id, User obj) {
		User entity = reporistory.getById(id);
		updateData(entity, obj);
		return reporistory.save(entity);
	}
	
	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
}
