package it.epicode.capstone.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.epicode.capstone.models.Verification;
import it.epicode.capstone.models.VerificationType;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {
		
		Optional<Verification> findByName(VerificationType r);
		
}
