package com.firstday.firstday.util;

import com.firstday.firstday.event.CustomEvent;
import com.firstday.firstday.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Component
public class Writer {
	
	private final ApplicationEventPublisher eventPublisher;
	private final ImageService imageService;
	
	@Autowired
	public Writer(ImageService imageService, ApplicationEventPublisher eventPublisher) {
		this.imageService = imageService;
		this.eventPublisher = eventPublisher;
	}
	
	@Async(value = "executor1")
	public  void operationImage(File file){
		
		eventPublisher.publishEvent(new CustomEvent("", "запись в файл"));
		System.out.println(Thread.currentThread().getName());
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedImage result = new BufferedImage(
				image.getWidth(),
				image.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);
		
		Graphics2D graphic = result.createGraphics();
		graphic.drawImage(image, 0, 0, Color.WHITE, null);
		graphic.dispose();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(result, "jpg", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();
		
		imageService.saveImage(file.getName(), bytes);
	}
}
