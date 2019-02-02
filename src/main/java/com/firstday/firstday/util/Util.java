package com.firstday.firstday.util;

import com.firstday.firstday.event.CustomEvent;
import com.firstday.firstday.model.Image;
import com.firstday.firstday.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.io.File;;
import java.util.Optional;


@Component
public class Util {
	private final ImageService imageService;
	
	private final ApplicationEventPublisher eventPublisher;
	
	private final Writer writer;
	
	@Value("${folder}")
	private String folder;
	
	
	@Scheduled(fixedRate = 2000)
	public void run() {
		File fl = new File(folder);
		File[] folderEntries = fl.listFiles();
		for (File entry : folderEntries) {
			if (entry.isDirectory()) {
				continue;
			} else {
				Optional<Image> name = imageService.getByName(entry.getName());
				
				if (name.isPresent()) {
					continue;
				}
				eventPublisher.publishEvent(new CustomEvent("", "Такого файла нет в бд"));
				writer.operationImage(entry);
				
			}
		}
	}
	
	
	@Autowired
	public Util(ImageService imageService, Writer writer, ApplicationEventPublisher eventPublisher) {
		this.imageService = imageService;
		this.writer = writer;
		this.eventPublisher = eventPublisher;
	}
}
