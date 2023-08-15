package com.example.weblibrary.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	@NotNull(message = "category should not be null!")
	@NotBlank(message = "category name should not be blank!")
	@Length(min = 1, max = 100)
	private String categoryName;


	public String getCategoryName() {
		return this.categoryName;
	}


}
