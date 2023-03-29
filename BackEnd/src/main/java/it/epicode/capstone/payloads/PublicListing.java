package it.epicode.capstone.payloads;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import it.epicode.capstone.models.Amenity;
import it.epicode.capstone.models.Bathroom;
import it.epicode.capstone.models.Booking;
import it.epicode.capstone.models.Listing;
import it.epicode.capstone.models.Property;
import it.epicode.capstone.models.Review;
import it.epicode.capstone.models.Room;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicListing {
	
	private Long id;
	private String name;
	private String description;
	private String neighborhoodOverview;
	private List<String> pictures;	
	private double latitude;
	private double longitude;
	private Property propertyType;
	private Room roomType;
	private int capacity;
	private Bathroom bathrooms;
	private int bedrooms;
	private int beds;
	private double price;
	private int minimumStay;
	private int maximumStay;
	private boolean instantBookable;
	private Set<Amenity> amenities;
	private Set<PublicReview> reviews;
	private Set<Booking> bookings;
	private PublicUser owner;

	public static PublicListing build(Listing l) {
		PublicUser owner = PublicUser.build(l.getOwner());
		Set<PublicReview> reviews = new HashSet<PublicReview>();
		for (Review review : l.getReviews()) {
			PublicReview r = PublicReview.build(review);
			reviews.add(r);
		}
		return new PublicListing(l.getId(), l.getName(), l.getDescription(), l.getNeighborhoodOverview(), l.getPictures(), l.getLatitude(), 
				l.getLongitude(), l.getPropertyType(), l.getRoomType(), l.getCapacity(), l.getBathrooms(), l.getBedrooms(), l.getBeds(),
				l.getPrice(), l.getMinimumStay(), l.getMaximumStay(), l.isInstantBookable(), l.getAmenities(), reviews, l.getBookings(), owner);
	}
}
