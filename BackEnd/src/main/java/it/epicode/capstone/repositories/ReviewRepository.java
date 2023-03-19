package it.epicode.capstone.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import it.epicode.capstone.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM reviews r INNER JOIN users u ON r.reviewer_id = u.id INNER JOIN listings l ON r.listing_id = l.id WHERE u.username = :u AND l.name = :n")
	Optional<Review> findReviewByListingAndReviewer(@Param("n") String name, @Param("u") String username);
	
	@Query(nativeQuery = true, value = "SELECT * FROM reviews WHERE reviewer_id = :id")
	Optional<Review> findReviewByReviewerId(@Param("id") Long id);
}