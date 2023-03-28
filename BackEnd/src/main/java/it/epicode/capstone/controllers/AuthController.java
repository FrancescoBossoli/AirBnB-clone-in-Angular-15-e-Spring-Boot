package it.epicode.capstone.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.capstone.models.LoggedUserDetails;
import it.epicode.capstone.models.Role;
import it.epicode.capstone.models.RoleType;
import it.epicode.capstone.models.User;
import it.epicode.capstone.payloads.JwtResponse;
import it.epicode.capstone.payloads.LoginRequest;
import it.epicode.capstone.payloads.MessageResponse;
import it.epicode.capstone.payloads.RestoreRequest;
import it.epicode.capstone.payloads.SignupRequest;
import it.epicode.capstone.repositories.RoleRepository;
import it.epicode.capstone.repositories.UserRepository;
import it.epicode.capstone.utils.JwtUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class AuthController {
	
	@Autowired
	AuthenticationManager aM;
	@Autowired
	UserRepository uR;
	@Autowired
	RoleRepository rR;
	@Autowired
	PasswordEncoder pE;
	@Autowired
	JwtUtils jU;

	@PostMapping("login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		if (!uR.existsByUsername(loginRequest.getUsername()))
			return ResponseEntity.badRequest().body(new MessageResponse("Error: There is no Account associated to this Username!"));
		
		Authentication a = aM.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		return returnToken(a);
	}

	@PostMapping("signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (!Pattern.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", signUpRequest.getEmail())) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is invalid!"));
		
		if (uR.existsByUsername(signUpRequest.getUsername())) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		
		if (uR.existsByEmail(signUpRequest.getEmail())) 
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
				
		User user = User.builder().username(signUpRequest.getUsername())
								  .email(signUpRequest.getEmail())
								  .password(pE.encode(signUpRequest.getPassword()))
								  .name(signUpRequest.getName())
								  .surname(signUpRequest.getSurname()).build();

		Set<String> roles = signUpRequest.getRoles();
		Set<Role> r = new HashSet<>();

		r.add(rR.findByName(RoleType.User).get());
		if (roles != null) {			
			roles.forEach(role -> {				
				Optional<Role> x = rR.findByName(RoleType.valueOf(Character.toUpperCase(role.charAt(0)) + role.toLowerCase().substring(1)));
				if (x.isPresent()) r.add(x.get());				
			});
		}
		user.setRoles(r);
		uR.save(user);
		return returnToken(aM.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), signUpRequest.getPassword())));
	}
	
	@PostMapping("restore")
	public ResponseEntity<?> restoreUser(@Valid @RequestBody RestoreRequest restoreRequest) {
		Optional<User> uOpt = uR.findUserByUsername(jU.getUserNameFromJwtToken(restoreRequest.getToken()));
		if (uOpt.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Error: User not Found"));
		
		LoggedUserDetails u = LoggedUserDetails.build(uOpt.get());
		if (u.getUsername().equals(restoreRequest.getUsername()) == false || u.getId().equals(restoreRequest.getId()) == false)
			return ResponseEntity.badRequest().body(new MessageResponse("Error: The sent details don't match with the User's ones!"));
		
		List<String> roles = u.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());		
		return ResponseEntity.ok(new JwtResponse(restoreRequest.getToken(), u.getId(), u.getUsername(), u.getEmail(), roles, u.getName(), u.getSurname(),
				u.getHostSince(), u.getLocation(), u.getNeighbourhood(), u.getAbout(), u.getPictureUrl(), u.getSpokenLanguages(),
				u.getVerifications(), u.getListings(), u.getReviews(), u.getBookings(), u.getFavourites()));
	}
	
	public ResponseEntity<?> returnToken(Authentication a) {
		SecurityContextHolder.getContext().setAuthentication(a);
		String jwt = jU.generateJwtToken(a);
		
		LoggedUserDetails u = (LoggedUserDetails) a.getPrincipal();		
		List<String> roles = u.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, u.getId(), u.getUsername(), u.getEmail(), roles, u.getName(), u.getSurname(),
				u.getHostSince(), u.getLocation(), u.getNeighbourhood(), u.getAbout(), u.getPictureUrl(), u.getSpokenLanguages(),
				u.getVerifications(), u.getListings(), u.getReviews(), u.getBookings(), u.getFavourites()));
	}
}