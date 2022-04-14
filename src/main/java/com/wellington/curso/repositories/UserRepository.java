package com.wellington.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellington.curso.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	
	
}
