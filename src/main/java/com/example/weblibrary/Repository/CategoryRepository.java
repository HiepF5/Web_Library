package com.example.weblibrary.Repository;

import com.example.weblibrary.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	Category findByCategoryName(String categoryName);
}
