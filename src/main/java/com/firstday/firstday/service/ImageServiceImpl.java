package com.firstday.firstday.service;

import com.firstday.firstday.model.Image;
import com.firstday.firstday.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ImageServiceImpl  implements  ImageService{
	
	private final ImageRepository imageRepository;
	
	@Autowired
	public ImageServiceImpl(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}
	
	@Override
	@Transactional
	public Image saveImage(String fileName, byte[] bytes) {
		Image image = new Image();
		image.setFileName(fileName);
		image.setImage(bytes);
		imageRepository.save(image);
		return  image;
	}
	
	@Override
	public Optional<Image> getByName(String fileName) {
		return imageRepository.getByFileName(fileName);
	}
}
