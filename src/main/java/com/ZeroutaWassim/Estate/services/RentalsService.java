package com.ZeroutaWassim.Estate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ZeroutaWassim.Estate.DTOs.RentalsDTO;
import com.ZeroutaWassim.Estate.DTOs.RentalsResponseDTO;
import com.ZeroutaWassim.Estate.Mappers.RentalsMapper;
import com.ZeroutaWassim.Estate.model.DBUser;
import com.ZeroutaWassim.Estate.model.Rentals;
import com.ZeroutaWassim.Estate.model.RentalsResponse;
import com.ZeroutaWassim.Estate.repository.DBUserRepository;
import com.ZeroutaWassim.Estate.repository.RentalsRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RentalsService {

	@Autowired
	private final RentalsRepository rentalsRepository;
	private final StorageService storageService;
	private final RentalsMapper rentalsMapper;
	private final DBUserRepository dbUserRepository;

	@Lazy
	@Autowired
	private JWTService jwtService;

	public RentalsService(StorageService storageService, RentalsRepository rentalRepository,
			RentalsMapper rentalsMapper, DBUserRepository dbUserRepository) {
		this.storageService = storageService;
		this.rentalsRepository = rentalRepository;
		this.rentalsMapper = rentalsMapper;
		this.dbUserRepository = dbUserRepository;
	}

	// Retrieves all rentals, after verifying user authentication
	public ResponseEntity<RentalsResponse> getAllRentals(@RequestHeader("Authorization") String token) {
		String email = jwtService.extractEmailFromToken(token);

		if (email == null || email.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		DBUser user = dbUserRepository.findByEmail(email);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		List<Rentals> rentals = rentalsRepository.findAll();

		RentalsResponse response = new RentalsResponse();
		response.setRentals(rentals);
		
		return ResponseEntity.ok(response);
	}

	// Retrieves a specific rental by ID, after verifying user authentication
	public ResponseEntity<RentalsResponseDTO> getRentalById(@RequestHeader("Authorization") String token, Integer id) {
		String email = jwtService.extractEmailFromToken(token);

		if (email == null || email.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		DBUser user = dbUserRepository.findByEmail(email);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return rentalsRepository.findById(id).map(rental -> ResponseEntity.ok(rentalsMapper.toResponseDTO(rental)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	// Updates an existing rental by ID, after verifying user authentication
	public ResponseEntity<Object> updateRental(@RequestHeader("Authorization") String token, Integer id,
			RentalsDTO rentalDTO) {

		String email = jwtService.extractEmailFromToken(token);

		if (email == null || email.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		DBUser user = dbUserRepository.findByEmail(email);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Rentals Updatedrental = rentalsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Rental not found: " + id));

		if (rentalDTO.getName() != null) {
			Updatedrental.setName(rentalDTO.getName());
		}
		if (rentalDTO.getSurface() != null) {
			Updatedrental.setSurface(rentalDTO.getSurface());
		}
		if (rentalDTO.getPrice() != null) {
			Updatedrental.setPrice(rentalDTO.getPrice());
		}
		if (rentalDTO.getDescription() != null) {
			Updatedrental.setDescription(rentalDTO.getDescription());
		}
		if (rentalDTO.getOwnerId() != null) {
			Updatedrental.setOwnerId(rentalDTO.getOwnerId());
		}

		Updatedrental.setUpdatedAt(LocalDateTime.now());

		rentalsRepository.save(Updatedrental);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Rental updated !");
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	// Creates a new rental, , after verifying user authentication
	public ResponseEntity<Object> saveRental(@RequestHeader("Authorization") String token,
			@ModelAttribute RentalsDTO rentalDTO) {

		String email = jwtService.extractEmailFromToken(token);

		if (email == null || email.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		DBUser user = dbUserRepository.findByEmail(email);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		rentalDTO.setOwnerId(user.getId());

		try {
			String url = storageService.saveImage(rentalDTO.getPicture());

			Rentals rental = rentalsMapper.toEntity(rentalDTO);
			rental.setPicture(url);

			rentalsRepository.save(rental);

		} catch (IOException e) {
			throw new RuntimeException("Error", e);
		}

		Map<String, String> response = new HashMap<>();
		response.put("message", "Rental created !");
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}