package it.epicode.capstone.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.capstone.models.Language;
import it.epicode.capstone.models.LanguageSpoken;
import it.epicode.capstone.repositories.LanguageRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LanguageService {

	@Autowired
	private LanguageRepository lR;
	
	public void save(Language v) {
		Optional<Language> o = getLanguageByName(v.getName());
		if (o.isEmpty()) {
			lR.save(v);
			log.info("The Language has been saved in the Database.");			
		}
		else log.info("This Language is already present in the Database.");
	}
	
	public Optional<Language> getLanguageByName(LanguageSpoken n) {
		return lR.findByName(n);
	}
	
	public Optional<Language> getLanguageById(Long id) {
		return lR.findById(id);
	}
		
	public List<Language> getAllLanguages(){
		return lR.findAll();
	}
	
	public Page<Language> getAllLanguages(Pageable p) {
		return lR.findAll(p);
	}
	
	public void deleteLanguageById(Long id) {
		lR.deleteById(id);
	}
	
	public void printList(List<Language> list) {
		for (Language l : list)
			log.info(l.toString());
	}

}
