package it.epicode.capstone.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.epicode.capstone.models.Role;
import it.epicode.capstone.models.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(RoleType r);
}