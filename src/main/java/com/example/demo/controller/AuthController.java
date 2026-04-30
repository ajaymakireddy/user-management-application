package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entiry.User;
import com.example.demo.repositroy.UserRepository;
import com.example.demo.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	//	Register User
	//	Login User
	//  Logout User
	//	getCurrentLoggedinUser
	  
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/registernormaluser")
	public ResponseEntity<UserDTO> registerNormalUser(@RequestBody RegisterRequestDTO registerRequestDTO){
		return ResponseEntity.ok(authenticationService.registerNormalUser(registerRequestDTO));
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		LoginResponseDTO loginResponseDTO =  authenticationService.login(loginRequestDTO);
		
		ResponseCookie responseCookie = ResponseCookie.from(loginResponseDTO.getJwtToken())
				                                      .httpOnly(true)
				                                      .secure(true)
				                                      .path("/")
				                                      .maxAge(1 * 60 * 60 )
				                                      .sameSite("Strict")
				                                      .build();
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
				.body(loginResponseDTO.getUserDTO());
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(){
		return authenticationService.logout();
	}
	
	public ResponseEntity<?> getCurrentLoginUser(Authentication authentication) {
		if(authentication == null ) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
		}
		String name = authentication.getName();
		
		User user  = userRepository.findByUsername(name);
	}
}
