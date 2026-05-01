package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.AuthenticationService;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/registeradminuser")
	public ResponseEntity<UserDTO> registerAdminUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
		return ResponseEntity.ok(authenticationService.registerAdminUser(registerRequestDTO));
	}
}
