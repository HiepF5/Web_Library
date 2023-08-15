package com.example.weblibrary.entity.dto;

import com.example.weblibrary.entity.Category;
import lombok.Data;

import java.util.List;


@Data
public class CategoryView {
	int pageCount;
	List<Category> categories;

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public CategoryView(int pageCount, List<Category> categories) {
		super();
		this.pageCount = pageCount;
		this.categories = categories;
	}

	public CategoryView() {
		super();
	}

}
