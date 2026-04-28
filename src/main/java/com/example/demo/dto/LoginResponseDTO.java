package com.example.demo.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

	private String jwtToken ;
	private UserDTO userDTO;
}
