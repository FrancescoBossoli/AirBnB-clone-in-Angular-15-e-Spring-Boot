package it.epicode.capstone.models;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoggedUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String email;	
	private String password;
	private String name;
	private String surname;
	private LocalDate hostSince;
	private String location;
	private String neighbourhood;
	private String about;	
	private String pictureUrl;
	private Set<Language> spokenLanguages;
	private Set<Verification> verifications;
	private Set<Listing> listings;
	private Set<Review> reviews;
	private Set<Booking> bookings;	
	private Collection<? extends GrantedAuthority> authorities;	

	public static LoggedUserDetails build(User u) {
		List<GrantedAuthority> authorities = u.getRoles().stream().map(
				role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList() );
		return new LoggedUserDetails(u.getId(), u.getUsername(), u.getEmail(), u.getPassword(), u.getName(), u.getSurname(),
				u.getHostSince(), u.getLocation(), u.getNeighbourhood(), u.getAbout(), u.getPictureUrl(), u.getSpokenLanguages(),
				u.getVerifications(), u.getListings(), u.getReviews(), u.getBookings(), authorities);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}	
}