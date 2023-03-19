package it.epicode.capstone.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.capstone.models.Listing;
import it.epicode.capstone.models.User;
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

	public List<Listing> getAllListings() {
		return lR.findAll();
	}

	public Page<Listing> getAllListings(Pageable p) {
		return lR.findAll(p);
	}

	public void deleteListingById(Long id) {
		lR.deleteById(id);
	}

	public void printList(List<Listing> list) {
		for (Listing l : list)
			log.info(l.toString());
	}
}
