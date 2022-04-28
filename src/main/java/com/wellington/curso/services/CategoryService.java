package com.wellington.curso.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wellington.curso.entities.Category;
import com.wellington.curso.entities.Product;
import com.wellington.curso.repositories.CategoryRepository;
import com.wellington.curso.services.exceptions.DatabaseException;
import com.wellington.curso.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
	
	public Category findById(Long id) {	
		try {
			Optional<Category> obj = categoryRepository.findById(id);
			return obj.orElseThrow(() -> new ResourceNotFoundException(id));
		} catch (RuntimeException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public Category insert(Category category) {
		return categoryRepository.save(category);
	}
	
	public Category update(Long id, Category category) {
		Category entity = findById(id);
		entity.setName(category.getName());
		
		return entity;
		
	}
	
	public void remove(Long id) {
		try {
			categoryRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		}
		
	}
	
	public Set<Category> insertAllCategories(Product product){
		Set<Category> listCategories = product.getCategories();
		Set<Category> list = new HashSet<>();
		
		for(Category c : listCategories) {
			list.add(findById(c.getId()));
		}
		
		return list;
	}
	
}
