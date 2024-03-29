package it.epicode.capstone.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.capstone.models.Listing;
import it.epicode.capstone.models.LoggedUserDetails;
import it.epicode.capstone.models.User;
import it.epicode.capstone.payloads.MessageResponse;
import it.epicode.capstone.services.ListingService;
import it.epicode.capstone.services.UserService;

@RestController
@RequestMapping("/api/favourite")
@CrossOrigin("http://localhost:4200")
public class FavouriteController {
	
	@Autowired
	private UserService userSrv;
	@Autowired
	private ListingService listSrv;
		
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		LoggedUserDetails loggedUser = (LoggedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> uOpt = userSrv.getUserByUsername(loggedUser.getUsername());
		if (uOpt.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Error: User not Found"));
		User u = uOpt.get();
		Set<Listing> oldFavourites = u.getFavourites();
		Set<Listing> favourites = new HashSet<Listing>();
		for (Listing fav : oldFavourites) if (fav.getId() != id) favourites.add(fav);		
		u.setFavourites(favourites);		
		userSrv.edit(u);
		return ResponseEntity.ok(new MessageResponse("The favourite listing has been deleted!"));
	}
	
	@PostMapping("{id}")
	public ResponseEntity<?> add(@PathVariable Long id) {
		LoggedUserDetails loggedUser = (LoggedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> uOpt = userSrv.getUserByUsername(loggedUser.getUsername());
		if (uOpt.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Error: User not Found"));
		User u = uOpt.get();
		Optional<Listing> lOpt = listSrv.getListingById(id);
		if (lOpt.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Error: Preferred Listing not Found"));
		Set<Listing> favourites = u.getFavourites();
		favourites.add(lOpt.get());
		u.setFavourites(favourites);
		userSrv.edit(u);
		return ResponseEntity.ok(new MessageResponse("The favourite listing has been added!"));
	}

}
