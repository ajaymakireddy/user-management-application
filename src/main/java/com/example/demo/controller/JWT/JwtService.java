package com.example.demo.controller.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.example.demo.entiry.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private String secretkey;
	
	private Long jwtexpiration ;
	
	// Method to extract the userId	
	public Long extractUserById(String jwtToken) {
		
		String userIdString = extractClaim(jwtToken , claims -> claims.get("userID" , String.class));
		
		return userIdString != null ? Long.parseLong(userIdString) : null ;
	}
	
	// Method to extract the userName 
	public String extractUsername(String jwtToken) {
		return extractClaim(jwtToken , Claims::getSubject);
	}
	
	public <T> T extractClaim(String jwtToken , Function<Claims, T> claimResolver) {
		
		final Claims claims = extractAllClamis(jwtToken);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClamis(String jwtToken) {
		return Jwts
				  .parser()
				  .verifyWith(getSignInKey())
				  .build()
				  .parseSignedClaims(jwtToken)
				  .getPayload();
	}
	
	public SecretKey getSignInKey() {
		return Keys.hmacShaKeyFor(secretkey.getBytes());
	}
	
	public String generateToken(User user ) {
		return generateToken(new HashMap() , user) ;
	}
	
	public String generateToken(Map<String, Object> extractClaims, User user) {
		Map<String , Object> claims = new HashMap<>(extractClaims);
		claims.put("userId", user.getId().toString());
		
		return Jwts
				   .builder()
				   .claims(claims)
				   .subject(user.getUsername())
				   .issuedAt(new Date(System.currentTimeMillis()))
				   .expiration(new Date(System.currentTimeMillis() + jwtexpiration))
				   .signWith(getSignInKey())
				   .compact();
	}
	
	public boolean isTokenValid(String jwtToken , User user ) {
		final Long UserIdfromToken = extractUserById(jwtToken);
		
		final Long userIdDetails = user.getId();
		
		return (UserIdfromToken != null && UserIdfromToken.equals(userIdDetails)
										&& !isTokenExpired(jwtToken));
											
	}
	
	private boolean isTokenExpired(String jwtToken) {
		return extractExpiration(jwtToken).before(new Date());
	}
	
	private Date extractExpiration(String jwtToken) {
		return extractClaim(jwtToken, Claims::getExpiration);
	}

}
