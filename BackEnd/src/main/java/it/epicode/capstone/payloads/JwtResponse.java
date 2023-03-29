package it.epicode.capstone.payloads;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.epicode.capstone.models.Booking;
import it.epicode.capstone.models.Language;
import it.epicode.capstone.models.Listing;
import it.epicode.capstone.models.Review;
import it.epicode.capstone.models.Verification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
    private String name;
	private String surname;
	private LocalDate hostSince;
	private String location;
	private String neighbourhood;
	private String about;	
	private String pictureUrl;
	private Set<Language> spokenLanguages;
	private Set<Verification> verifications;
	private Set<PublicListing> listings;
	private Set<Review> reviews;
	private Set<Booking> bookings;
	private Set<PublicListing> favourites;

	public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles, String name, String surname, 
			LocalDate hostSince, String location, String neighbourhood, String about, String pictureUrl, Set<Language> spokenLanguages, 
			Set<Verification> verifications, Set<Listing> listings, Set<Review> reviews, Set<Booking> bookings, Set<Listing> favourites) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.name = name;
		this.surname = surname;
		this.hostSince = hostSince;
		this.location = location;
		this.neighbourhood = neighbourhood;
		this.about = about;	
		this.pictureUrl = pictureUrl;
		this.spokenLanguages = spokenLanguages;
		this.verifications = verifications;		
		this.reviews = reviews;
		this.bookings = bookings;		
		this.listings = publicParse(listings);				
		this.favourites = publicParse(favourites);
	}
	
	public Set<PublicListing> publicParse(Set<Listing> listings) {
		Set<PublicListing> pubListings = new HashSet<PublicListing>();
		for (Listing listing : listings) {
			PublicListing pubListing = PublicListing.build(listing);
			pubListings.add(pubListing);
		}
		return pubListings;
	}
}