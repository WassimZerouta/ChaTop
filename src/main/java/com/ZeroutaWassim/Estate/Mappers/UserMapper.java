package com.ZeroutaWassim.Estate.Mappers;

import java.time.LocalDateTime;

import com.ZeroutaWassim.Estate.DTOs.UserDTO;
import com.ZeroutaWassim.Estate.model.DBUser;

public class UserMapper {

	// Map entity to DTO
	public static UserDTO toDTO(DBUser dbUser) {
		if (dbUser == null) {
			return null;
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(dbUser.getEmail());
		userDTO.setName(dbUser.getUsername());
		userDTO.setPassword(dbUser.getPassword());
		userDTO.setCreatedAt(dbUser.getCreatedAt());
		userDTO.setUpdatedAt(dbUser.getUpdatedAt());
		return userDTO;
	}

	// Map DTO to entity
	public static DBUser toEntity(UserDTO userDTO) {
		if (userDTO == null) {
			return null;
		}
		DBUser dbUser = new DBUser();
		dbUser.setEmail(userDTO.getEmail());
		dbUser.setUsername(userDTO.getName());
		dbUser.setPassword(userDTO.getPassword());
		dbUser.setCreatedAt(LocalDateTime.now());
		dbUser.setUpdatedAt(LocalDateTime.now());
		return dbUser;
	}

}
