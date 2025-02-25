package com.ZeroutaWassim.Estate.controllers.AuthController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZeroutaWassim.Estate.DTOs.UserDTO;
import com.ZeroutaWassim.Estate.services.CustomUserDetailsService;

@RestController
public class RegisterController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@PostMapping("/api/auth/register")
	// Handles user registration
	public ResponseEntity<Object> register(@RequestBody UserDTO userDTO) {
		return customUserDetailsService.register(userDTO);

	}
}