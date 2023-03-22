package it.epicode.capstone.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

import it.epicode.capstone.models.AccessDetails;
import it.epicode.capstone.models.Role;
import it.epicode.capstone.models.RoleType;
import it.epicode.capstone.models.User;
import it.epicode.capstone.payloads.JwtResponse;
import it.epicode.capstone.payloads.LoginRequest;
import it.epicode.capstone.payloads.MessageResponse;
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
		Authentication a = aM.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		return returnToken(a);
	}

	@PostMapping("signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (uR.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (uR.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		
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
	
	public ResponseEntity<?> returnToken(Authentication a) {
		SecurityContextHolder.getContext().setAuthentication(a);
		String jwt = jU.generateJwtToken(a);
		
		AccessDetails uD = (AccessDetails) a.getPrincipal();		
		List<String> roles = uD.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, uD.getId(), uD.getUsername(), uD.getEmail(), roles));
	}
}