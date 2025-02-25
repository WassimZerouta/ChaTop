package com.ZeroutaWassim.Estate.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ZeroutaWassim.Estate.DTOs.RentalsResponseDTO;
import com.ZeroutaWassim.Estate.model.RentalsResponse;

import com.ZeroutaWassim.Estate.DTOs.RentalsDTO;
import com.ZeroutaWassim.Estate.services.RentalsService;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

	private final RentalsService rentalsService;

	public RentalsController(RentalsService rentalsService) {
		this.rentalsService = rentalsService;
	}

	@GetMapping
	// Retrieves all rentals, requiring authentication
	public ResponseEntity<RentalsResponse> getAllRentals(@RequestHeader("Authorization") String token) {
		return rentalsService.getAllRentals(token);
	}

	@GetMapping("/{id}")
	// Retrieves a specific rental by ID, requiring authentication
	public ResponseEntity<RentalsResponseDTO> getRentalById(@RequestHeader("Authorization") String token,
			@PathVariable Integer id) {
		return rentalsService.getRentalById(token, id);
	}

	@PostMapping
	// Creates a new rental, requiring authentication
	public ResponseEntity<Object> createRental(@RequestHeader("Authorization") String token,
			@ModelAttribute RentalsDTO rentalDTO) {
		return rentalsService.saveRental(token, rentalDTO);

	}

	@PutMapping("/{id}")
	//Updates an existing rental by ID, requiring authentication
	public ResponseEntity<Object> updateRental(@RequestHeader("Authorization") String token, @PathVariable Integer id,
			@ModelAttribute RentalsDTO updatedRentalDTO) {
		return rentalsService.updateRental(token, id, updatedRentalDTO);

	}

}
