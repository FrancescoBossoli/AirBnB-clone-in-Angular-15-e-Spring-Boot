package it.epicode.capstone.repositories;

import java.time.LocalDate;
import java.util.List;
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
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings WHERE longitude = :lon AND latitude = :lat")
	Optional<Listing> findListingByLongitudeAndLatitude(@Param("lon") Double lon, @Param("lat") Double lat);
		
	@Query(nativeQuery = true, value = "SELECT * FROM listings l WHERE LOWER(l.location) LIKE LOWER(CONCAT('%', :loc, '%')) AND l.capacity >= :p AND l.id NOT IN ( SELECT b.location_id FROM bookings b WHERE (b.arrival <= :d AND b.departure >= :a))")
	List<Listing> findListingsByLocationAndDatesAndCapacity(@Param("loc") String loc, @Param("a") LocalDate a, @Param("d") LocalDate d, @Param("p") int p);
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings l WHERE LOWER(l.location) LIKE LOWER(CONCAT('%', :loc, '%')) AND l.id NOT IN ( SELECT b.location_id FROM bookings b WHERE (b.arrival <= :d AND b.departure >= :a))")
	List<Listing> findListingsByLocationAndDates(@Param("loc") String loc, @Param("a") LocalDate a, @Param("d") LocalDate d);
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings l WHERE LOWER(l.location) LIKE LOWER(CONCAT('%', :loc, '%')) AND l.id NOT IN ( SELECT b.location_id FROM bookings b WHERE  b.arrival <= :a AND b.departure >= :a)")
	List<Listing> findListingsByLocationAndArrival(@Param("loc") String loc, @Param("a") LocalDate a);
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings l WHERE l.capacity >= :p AND l.id NOT IN ( SELECT b.location_id FROM bookings b WHERE (b.arrival <= :d AND b.departure >= :a))")
	List<Listing> findListingsByDatesAndPeople(@Param("a") LocalDate a, @Param("d") LocalDate d, @Param("p") int p);
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings l WHERE l.id NOT IN ( SELECT b.location_id FROM bookings b WHERE (b.arrival <= :d AND b.departure >= :a))")
	List<Listing> findListingsByDates(@Param("a") LocalDate a, @Param("d") LocalDate d);
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings l WHERE LOWER(l.location) LIKE LOWER(CONCAT('%', :loc, '%')) AND l.capacity >= :p AND l.id NOT IN ( SELECT b.location_id FROM bookings b WHERE b.arrival <= :a AND b.departure >= :a)")
	List<Listing> findListingsByLocationAndArrivalAndCapacity(@Param("loc") String loc, @Param("a") LocalDate a, @Param("p") int p);
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings l WHERE l.capacity >= :p AND l.id NOT IN ( SELECT b.location_id FROM bookings b WHERE b.arrival <= :a AND b.departure >= :a)")
	List<Listing> findListingsByArrivalAndCapacity(@Param("a") LocalDate a, @Param("p") int p);
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings l WHERE LOWER(l.location) LIKE LOWER(CONCAT('%', :loc, '%'))")
	List<Listing> findListingsByLocation(@Param("loc") String loc);
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings l WHERE LOWER(l.location) LIKE LOWER(CONCAT('%', :loc, '%')) AND l.capacity >= :p")
	List<Listing> findListingsByLocationAndCapacity(@Param("loc") String loc, @Param("p") int p);
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings l WHERE l.capacity >= :p")
	List<Listing> findListingsByPeople(@Param("p") int p);
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings l WHERE l.id NOT IN ( SELECT b.location_id FROM bookings b WHERE b.arrival <= :a AND b.departure >= :a)")
	List<Listing> findListingsByArrival(@Param("a") LocalDate a);	
	
	@Query(nativeQuery = true, value = "SELECT * FROM listings l WHERE l.id >= :a AND l.id <= :b")
	List<Listing> findListingsRange(@Param("a") int a, @Param("b") int b);	
	
}