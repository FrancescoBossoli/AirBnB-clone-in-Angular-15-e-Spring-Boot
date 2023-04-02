package it.epicode.capstone.payloads;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {

	@NotBlank
	private String arrival;
	@NotBlank
	private String departure;	
	private double cost;	
	private Long locationId;	
	private Long userId;
}
