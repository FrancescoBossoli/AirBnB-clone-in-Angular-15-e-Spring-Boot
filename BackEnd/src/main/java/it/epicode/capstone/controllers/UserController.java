package it.epicode.capstone.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.capstone.models.User;
import it.epicode.capstone.payloads.MessageResponse;
import it.epicode.capstone.services.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:4200")
public class UserController {

	@Autowired
	private UserService userSrv;
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
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<User> user = userSrv.getUserById(id);		
		if (user.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Error: User not Found"));
		else userSrv.deleteUserById(user.get().getId());		
		return ResponseEntity.ok(new MessageResponse("The user has been deleted!"));
	}
}
