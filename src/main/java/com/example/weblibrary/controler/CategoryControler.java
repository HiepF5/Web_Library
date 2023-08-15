package com.example.weblibrary.controler;

import com.example.weblibrary.entity.Category;
import com.example.weblibrary.entity.dto.CartMessage;
import com.example.weblibrary.entity.dto.CategoryDto;
import com.example.weblibrary.entity.dto.CategoryView;
import com.example.weblibrary.exception.ObjectExistedException;
import com.example.weblibrary.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryControler {
	private final CategoryService categoryService;

	public CategoryControler(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/category/getAll")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(categoryService.getall(), HttpStatus.OK);
	}

	@GetMapping("/category")
	public ResponseEntity<?> getCategories(@RequestParam(name = "size", required = false, defaultValue = "12") int size,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page) {
		List<Category> categories = categoryService.findAll(page, size).getContent();
		int pageCount = categoryService.findAll(page, size).getTotalPages();
		CategoryView c = new CategoryView(pageCount, categories);
		return new ResponseEntity<>(c, HttpStatus.OK);
	}

	@PostMapping("/category")
	public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryDto categoryDto) throws ObjectExistedException {
		return new ResponseEntity<>(categoryService.create(categoryDto), HttpStatus.OK);
	}

	@DeleteMapping("/category/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable int id) {
		categoryService.delete(id);
		return new ResponseEntity<>(new CartMessage("delete successfully"), HttpStatus.OK);
	}
}
