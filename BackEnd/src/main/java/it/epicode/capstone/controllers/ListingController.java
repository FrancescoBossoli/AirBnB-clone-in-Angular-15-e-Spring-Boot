package it.epicode.capstone.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.epicode.capstone.models.Listing;
import it.epicode.capstone.payloads.MessageResponse;
import it.epicode.capstone.payloads.Place;
import it.epicode.capstone.payloads.PublicListing;
import it.epicode.capstone.payloads.SearchRequest;
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
	
	@PostMapping("location")
	public ResponseEntity<?> getByPartialLocation(@Valid @RequestBody String s) {
		Set<Place> set = listSrv.getLocationsFromInput(s);
		if (set.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Error: Location not Found"));		
		return ResponseEntity.ok(set);
	}
	
	@PostMapping
	public ResponseEntity<List<Listing>> searchForListings(@Valid @RequestBody SearchRequest req) {
		LocalDate arrival = LocalDate.now();
		boolean checkA = false;
		if (req.getArrival() != "" && req.getArrival() != null) {
			checkA = true;
			arrival = LocalDate.parse(req.getArrival());
		}
		LocalDate departure = LocalDate.now();
		boolean checkB = false;
		if (req.getDeparture() != "" && req.getDeparture() != null) {
			checkB = true;
			departure = LocalDate.parse(req.getDeparture());
		}
		int people = 0;
		if (req.getPeople() != 0) people = req.getPeople();
		List<Listing> list = new ArrayList<Listing>();
		if (checkA && checkB && req.getLocation() != "" && people != 0) 
			list = listSrv.getListingsByLocationAndDatesAndCapacity(req.getLocation(), arrival, departure, people);
		else if (checkA && checkB && req.getLocation() != "" && people == 0) 
			list = listSrv.getListingsByLocationAndDates(req.getLocation(), arrival, departure);
		else if (checkA && checkB && req.getLocation() == "" && people != 0) 
			list = listSrv.getListingsByDatesAndPeople(arrival, departure, people);
		else if (checkA && checkB && req.getLocation() == "" && people == 0) 
			list = listSrv.getListingsByDates(arrival, departure);
		else if (checkA && !checkB && req.getLocation() != "" && people != 0) 
			list = listSrv.getListingsByLocationAndArrivalAndCapacity(req.getLocation(), arrival, people);
		else if (checkA && !checkB && req.getLocation() != "" && people == 0) 
			list = listSrv.getListingsByLocationAndArrival(req.getLocation(), arrival);
		else if (checkA && !checkB && req.getLocation() == "" && people != 0) 
			list = listSrv.getListingsByArrivalAndCapacity(arrival, people);
		else if (checkA && !checkB && req.getLocation() == "" && people == 0) 
			list = listSrv.getListingsByArrival(arrival);
		else if (!checkA && !checkB && req.getLocation() != "" && people != 0)
			list = listSrv.getListingsByLocationAndCapacity(req.getLocation(), people);
		else if (!checkA && !checkB && req.getLocation() != "" && people == 0)
			list = listSrv.getListingsByLocation(req.getLocation());
		else if (!checkA && !checkB && req.getLocation() == "" && people != 0)
			list = listSrv.getListingsByPeople(people);		
		return ResponseEntity.ok(list);
	}
}
