package com.example.weblibrary.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	@NotBlank(message =  "content should not be blank!")
	@NotNull(message = "content should not be null!")
	private String content;
	@Min(1)
	private int productId;
	@Min(1)
	private int userId;
	
	private int star;
	
	
	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public Object getContent() {
		return this.content;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
