package com.ZeroutaWassim.Estate.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.ZeroutaWassim.Estate.model.DBUser;
import com.ZeroutaWassim.Estate.repository.DBUserRepository;

@Service
public class JWTService {

	@Autowired
	private DBUserRepository dbUserRepository;

	private JwtEncoder jwtEncoder;
	private JwtDecoder jwtDecoder;

	public JWTService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
		this.jwtEncoder = jwtEncoder;
		this.jwtDecoder = jwtDecoder;

	}

	// Finds a user by email
	public DBUser findUserByEmail(String email) {
		return dbUserRepository.findByEmail(email);
	}

	// Generates a JWT token for the authenticated user, valid for 1 day
	public String generateToken(Authentication authentication) {
		Instant now = Instant.now();

		JwtClaimsSet claims = JwtClaimsSet.builder().subject(authentication.getPrincipal().toString()).issuedAt(now)
				.expiresAt(now.plus(1, ChronoUnit.DAYS)).build();
		System.out.println(authentication.getName());
		JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters
				.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
		return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
	}

	// Extracts the email from token.
	public String extractEmailFromToken(String token) {
		String sanitizedToken = removeBearerPrefix(token);
		if (sanitizedToken != null && !sanitizedToken.isEmpty()) {
			try {
				Jwt claims = this.jwtDecoder.decode(sanitizedToken);
				return claims.getSubject();
			} catch (Exception e) {
				System.err.println("Invalid token: " + e.getMessage());
			}
		}
		return "";
	}

	// Removes the "Bearer " prefix from the token
	public String removeBearerPrefix(String token) {
		if (token != null && token.startsWith("Bearer ")) {
			return token.substring(7).trim();
		}
		return null;
	}
}
