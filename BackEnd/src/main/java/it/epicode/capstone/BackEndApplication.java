package it.epicode.capstone;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.epicode.capstone.config.BeanConfig;
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
import it.epicode.capstone.services.AmenityService;
import it.epicode.capstone.services.BookingService;
import it.epicode.capstone.services.LanguageService;
import it.epicode.capstone.services.ListingService;
import it.epicode.capstone.services.ReviewService;
import it.epicode.capstone.services.RoleService;
import it.epicode.capstone.services.UserService;
import it.epicode.capstone.services.VerificationService;
import it.epicode.capstone.utils.DataRetriever;

@SpringBootApplication
public class BackEndApplication implements CommandLineRunner {

	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
	
	@Autowired
	private DataRetriever csvParser;
	@Autowired
	private RoleService roleServ;
	@Autowired
	private VerificationService verificationServ;
	@Autowired
	private AmenityService amenityServ;
	@Autowired
	private UserService userServ;	
	@Autowired
	private LanguageService languageServ;
	@Autowired
	private ListingService listingServ;
	@Autowired
	private ReviewService reviewServ;
	@Autowired
	private BookingService bookingServ;
	@Autowired
	private PasswordEncoder encoder;
	
	public static void main(String[] args) {		
		SpringApplication.run(BackEndApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//if (roleServ.getAllRoles().size() == 0 ) {
			populateDb();
		//}		
	}
	
	public void populateDb() {
		saveRoles();
		saveVerifications();
		saveAmenities();
		saveLanguages();
		saveUsers();
		saveListings();
		saveReviews();
		saveBookings();
	}
	
	public void saveRoles() {
		for (String[] s : csvParser.lineReader("src/main/resources/csv/roles.csv")) {
			roleServ.save((Role)ctx.getBean("role", RoleType.valueOf(s[0])));
		}
	}
	
	public void saveVerifications() {
		for (String[] s : csvParser.lineReader("src/main/resources/csv/verifications.csv")) {
			verificationServ.save((Verification)ctx.getBean("verification", VerificationType.valueOf(s[0])));
		}
	}
	
	public void saveAmenities() {
		for (String[] s : csvParser.lineReader("src/main/resources/csv/amenities.csv")) {
			amenityServ.save((Amenity)ctx.getBean("amenity", AmenityType.valueOf(s[0])));
		}
	}
	
	public void saveLanguages() {
		for (String[] s : csvParser.lineReader("src/main/resources/csv/languages.csv")) {
			languageServ.save((Language)ctx.getBean("language", LanguageSpoken.valueOf(s[0])));
		}
	}
	
	public void saveUsers() {
		for (String[] s : csvParser.lineReader("src/main/resources/csv/users.csv")) {			
			
			Set<Role> roles = new HashSet<>();		
			roles.add(roleServ.getRoleByName(RoleType.User).get());
			if (s[6] != "") {	
				String[] rSet = s[6].split("[,]");
				for (int x = 0; x < rSet.length; x++) {
					Optional<Role> rOpt = roleServ.getRoleById(Long.parseLong(rSet[x]));
					if (rOpt.isPresent()) roles.add(rOpt.get());						
				}			
			}			
			
			Set<Verification> verifications = new HashSet<>();
			if (s[12] != "") {
				String[] vSet = s[12].split("[,]");
				for (int x = 0; x < vSet.length; x++) {
					Optional<Verification> vOpt = verificationServ.getVerificationById(Long.parseLong(vSet[x]));
					if (vOpt.isPresent()) verifications.add(vOpt.get());						
				}
			}
						
			Set<Language> languages = new HashSet<>();
			if (s[13] != "") {
				String[] lSet = s[13].split("[,]");
				for (int x = 0; x < lSet.length; x++) {
					Optional<Language> lOpt = languageServ.getLanguageById(Long.parseLong(lSet[x]));
					if (lOpt.isPresent()) languages.add(lOpt.get());						
				}
			}
			
			userServ.save((User)ctx.getBean("user", s[1], s[2], s[3], encoder.encode(s[4]), s[5], 
					roles, s[7], s[8], s[9], s[10], s[11], verifications, languages));			
		}
	}
	
	public void saveListings() {
		for (String[] s : csvParser.lineReader("src/main/resources/csv/listings.csv")) {
			List<String> pics = new ArrayList<>();
			if (s[4] != null) {
				String[] pSet = s[4].split("[,]");
				for (int x = 0; x < pSet.length; x++) {
					pics.add(pSet[x]);						
				}
			}
			User u = userServ.getUserById(Long.parseLong(s[5])).get();
			
			Set<Amenity> amenities = new HashSet<>();
			if (s[19] != null) {
				String[] aSet = s[19].split("[,]");
				for (int x = 0; x < aSet.length; x++) {
					Optional<Amenity> aOpt = amenityServ.getAmenityByName(AmenityType.getEnumByString(aSet[x]));
					if (aOpt.isPresent()) amenities.add(aOpt.get());						
				}
			}
						
			listingServ.save((Listing)ctx.getBean("listing", s[1], s[2], s[3], pics, u, Double.parseDouble(s[6]), 
					Double.parseDouble(s[7]), s[8],Property.getEnumByString(s[9]), Room.getEnumByString(s[10]), Integer.parseInt(s[11])
					, Bathroom.getEnumByString(s[12]), Integer.parseInt(s[13]), Integer.parseInt(s[14]), Double.parseDouble(s[15])
					, Integer.parseInt(s[16]), Integer.parseInt(s[17]), Boolean.parseBoolean(s[18]), amenities));	
		}		
	}
	
	public void saveReviews() {
		for (String[] s : csvParser.lineReader("src/main/resources/csv/reviews.csv")) {			
			Listing l = listingServ.getListingById(Long.parseLong(s[1])).get();
			User u = userServ.getUserById(Long.parseLong(s[3])).get();
			
			reviewServ.save((Review)ctx.getBean("review", l, LocalDate.parse(s[2]), u, s[4], Integer.parseInt(s[5])));			
		}
	}
	
	public void saveBookings() {
		for (String[] s : csvParser.lineReader("src/main/resources/csv/bookings.csv")) {
			Listing l = listingServ.getListingById(Long.parseLong(s[4])).get();
			User u = userServ.getUserById(Long.parseLong(s[5])).get();
			bookingServ.save((Booking)ctx.getBean("booking", LocalDate.parse(s[1]), LocalDate.parse(s[2]), Double.parseDouble(s[3]), l, u));
		}
	}
	

}
