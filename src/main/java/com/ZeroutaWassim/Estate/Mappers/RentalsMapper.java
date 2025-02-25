package com.ZeroutaWassim.Estate.Mappers;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ZeroutaWassim.Estate.DTOs.RentalsDTO;
import com.ZeroutaWassim.Estate.DTOs.RentalsResponseDTO;
import com.ZeroutaWassim.Estate.model.Rentals;
import com.ZeroutaWassim.Estate.services.StorageService;

@Component
public class RentalsMapper {

	private final StorageService storageService;

	@Autowired
	public RentalsMapper(StorageService storageService) {
		this.storageService = storageService;
	}

	// Map DTO to Entity
	public Rentals toEntity(RentalsDTO rentalsDTO) throws IOException {
		if (rentalsDTO == null) {
			return null;
		}

		Rentals rental = new Rentals();
		rental.setName(rentalsDTO.getName());
		rental.setSurface(rentalsDTO.getSurface());
		rental.setPrice(rentalsDTO.getPrice());
		rental.setDescription(rentalsDTO.getDescription());
		rental.setOwnerId(rentalsDTO.getOwnerId());
		rental.setCreatedAt(LocalDateTime.now());
		rental.setUpdatedAt(LocalDateTime.now());
		if (rentalsDTO.getPicture() != null && !rentalsDTO.getPicture().isEmpty()) {
			String picturePath = storageService.saveImage(rentalsDTO.getPicture());
			rental.setPicture(picturePath);
		}

		return rental;
	}

	// Map DTO to Entity
	public RentalsDTO toDTO(Rentals rental) {
		if (rental == null) {
			return null;
		}

		RentalsDTO rentalDTO = new RentalsDTO();
		rentalDTO.setId(rental.getId());
		rentalDTO.setName(rental.getName());
		rentalDTO.setSurface(rental.getSurface());
		rentalDTO.setPrice(rental.getPrice());
		rentalDTO.setDescription(rental.getDescription());
		rentalDTO.setOwnerId(rental.getOwnerId());
		rentalDTO.setCreatedAt(rental.getCreatedAt());
		rentalDTO.setUpdatedAt(rental.getUpdatedAt());

		return rentalDTO;
	}

		// Map Entity to ResponseDTO
	    public RentalsResponseDTO toResponseDTO(Rentals rental) {
        if (rental == null) {
            return null;
        }

        RentalsResponseDTO responseDTO = new RentalsResponseDTO();
        responseDTO.setId(rental.getId());
        responseDTO.setName(rental.getName());
        responseDTO.setSurface(rental.getSurface());
        responseDTO.setPrice(rental.getPrice());
        responseDTO.setDescription(rental.getDescription());
        responseDTO.setOwnerId(rental.getOwnerId());
        responseDTO.setCreatedAt(rental.getCreatedAt());
        responseDTO.setUpdatedAt(rental.getUpdatedAt());
        responseDTO.setPicture(rental.getPicture());

        return responseDTO;
    }
}
