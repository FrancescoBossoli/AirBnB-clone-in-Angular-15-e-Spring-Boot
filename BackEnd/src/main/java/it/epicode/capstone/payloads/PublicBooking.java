package it.epicode.capstone.payloads;

import java.time.LocalDate;
import it.epicode.capstone.models.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicBooking {

	private Long id;
	private LocalDate arrival;
	private LocalDate departure;
	private double cost;	
	private PublicListing location;	
	private PublicUser booker;
	
	public static PublicBooking build(Booking b) {
		PublicUser booker = PublicUser.build(b.getBooker());
		PublicListing location = PublicListing.build(b.getLocation());
		
		return new PublicBooking(b.getId(), b.getArrival(), b.getDeparture(), b.getCost(), location, booker);
	}
}
