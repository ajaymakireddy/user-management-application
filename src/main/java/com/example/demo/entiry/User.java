package com.example.demo.entiry;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "users")
public class User {

	private Long id ; 
	private String username ;
	private String email ;
	private String password ;
	private boolean isActive = true ;
	private LocalDateTime createdAt ;
	private LocalDateTime updatedAt ;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles ;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	
}
