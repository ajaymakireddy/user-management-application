package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entiry.User;
import com.example.demo.repositroy.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserDTO getUserById(Long id ) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		return convertToUserDTO(user);
	}
	
	
	public UserDTO getUserByUsername(String name ) {
		User user = userRepository.findByUsername(name).orElseThrow(() -> new RuntimeException("User not found"));
		return convertToUserDTO(user);
	}
	
	public List<UserDTO> getAllUsers(){
		List<User> listOfUsers = userRepository.findAll();
		 return listOfUsers.stream()
				 		   .map(this::convertToUserDTO)
				 		   .collect(Collectors.toList());
	}
	
	public UserDTO convertToUserDTO(User user ) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setUsername(user.getUsername());
		return userDTO;
	}
}
