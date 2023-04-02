package it.epicode.capstone.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.epicode.capstone.models.Listing;
import it.epicode.capstone.payloads.MessageResponse;
import it.epicode.capstone.payloads.PublicListing;
import it.epicode.capstone.services.ListingService;

@RestController
@RequestMapping("/api/listing")
@CrossOrigin("http://localhost:4200")
public class ListingController {

	@Autowired
	private ListingService listSrv;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		List<Listing> list = listSrv.getAllListings();		
		if( list.isEmpty() ) return ResponseEntity.badRequest().body(new MessageResponse("Error: Listing List not found"));
		List<PublicListing> pList = new ArrayList<PublicListing>();
		for (Listing listing : list) {
			PublicListing pListing = PublicListing.build(listing);
			pList.add(pListing);
		}		
		return new ResponseEntity<List<PublicListing>>(pList, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		Optional<Listing> lOpt = listSrv.getListingById(id);
		if (lOpt.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Error: Listing not Found"));
		PublicListing listing = PublicListing.build(lOpt.get());
		return ResponseEntity.ok(listing);
	}
}
