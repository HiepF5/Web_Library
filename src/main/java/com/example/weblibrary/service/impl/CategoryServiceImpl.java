package com.example.weblibrary.service.impl;

import com.example.weblibrary.Repository.CategoryRepository;
import com.example.weblibrary.entity.Category;
import com.example.weblibrary.entity.dto.CategoryDto;
import com.example.weblibrary.exception.ObjectExistedException;
import com.example.weblibrary.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getall() {

		return categoryRepository.findAll();
	}

	@Override
	public Category create(CategoryDto categoryDto) throws ObjectExistedException {
		// TODO Auto-generated method stub
		Category category = new Category();
		if (!ObjectUtils.isEmpty(categoryRepository.findByCategoryName(categoryDto.getCategoryName()))) {
			throw new ObjectExistedException("category existed!");
		}
		category.setCategoryName(categoryDto.getCategoryName());

		return categoryRepository.save(category);
	}

	@Override
	public Page<Category> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		return categoryRepository.findAll(pageable);
	}

	@Override
	public void delete(int id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public Category GetCategoryId(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}

}
