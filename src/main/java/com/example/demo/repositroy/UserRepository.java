package com.example.demo.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entiry.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
