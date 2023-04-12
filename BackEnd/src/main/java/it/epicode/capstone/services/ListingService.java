package it.epicode.capstone.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.capstone.models.Listing;
import it.epicode.capstone.payloads.Place;
import it.epicode.capstone.repositories.ListingRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ListingService {

	@Autowired
	private ListingRepository lR;

	public void save(Listing l) {
		Optional<Listing> o = getListingByNameAndOwner(l.getName(), l.getOwner().getUsername());
		if (o.isEmpty()) {
			lR.save(l);
			log.info("The Listing has been saved in the Database.");			
		}
		else log.info("This Listing is already present in the Database.");
	}

	public Optional<Listing> getListingById(Long id) {
		return lR.findById(id);
	}

	public Optional<Listing> getListingByOwnerId(Long id) {
		return lR.findListingByOwnerId(id);
	}
	
	public Optional<Listing> getListingByNameAndOwner(String n, String u) {
		return lR.findListingByNameAndOwner(n, u);
	}
	
	public Optional<Listing> getListingByLongitudeAndLatitude(Double lon, Double lat) {
		return lR.findListingByLongitudeAndLatitude(lon, lat);
	}

	public List<Listing> getAllListings() {
		return lR.findAll();
	}

	public Page<Listing> getAllListings(Pageable p) {
		return lR.findAll(p);
	}
	
	public List<Listing> getListingsRange(int a, int b) {
		return lR.findListingsRange(a,b);
	}
	
	public Set<Place> getLocationsFromInput(String loc) {
		List<Listing> list = lR.findListingsByLocation(loc);
		Set<Place> set = new HashSet<Place>();
		for (Listing l : list) {
			Place p = new Place();
			p.setLocation(l.getLocation());
			set.add(p);
		}
		return set;
	}
	
	public List<Listing> getListingsByLocationAndDatesAndCapacity(String loc, LocalDate a, LocalDate d, int p) {
		return lR.findListingsByLocationAndDatesAndCapacity(loc, a, d, p);
	}
	
	public List<Listing> getListingsByLocationAndDates(String loc, LocalDate a, LocalDate d) {
		return lR.findListingsByLocationAndDates(loc, a, d);
	}
	
	public List<Listing> getListingsByDatesAndPeople(LocalDate a, LocalDate d, int p) {
		return lR.findListingsByDatesAndPeople(a, d, p);
	}
	
	public List<Listing> getListingsByDates(LocalDate a, LocalDate d) {
		return lR.findListingsByDates(a, d);
	}
	
	public List<Listing> getListingsByLocationAndArrivalAndCapacity(String loc, LocalDate a, int p) {
		return lR.findListingsByLocationAndArrivalAndCapacity(loc, a, p);
	}
	
	public List<Listing> getListingsByLocationAndArrival(String loc, LocalDate a) {
		return lR.findListingsByLocationAndArrival(loc, a);
	}
	
	public List<Listing> getListingsByArrivalAndCapacity(LocalDate a, int p) {
		return lR.findListingsByArrivalAndCapacity(a, p);
	}
	
	public List<Listing> getListingsByArrival(LocalDate a) {
		return lR.findListingsByArrival(a);
	}
	
	public List<Listing> getListingsByLocationAndCapacity(String loc, int p) {
		return lR.findListingsByLocationAndCapacity(loc, p);
	}
	
	public List<Listing> getListingsByLocation(String loc) {
		return lR.findListingsByLocation(loc);
	}
	
	public List<Listing> getListingsByPeople(int p) {
		return lR.findListingsByPeople(p);
	}	

	public void deleteListingById(Long id) {
		lR.deleteById(id);
	}
	
	public void edit(Listing l) {
		lR.save(l);
	}

	public void printList(List<Listing> list) {
		for (Listing l : list)
			log.info(l.toString());
	}
}
