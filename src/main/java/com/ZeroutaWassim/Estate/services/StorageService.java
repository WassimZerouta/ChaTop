package com.ZeroutaWassim.Estate.services;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Value("${file.base-url}")
	private String baseUrl;

	// Saves an uploaded image to the storage directory and returns its accessible URL
	public String saveImage(MultipartFile image) throws IOException {
		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
		Path filePath = uploadPath.resolve(fileName);

		Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		return baseUrl + "/" + fileName;
	}
}
