package it.epicode.capstone.models;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String username;
	@NotBlank
	private String name;
	@NotBlank
	private String surname;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String password;
	@ManyToMany
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	@Column(name = "host_since")
	private LocalDate hostSince;
	private String location;
	private String neighbourhood;
	@Column(columnDefinition = "TEXT")
	private String about;
	@Column(name = "picture_url")
	private String pictureUrl;
	@ManyToMany
	@JoinTable(name = "user_languages", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "language_id"))
	@Column(name = "spoken_languages")
	private Set<Language> spokenLanguages;
	@ManyToMany
	@JoinTable(name = "user_verifications", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "verification_id"))
	private Set<Verification> verifications;
	@JsonManagedReference
    @OneToMany(mappedBy = "owner")
	private Set<Listing> listings;
	@JsonManagedReference
    @OneToMany(mappedBy = "reviewer")
	private Set<Review> reviews;
	@JsonManagedReference
    @OneToMany(mappedBy = "booker")
	private Set<Booking> bookings;
	
	
	@Override
	public String toString() {
		return "Id: " + String.format("%1$-"+ 5 + "s", this.getId()) + "Nome: " + String.format("%1$-"+ 18 + "s",this.getName()) 
				+ "UserName: " + String.format("%1$-"+ 13 + "s", this.getUsername()) + "Email: " 
				+ String.format("%1$-"+ 25 + "s", this.getEmail());
	}
}
