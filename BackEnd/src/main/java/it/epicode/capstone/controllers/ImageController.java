package it.epicode.capstone.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/BackEnd/src/main/resources/img")
@CrossOrigin("http://localhost:4200")
public class ImageController {

	
	@GetMapping("/user/{imageName}")
	public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
	    Resource image = new FileSystemResource("src/main/resources/img/user/" + imageName);
	    return ResponseEntity.ok()
	        .contentType(MediaType.IMAGE_JPEG).body(image);
	}
}
