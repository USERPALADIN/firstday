package com.firstday.firstday.service;

import com.firstday.firstday.model.Image;

import java.util.Optional;

public interface ImageService {
	
	Image saveImage(String fileName, byte[] bytes);
	
	Optional<Image> getByName(String fileName);
}
