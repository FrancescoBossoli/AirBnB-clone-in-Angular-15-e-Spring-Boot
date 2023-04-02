package it.epicode.capstone.config;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import it.epicode.capstone.models.Amenity;
import it.epicode.capstone.models.AmenityType;
import it.epicode.capstone.models.Bathroom;
import it.epicode.capstone.models.Booking;
import it.epicode.capstone.models.Language;
import it.epicode.capstone.models.LanguageSpoken;
import it.epicode.capstone.models.Listing;
import it.epicode.capstone.models.Property;
import it.epicode.capstone.models.Review;
import it.epicode.capstone.models.Role;
import it.epicode.capstone.models.RoleType;
import it.epicode.capstone.models.Room;
import it.epicode.capstone.models.User;
import it.epicode.capstone.models.Verification;
import it.epicode.capstone.models.VerificationType;

@Configuration
public class BeanConfig {
	
	@Bean
	@Scope("prototype")
	public Role role(RoleType r) {
		return Role.builder().name(r).build();
	}
	
	@Bean
	@Scope("prototype")
	public Verification verification(VerificationType v) {
		return Verification.builder().name(v).build();
	}
	
	@Bean
	@Scope("prototype")
	public Amenity amenity(AmenityType v) {
		return Amenity.builder().name(v).build();
	}
	
	@Bean
	@Scope("prototype")
	public Language language(LanguageSpoken v) {
		return Language.builder().name(v).build();
	}
	
	@Bean
	@Scope("prototype")
	public User user(String n, String s, String u, String pw, String e, Set<Role> r, String d, String a, 
					 String url, String ne, String loc, Set<Verification> v, Set<Language> sl) {
		User user = User.builder().name(n).surname(s).username(u).email(e).password(pw)
						.roles(r).verifications(v).spokenLanguages(sl).build();				
		if (d != "") user.setHostSince(LocalDate.parse(d));
		if (a!= "") user.setAbout(a);
		if (url!= "") user.setPictureUrl(url);
		if (ne != "") user.setNeighbourhood(ne);
		if (loc != "") user.setLocation(loc);				
		return user;
	}
	
	@Bean
	@Scope("prototype")
	public Listing listing(String n, String d, String ne, List<String> pics, User u, double lat, double lon, String loc,
							Property p, Room r, Integer c, Bathroom b, Integer rooms, Integer beds, Double price, 
							Integer min, Integer max, boolean rent, Set<Amenity> a) {
		return Listing.builder().name(n).description(d).neighborhoodOverview(ne).pictures(pics).owner(u).latitude(lat)
					  .longitude(lon).location(loc).propertyType(p).roomType(r).capacity(c).bathrooms(b).bedrooms(rooms)
					  .beds(beds).price(price).minimumStay(min).maximumStay(max).instantBookable(rent).amenities(a).build();	
	}
	
	@Bean
	@Scope("prototype")
	public Review review(Listing l, LocalDate d, User r, String c, int s) {
		return Review.builder().listing(l).date(d).reviewer(r).comment(c).score(s).build();	
	}
	
	@Bean
	@Scope("prototype")
	public Booking booking(LocalDate a, LocalDate d, Double c, Listing l, User u) {
		return Booking.builder().arrival(a).departure(d).cost(c).location(l).booker(u).build();
	}
}
