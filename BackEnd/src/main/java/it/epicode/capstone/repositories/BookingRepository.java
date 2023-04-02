package it.epicode.capstone.repositories;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.epicode.capstone.models.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM bookings WHERE location_id = :id")
	List<Booking> findBookingByListingId(@Param("id") Long id);
	
	@Query(nativeQuery = true, value = "SELECT * FROM bookings WHERE booker_id = :id")
	List<Booking> findBookingByBookerId(@Param("id") Long id);
	
	@Query(nativeQuery = true, value = "SELECT * FROM bookings WHERE location_id = :id AND ((:arr <= departure AND departure <= :dep) OR (:arr <= arrival AND arrival <= :dep))")
	List<Booking> findBookingByLocationAndDateRange(@Param("id") Long id, @Param("arr") LocalDate arr, @Param("dep") LocalDate dep);
}
