package com.firstday.firstday.repository;

import com.firstday.firstday.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

	Optional<Image> getByFileName(String fileName);
}
