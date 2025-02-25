package com.ZeroutaWassim.Estate.controllers.AuthController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZeroutaWassim.Estate.DTOs.LoginDTO;
import com.ZeroutaWassim.Estate.services.CustomUserDetailsService;

@RestController
public class LoginController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@PostMapping("/api/auth/login")
	// Handles user login
	public ResponseEntity<Object> login(@RequestBody LoginDTO loginDto) {
		return customUserDetailsService.login(loginDto.getLogin(), loginDto.getPassword());
	}

}
