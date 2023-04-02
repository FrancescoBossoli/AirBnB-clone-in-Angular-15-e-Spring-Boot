package it.epicode.capstone.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.capstone.models.Booking;
import it.epicode.capstone.repositories.BookingRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookingService {

	@Autowired
	private BookingRepository bR;
	
	public void save(Booking b) {
		List<Booking> o = getBookingByLocationAndDateRange(b.getLocation().getId(), b.getArrival(), b.getDeparture());
		if (o.isEmpty()) {
			bR.save(b);
			log.info("The Booking has been saved in the Database.");			
		}
		else log.info("This Booking could not be saved due to a previous booking already in place.");
	}
	
	public List<Booking> getBookingByLocationAndDateRange(Long id, LocalDate arr, LocalDate dep) {
		return bR.findBookingByLocationAndDateRange(id, arr, dep);
	}
	
	public List<Booking> getBookingByBookerId(Long id) {
		return bR.findBookingByBookerId(id);
	}
	
	public List<Booking> getBookingByListingId(Long id) {
		return bR.findBookingByListingId(id);
	}
	
	public Optional<Booking> getBookingById(Long id) {
		return bR.findById(id);
	}
		
	public List<Booking> getAllBookings(){
		return bR.findAll();
	}
	
	public Page<Booking> getAllBookings(Pageable p) {
		return bR.findAll(p);
	}
	
	public void deleteBookingById(Long id) {
		bR.deleteById(id);
	}
	
	public void printList(List<Booking> list) {
		for (Booking l : list) log.info(l.toString());
	}
	
}
