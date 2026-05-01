package com.example.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.controller.JWT.JwtService;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entiry.User;
import com.example.demo.repositroy.UserRepository;

@Service
public class AuthenticationService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	public UserDTO registerNormalUser(RegisterRequestDTO registerRequestDTO) {
		if(userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
			throw new RuntimeException("User is already Register");
		}
		
		Set<String> set = new HashSet<String>();
		set.add("ROLE_USER");
		
		User user = new User();
		user.setUsername(registerRequestDTO.getUsername());
		user.setEmail(registerRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
		user.setRoles(set);
		
		User savedUser = userRepository.save(user);
		
		return convertToUserDTO(savedUser);
		
	}
	
	public UserDTO registerAdminUser(RegisterRequestDTO registerRequestDTO) {
		if(userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
			throw new RuntimeException("User already Register");
					
		}
		
		Set<String> set = new HashSet<String>();
		set.add("ROLE_ADMIN");
		set.add("ROLE_USER");
		
		User user = new User();
		user.setEmail(registerRequestDTO.getEmail());
		user.setUsername(registerRequestDTO.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
		user.setRoles(set);
		
		User save = userRepository.save(user);
		
		return convertToUserDTO(save);
		
	}
	
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
		User user = userRepository.findByUsername(loginRequestDTO.getUsername())
		                                              .orElseThrow(() -> new RuntimeException("User not found"));
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
				                               (loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
		
		String jwtToken = jwtService.generateToken(user);
		
		return LoginResponseDTO.builder()
				               .jwtToken(jwtToken)
				               .userDTO(convertToUserDTO(user))
				               .build();
		
		
	}
	
	public ResponseEntity<String> logout() {
		
       //	create a expired cookie
		
		ResponseCookie cookie = ResponseCookie.from("JWT" , "")
				                              .httpOnly(true)
				                              .secure(true)
				                              .path("/")
				                              .maxAge(0)
				                              .sameSite("Strice")
				                              .build();
		
		return ResponseEntity.ok()
				             .header(HttpHeaders.SET_COOKIE, cookie.toString())
				             .body("LOGGED OUT SUCCESSFULLY");
		
	}
	
	
	
	
	
	
	
	public UserDTO convertToUserDTO(User user ) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		userDTO.setUsername(user.getUsername());
		return userDTO;
	}
}
