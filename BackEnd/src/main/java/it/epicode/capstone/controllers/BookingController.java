package it.epicode.capstone.controllers;

import java.time.LocalDate;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.epicode.capstone.models.Booking;
import it.epicode.capstone.models.Listing;
import it.epicode.capstone.models.User;
import it.epicode.capstone.payloads.BookingRequest;
import it.epicode.capstone.payloads.MessageResponse;
import it.epicode.capstone.services.BookingService;
import it.epicode.capstone.services.ListingService;
import it.epicode.capstone.services.UserService;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin("http://localhost:4200")
public class BookingController {

	@Autowired
	private BookingService bookSrv;
	@Autowired
	private UserService userSrv;
	@Autowired
	private ListingService listSrv;
	
	@PostMapping
	public ResponseEntity<?> newBooking(@Valid @RequestBody BookingRequest request) {
		Optional<User> uOpt = userSrv.getUserById(request.getUserId());
		if (uOpt.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Error: User not Found"));
		Optional<Listing> lOpt = listSrv.getListingById(request.getLocationId());
		if (lOpt.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Error: Listing not Found"));
		User booker = uOpt.get();
		Listing location = lOpt.get();
		Booking b = Booking.builder().arrival(LocalDate.parse(request.getArrival())).departure(LocalDate.parse(request.getDeparture())).cost(request.getCost())
									 .location(location).booker(booker).build();
		bookSrv.save(b);		
		return ResponseEntity.ok(new MessageResponse("Your booking has been saved successfully!"));
	}
}
