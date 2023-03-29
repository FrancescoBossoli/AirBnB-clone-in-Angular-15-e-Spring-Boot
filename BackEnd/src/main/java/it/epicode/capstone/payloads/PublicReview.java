package it.epicode.capstone.payloads;

import java.time.LocalDate;
import it.epicode.capstone.models.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicReview {
	
	private Long id;
	private LocalDate date;	
	private String comment;
	private int score;
	private PublicUser reviewer;

	public static PublicReview build(Review r) {
		PublicUser reviewer = PublicUser.build(r.getReviewer());
		return new PublicReview(r.getId(), r.getDate(), r.getComment(), r.getScore(), reviewer);
	}
}
