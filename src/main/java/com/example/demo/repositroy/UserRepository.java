package com.example.demo.repositroy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entiry.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
}
