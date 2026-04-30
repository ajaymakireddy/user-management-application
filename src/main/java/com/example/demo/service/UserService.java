package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ChangePasswordDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entiry.User;
import com.example.demo.repositroy.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
	
	public UserDTO changePassword(Long id , ChangePasswordDTO changePasswordDTO) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		
		if(!passwordEncoder.matches(user.getPassword(), changePasswordDTO.getCurrentPassword())) {
			throw new RuntimeException("Current password is incorrect");
		}
		
		if(!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
			throw new RuntimeException("New password and confrim password does not match");
		}
		
		user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
		
		User save = userRepository.save(user);
		return convertToUserDTO(save);
		
	}
	
	public UserDTO updateUser(Long id , UserDTO userDTO ) {
		User user  = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User does not exit"));
		
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getUsername());
		
		User save = userRepository.save(user);
		return convertToUserDTO(save);
	}
	
	public void deleteUser(Long id ) {
		userRepository.deleteById(id);
	}
	
	public UserDTO convertToUserDTO(User user ) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setUsername(user.getUsername());
		return userDTO;
	}
}
