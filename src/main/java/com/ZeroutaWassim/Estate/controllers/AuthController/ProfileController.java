package com.ZeroutaWassim.Estate.controllers.AuthController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ZeroutaWassim.Estate.services.CustomUserDetailsService;

@RestController
public class ProfileController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@GetMapping("/api/auth/me")
	// Retrieves the authenticated user's profile using the provided JWT token
	public ResponseEntity<Object> getProfile(@RequestHeader("Authorization") String token) {
		return customUserDetailsService.getProfile(token);
	}
}
