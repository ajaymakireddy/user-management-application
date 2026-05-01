package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {

	@NotBlank
	private Long id ;
	
	@NotBlank
	@Size(min = 2 , max = 50)
	private String username ;
	
	@NotBlank
	@Size(min = 5 , max = 50)
	private String password ;
	
	@NotBlank
	private String email ;
}
