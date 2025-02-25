package com.ZeroutaWassim.Estate.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ZeroutaWassim.Estate.repository.DBUserRepository;
import com.ZeroutaWassim.Estate.repository.MessagesRepository;
import com.ZeroutaWassim.Estate.DTOs.MessagesDTO;
import com.ZeroutaWassim.Estate.Mappers.MessagesMapper;
import com.ZeroutaWassim.Estate.model.DBUser;
import com.ZeroutaWassim.Estate.model.Messages;

@Service
public class MessagesService {

	@Autowired
	private final MessagesRepository messagesRepository;
	private final DBUserRepository dbUserRepository;

	@Lazy
	@Autowired
	private JWTService jwtService;

	public MessagesService(MessagesRepository messagesRepository, DBUserRepository dbUserRepository) {
		this.messagesRepository = messagesRepository;
		this.dbUserRepository = dbUserRepository;
	}

	// Saves message to the database after verifying user authentication
	public ResponseEntity<Object> saveMessage(@RequestHeader("Authorization") String token,
			@RequestBody MessagesDTO messageDTO) {

		String email = jwtService.extractEmailFromToken(token);

		if (email == null || email.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		DBUser user = dbUserRepository.findByEmail(email);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Messages message = MessagesMapper.toEntity(messageDTO);
		messagesRepository.save(message);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Message send with success");
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}