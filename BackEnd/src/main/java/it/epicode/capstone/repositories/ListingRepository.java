package it.epicode.capstone.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.epicode.capstone.models.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM listings l INNER JOIN users u ON l.owner_id = u.id WHERE l.name = :n AND u.username = :u")
	Optional<Listing> findListingByNameAndOwner(@Param("n") String name, @Param("u") String username);
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings WHERE owner_id = :id")
	Optional<Listing> findListingByOwnerId(@Param("id") Long id);
}