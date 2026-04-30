package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ChangePasswordDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/getuserbyid/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@GetMapping("/getuserbyname/{username}")
	public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username ){
		return ResponseEntity.ok(userService.getUserByUsername(username));
	}
	
	@GetMapping("/getallusers")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@PutMapping("/changepasswod/{id}")
	public ResponseEntity<UserDTO> changePassword(@PathVariable Long id , ChangePasswordDTO changePasswordDTO ) {
		return ResponseEntity.ok(userService.changePassword(id , changePasswordDTO));
	}
	
	@PutMapping("/updateuser/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id , @RequestBody UserDTO userDTO ) {
		return ResponseEntity.ok(userService.updateUser(id , userDTO));
	}
	
	@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id ) { 
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}
}
