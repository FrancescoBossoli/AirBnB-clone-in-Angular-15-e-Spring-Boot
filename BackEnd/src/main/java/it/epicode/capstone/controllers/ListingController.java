package it.epicode.capstone.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.epicode.capstone.models.Bathroom;
import it.epicode.capstone.models.Listing;
import it.epicode.capstone.models.Property;
import it.epicode.capstone.models.Room;
import it.epicode.capstone.models.User;
import it.epicode.capstone.payloads.MessageResponse;
import it.epicode.capstone.payloads.Place;
import it.epicode.capstone.payloads.PublicListing;
import it.epicode.capstone.payloads.SearchRequest;
import it.epicode.capstone.services.ListingService;
import it.epicode.capstone.services.UserService;
import it.epicode.capstone.utils.JwtUtils;

@RestController
@RequestMapping("/api/listing")
@CrossOrigin("http://localhost:4200")
public class ListingController {

	@Autowired
	private ListingService listSrv;
	@Autowired
	private UserService userSrv;
	@Autowired
    private HttpServletRequest request;
	@Autowired
	private JwtUtils jwt;
	
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
	
	@PostMapping("new")
	@ResponseBody
	public ResponseEntity<?> createUserListing(@RequestParam("image1") MultipartFile image1, @RequestParam("image2") MultipartFile image2,
											   @RequestParam("image3") MultipartFile image3, @RequestParam("image4") MultipartFile image4,
											   @RequestParam("image5") MultipartFile image5, @RequestParam("name") String name,
											   @RequestParam("description") String description, @RequestParam("neighborhoodOverview") String neighborhood,
											   @RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude,
											   @RequestParam("location") String location, @RequestParam("roomType") String roomType,
											   @RequestParam("propertyType") String propertyType, @RequestParam("capacity") String capacity,
											   @RequestParam("bedrooms") String bedrooms, @RequestParam("beds") String beds,
											   @RequestParam("bathrooms") String bathrooms, @RequestParam("price") String price) throws IOException {
		
		String token = request.getHeader("Authorization").substring(7);
		User user = userSrv.getUserByUsername(jwt.getUserNameFromJwtToken(token)).get();
		
		Listing listing = Listing.builder().name(name).description(description).neighborhoodOverview(neighborhood).longitude(Double.parseDouble(longitude))
										   .latitude(Double.parseDouble(latitude)).location(location).roomType(Room.valueOf(roomType)).owner(user)
										   .propertyType(Property.valueOf(propertyType)).capacity(Integer.parseInt(capacity)).bedrooms(Integer.parseInt(bedrooms))
										   .beds(Integer.parseInt(beds)).bathrooms(Bathroom.valueOf(bathrooms)).price(Double.parseDouble(price)).build();
		listSrv.save(listing);
		listing = listSrv.getListingByLongitudeAndLatitude(Double.parseDouble(longitude), Double.parseDouble(latitude)).get();
		List<MultipartFile> list = Arrays.asList(new MultipartFile[]{image1, image2, image3, image4, image5});
		int i = 1;
		List<String> pictures = new ArrayList<String>();
		for (MultipartFile image : list) {
			 String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."), image.getOriginalFilename().length());
			 String path = "src/main/resources/img/listing/" + listing.getId() + "-" + i++ + extension;
			 File file = new File(path);
			 try (OutputStream os = new FileOutputStream(file)) {
			    os.write(image.getBytes());
			 }
			 pictures.add("BackEnd/" + path);
		}
		listing.setPictures(pictures);
		listSrv.edit(listing);		
		return ResponseEntity.ok(new MessageResponse("The listing has been added to the database"));
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
	
	@GetMapping("islands")
	public ResponseEntity<?> getIslands() {
		List<PublicListing> list = getListingsRange(84,95);	
		return new ResponseEntity<List<PublicListing>>(list, HttpStatus.OK);
	}
	
	@GetMapping("trending")
	public ResponseEntity<?> getTrending() {
		List<PublicListing> list = getListingsRange(96,107);	
		return new ResponseEntity<List<PublicListing>>(list, HttpStatus.OK);
	}
	
	@GetMapping("treehouses")
	public ResponseEntity<?> getTreehouses() {
		List<PublicListing> list = getListingsRange(108,119);	
		return new ResponseEntity<List<PublicListing>>(list, HttpStatus.OK);
	}
	
	@GetMapping("topOfTheWorld")
	public ResponseEntity<?> getTopOfTheWorld() {
		List<PublicListing> list = getListingsRange(120,131);	
		return new ResponseEntity<List<PublicListing>>(list, HttpStatus.OK);
	}
	
	public List<PublicListing> getListingsRange(int a, int b) {
		List<Listing> list = listSrv.getListingsRange(a, b);
		List<PublicListing> pList = new ArrayList<PublicListing>();
		for (Listing listing : list) {
			PublicListing pListing = PublicListing.build(listing);
			pList.add(pListing);
		}
		return pList;
	}
}
