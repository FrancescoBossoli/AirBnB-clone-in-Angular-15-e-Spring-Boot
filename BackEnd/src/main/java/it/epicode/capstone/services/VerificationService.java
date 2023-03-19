package it.epicode.capstone.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.capstone.models.Verification;
import it.epicode.capstone.models.VerificationType;
import it.epicode.capstone.repositories.VerificationRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VerificationService {
	
	@Autowired
	private VerificationRepository vR;
	
	public void save(Verification v) {
		Optional<Verification> o = getVerificationByName(v.getName());
		if (o.isEmpty()) {
			vR.save(v);
			log.info("The Verification has been saved in the Database.");			
		}
		else log.info("This Verification is already present in the Database.");
	}
	
	public Optional<Verification> getVerificationByName(VerificationType n) {
		return vR.findByName(n);
	}
	
	public Optional<Verification> getVerificationById(Long id) {
		return vR.findById(id);
	}
		
	public List<Verification> getAllVerifications(){
		return vR.findAll();
	}
	
	public Page<Verification> getAllVerifications(Pageable p) {
		return vR.findAll(p);
	}
	
	public void deleteVerificationById(Long id) {
		vR.deleteById(id);
	}
	
	public void printList(List<Verification> list) {
		for (Verification l : list)
			log.info(l.toString());
	}

}

