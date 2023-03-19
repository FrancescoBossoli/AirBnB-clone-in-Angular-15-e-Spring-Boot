package it.epicode.capstone.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.epicode.capstone.models.Amenity;
import it.epicode.capstone.models.AmenityType;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
			
	Optional<Amenity> findByName(AmenityType t);
			
}
