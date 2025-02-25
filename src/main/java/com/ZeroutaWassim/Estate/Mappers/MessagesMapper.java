package com.ZeroutaWassim.Estate.Mappers;

import com.ZeroutaWassim.Estate.DTOs.MessagesDTO;
import com.ZeroutaWassim.Estate.model.Messages;

public class MessagesMapper {

	// Map Entity to DTO
	public static MessagesDTO toDTO(Messages message) {
		if (message == null) {
			return null;
		}

		MessagesDTO messageDTO = new MessagesDTO();
		messageDTO.setRentalId(message.getRentalId());
		messageDTO.setUserId(message.getUserId());
		messageDTO.setMessage(message.getMessage());
		return messageDTO;
	}

	// Map DTO to Entity
	public static Messages toEntity(MessagesDTO messageDTO) {
		if (messageDTO == null) {
			return null;
		}

		Messages message = new Messages();
		message.setRentalId(messageDTO.getRentalId());
		message.setUserId(messageDTO.getUserId());
		message.setMessage(messageDTO.getMessage());
		return message;
	}
}
