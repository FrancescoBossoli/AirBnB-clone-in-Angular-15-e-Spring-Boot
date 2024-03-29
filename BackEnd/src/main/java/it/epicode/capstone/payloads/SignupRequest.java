package it.epicode.capstone.payloads;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

	@NotBlank
	private String username;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	@NotBlank
	private String surname;
	private Set<String> roles;
	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
}
