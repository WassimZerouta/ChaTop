package com.ZeroutaWassim.Estate.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessagesDTO {
	@JsonProperty("rental_id")
    private Integer rentalId;
	@JsonProperty("user_id")
    private Integer userId;
    private String message;

    
    public MessagesDTO(String message, Integer userId, Integer rentalId) {
    	 this.message = message;
        this.userId = userId;
        this.rentalId = rentalId;
    }
    
    public MessagesDTO() {

    }

    // Getters and setters
    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}