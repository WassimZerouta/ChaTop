package com.ZeroutaWassim.Estate.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ZeroutaWassim.Estate.DTOs.ProfileDTO;
import com.ZeroutaWassim.Estate.DTOs.UserDTO;
import com.ZeroutaWassim.Estate.Mappers.UserMapper;
import com.ZeroutaWassim.Estate.model.DBUser;
import com.ZeroutaWassim.Estate.repository.DBUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private DBUserRepository dbUserRepository;

	@Lazy
	@Autowired
	private JWTService jwtService;

	@Lazy
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	// Loads user details by username for authentication
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DBUser user = dbUserRepository.findByUsername(username);

		return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities("USER"));

	}

	// Finds a user by email
	public DBUser findUserByEmail(String email) {
		return dbUserRepository.findByEmail(email);
	}

	// Handles user login by verifying credentials and generating token
	public ResponseEntity<Object> login(String login, String password) {

		DBUser user = dbUserRepository.findByEmail(login);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "error"));
		}

		if (!passwordEncoder.matches(password, user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "error"));
		}

		Authentication authentication = createAuthenticationToken(user);

		String token = jwtService.generateToken(authentication);

		Map<String, String> response = new HashMap<>();
		response.put("token", token);

		return ResponseEntity.ok(response);
	}

	// Registers a new user, encrypts their password, and return token
	public ResponseEntity<Object> register(UserDTO userDTO) {
		if (dbUserRepository.findByUsername(userDTO.getName()) != null) {
			return ResponseEntity.badRequest().build();
		}
		DBUser newUser = UserMapper.toEntity(userDTO);

		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

		dbUserRepository.save(newUser);

		Authentication authentication = createAuthenticationToken(newUser);
		String token = jwtService.generateToken(authentication);
		Map<String, String> response = new HashMap<>();
		response.put("token", token);
		return ResponseEntity.ok(response);

	}

	// Retrieves the authenticated user's profile using token.
	public ResponseEntity<Object> getProfile(@RequestHeader("Authorization") String token) {
		String email = jwtService.extractEmailFromToken(token);

		if (email == null || email.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		DBUser user = dbUserRepository.findByEmail(email);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		ProfileDTO profile = new ProfileDTO();
		profile.setId(user.getId());
		profile.setName(user.getUsername());
		profile.setEmail(user.getEmail());
		profile.setCreatedAt(user.getCreatedAt());
		profile.setUpdatedAt(user.getUpdatedAt());
		System.out.println(profile.getCreatedAt());

		return ResponseEntity.ok(profile);
	}

	private List<GrantedAuthority> getGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}

	// Create authentication token for user
	public Authentication createAuthenticationToken(DBUser user) {
		return new UsernamePasswordAuthenticationToken(user.getEmail(), null, getGrantedAuthorities("USER"));
	}
}