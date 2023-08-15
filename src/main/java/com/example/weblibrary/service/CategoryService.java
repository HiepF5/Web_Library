package com.example.weblibrary.service;

import com.example.weblibrary.entity.Category;
import com.example.weblibrary.entity.dto.CategoryDto;
import com.example.weblibrary.exception.ObjectExistedException;
import org.springframework.data.domain.Page;

import java.util.List;



public interface CategoryService {
	Page<Category> findAll(int page, int size);
//
//	Category findById(int id);

	List<Category> getall();

	Category create(CategoryDto categoryDto) throws ObjectExistedException;

//	Category update(CategoryDto categorydto, int id) throws ObjectExistedException;
//
	void delete(int id);
	Category GetCategoryId(String categoryName);	
}
