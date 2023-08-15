package com.example.weblibrary.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	@NotNull(message = "username should not be null!")
	@NotBlank(message = "username should not be blank!")
	@Length(min = 5, max = 35)
	private String username;
	@NotNull(message = "password should not be null")
	@NotBlank(message = "username should not be blank!")
	private String password;
}
