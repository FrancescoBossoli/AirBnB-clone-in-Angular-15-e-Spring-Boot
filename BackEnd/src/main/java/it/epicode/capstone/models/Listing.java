package it.epicode.capstone.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "listings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Listing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	@Column(columnDefinition = "TEXT", name = "neighborhood_overview")
	private String neighborhoodOverview;
	@ElementCollection
	private List<String> pictures;
	@ManyToOne
	@JsonBackReference
	private User owner;
	private double latitude;
	private double longitude;
	@Enumerated(EnumType.STRING)
	private Property propertyType;
	@Enumerated(EnumType.STRING)
	private Room roomType;
	private int capacity;
	@Enumerated(EnumType.STRING)
	private Bathroom bathrooms;
	private int bedrooms;
	private int beds;
	private double price;
	@Column(name = "minimum_stay")
	private int minimumStay;
	@Column(name = "maximum_stay")
	private int maximumStay;
	@Column(name = "instant_bookable")
	private boolean instantBookable;
	@ManyToMany
	@JoinTable(name = "listing_amenities", joinColumns = @JoinColumn(name = "listing_id"), inverseJoinColumns = @JoinColumn(name = "amenity_id"))
	private Set<Amenity> amenities;	
	@OneToMany(mappedBy = "listing")
	@JsonManagedReference
	private Set<Review> reviews;
	@OneToMany(mappedBy = "location")
	@JsonManagedReference
	private Set<Booking> bookings;
	
	
	
	
}
