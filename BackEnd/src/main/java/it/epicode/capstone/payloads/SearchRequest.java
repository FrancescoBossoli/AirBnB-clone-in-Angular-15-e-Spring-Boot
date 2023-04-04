package it.epicode.capstone.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchRequest {

	private String location;
	private String arrival;
	private String departure;
	private int people;
}
