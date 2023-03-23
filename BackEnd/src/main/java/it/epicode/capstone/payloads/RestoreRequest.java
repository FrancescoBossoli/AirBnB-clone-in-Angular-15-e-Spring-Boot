package it.epicode.capstone.payloads;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestoreRequest {
	
	private Long id;
	@NotBlank
	private String username;
	@NotBlank
	private String token;
}
