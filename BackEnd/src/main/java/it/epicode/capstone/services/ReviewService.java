package it.epicode.capstone.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.capstone.models.Review;
import it.epicode.capstone.repositories.ReviewRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewService {

	@Autowired
	private ReviewRepository rR;
	
	public void save(Review r) {
		Optional<Review> o = getReviewByListingAndReviewer(r.getListing().getName(), r.getReviewer().getUsername());
		if (o.isEmpty()) {
			rR.save(r);
			log.info("The Review has been saved in the Database.");			
		}
		else log.info("This Review is already present in the Database.");
	}
	
	public Optional<Review> getReviewByReviewerId(Long id) {
		return rR.findReviewByReviewerId(id);
	}
	
	public Optional<Review> getReviewById(Long id) {
		return rR.findById(id);
	}
	
	public Optional<Review> getReviewByListingAndReviewer(String n, String u) {
		return rR.findReviewByListingAndReviewer(n, u);
	}
		
	public List<Review> getAllReviews(){
		return rR.findAll();
	}
	
	public Page<Review> getAllReviews(Pageable p) {
		return rR.findAll(p);
	}
	
	public void deleteReviewById(Long id) {
		rR.deleteById(id);
	}
	
	public void printList(List<Review> list) {
		for (Review l : list) log.info(l.toString());
	}

}

