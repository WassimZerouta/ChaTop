package com.ZeroutaWassim.Estate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ZeroutaWassim.Estate.DTOs.MessagesDTO;
import com.ZeroutaWassim.Estate.services.MessagesService;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {
	
	@Autowired
    private final MessagesService messagesService;

    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }
    
	@PostMapping
	// Creates a new message
	public ResponseEntity<Object> createMessage(@RequestHeader("Authorization") String token, @RequestBody MessagesDTO messageDTO) {
	    return messagesService.saveMessage(token, messageDTO);
	}
}
