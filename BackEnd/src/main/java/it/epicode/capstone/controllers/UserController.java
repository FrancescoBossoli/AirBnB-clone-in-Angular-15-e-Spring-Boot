package it.epicode.capstone.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.epicode.capstone.models.User;
import it.epicode.capstone.payloads.MessageResponse;
import it.epicode.capstone.payloads.PublicUser;
import it.epicode.capstone.services.UserService;
import it.epicode.capstone.utils.JwtUtils;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:4200")
public class UserController {

	@Autowired
	private UserService userSrv;
	@Autowired
    private HttpServletRequest request;
	@Autowired
	private JwtUtils jwt;
	@Autowired
	PasswordEncoder pE;
	
	@GetMapping
	public ResponseEntity<List<User>> getAll() {
		List<User> list = userSrv.getAllUsers();		
		if( list.isEmpty() ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		Optional<User> user = userSrv.getUserById(id);
		if (user.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Error: User not Found"));		 
		return ResponseEntity.ok(user.get());
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
		Optional<User> user = userSrv.getUserById(id);		
		if (user.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Error: User not Found"));
		else userSrv.deleteUserById(user.get().getId());		
		return ResponseEntity.ok(new MessageResponse("The user has been deleted!"));
	}
	
	@PostMapping
	public ResponseEntity<MessageResponse> setUserPicture(@RequestParam("image") MultipartFile image) throws IOException {
		String token = request.getHeader("Authorization").substring(7);
		User user = userSrv.getUserByUsername(jwt.getUserNameFromJwtToken(token)).get();
	    
	    String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."), image.getOriginalFilename().length());
	    String path = "src/main/resources/img/user/" + user.getId() + extension;
	    File file = new File(path);
	    try (OutputStream os = new FileOutputStream(file)) {
	        os.write(image.getBytes());
	    }
	    user.setPictureUrl("BackEnd/" + path);
	    userSrv.edit(user);
		return ResponseEntity.ok(new MessageResponse("The image has been uploaded!"));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<MessageResponse> editUserParam(@PathVariable Long id, @Valid @RequestBody PublicUser u) {
		Optional<User> userOpt = userSrv.getUserById(id);		
		if (userOpt.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Error: User not Found"));
		User user = userOpt.get();
		user.setAbout(u.getAbout());
		user.setLocation(u.getLocation());		
	    userSrv.edit(user);
		return ResponseEntity.ok(new MessageResponse("The image has been uploaded!"));
	}
}
