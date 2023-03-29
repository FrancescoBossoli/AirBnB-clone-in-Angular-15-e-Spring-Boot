package it.epicode.capstone.payloads;

import java.time.LocalDate;
import java.util.Set;
import it.epicode.capstone.models.Language;
import it.epicode.capstone.models.Role;
import it.epicode.capstone.models.User;
import it.epicode.capstone.models.Verification;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicUser {

	private Long id;
	private String name;
	private LocalDate hostSince;
	private String location;
	private String neighbourhood;
	private String about;
	private String pictureUrl;
	private Set<Language> spokenLanguages;
	private Set<Verification> verifications;
	private boolean isSuperHost;
	
	public static PublicUser build(User u) {
		boolean s = false;
		for (Role role : u.getRoles()) if (role.getName().toString() == "SuperHost") s = true;
		return new PublicUser(u.getId(), u.getName(), u.getHostSince(), u.getLocation(), u.getNeighbourhood(), u.getAbout(), 
							  u.getPictureUrl(), u.getSpokenLanguages(), u.getVerifications(), s);
	}
}
