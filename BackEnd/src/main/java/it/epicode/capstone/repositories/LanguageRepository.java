package it.epicode.capstone.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.epicode.capstone.models.Language;
import it.epicode.capstone.models.LanguageSpoken;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

	Optional<Language> findByName(LanguageSpoken t);

}