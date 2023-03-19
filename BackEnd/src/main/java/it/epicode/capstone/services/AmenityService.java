package it.epicode.capstone.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.capstone.models.Amenity;
import it.epicode.capstone.models.AmenityType;
import it.epicode.capstone.repositories.AmenityRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AmenityService {

	@Autowired
	private AmenityRepository aR;
	
	public void save(Amenity v) {
		Optional<Amenity> o = getAmenityByName(v.getName());
		if (o.isEmpty()) {
			aR.save(v);
			log.info("The Amenity has been saved in the Database.");			
		}
		else log.info("This Amenity is already present in the Database.");
	}
	
	public Optional<Amenity> getAmenityByName(AmenityType n) {
		return aR.findByName(n);
	}
	
	public Optional<Amenity> getAmenityById(Long id) {
		return aR.findById(id);
	}
		
	public List<Amenity> getAllAmenities(){
		return aR.findAll();
	}
	
	public Page<Amenity> getAllAmenities(Pageable p) {
		return aR.findAll(p);
	}
	
	public void deleteAmenityById(Long id) {
		aR.deleteById(id);
	}
	
	public void printList(List<Amenity> list) {
		for (Amenity l : list)
			log.info(l.toString());
	}

}
