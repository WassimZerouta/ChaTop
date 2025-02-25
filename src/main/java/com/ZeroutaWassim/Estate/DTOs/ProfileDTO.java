package com.ZeroutaWassim.Estate.DTOs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileDTO {
	private Integer id;
	private String email;
	private String name;
	 @JsonProperty("created_at")
	private String createdAt;
	 @JsonProperty("updated_at")
	private String updatedAt;

	public ProfileDTO() {

	}

	public ProfileDTO(Integer id, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.email = email;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        this.createdAt = LocalDateTime.now().format(formatter);
        this.updatedAt = LocalDateTime.now().format(formatter);

	}

	// Getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	   public String getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(LocalDateTime createdAt) {
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	    	this.createdAt = LocalDateTime.now().format(formatter);
	    }

	    public String getUpdatedAt() {
	        return updatedAt;
	    }

	    public void setUpdatedAt(LocalDateTime updatedAt) {
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	    	this.updatedAt = LocalDateTime.now().format(formatter);
	    }
}
