package it.epicode.capstone.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.capstone.models.User;
import it.epicode.capstone.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository uR;
	
	public void save(User u) {
		Optional<User> o = getUserByUsername(u.getUsername());
		if (o.isEmpty()) {
			uR.save(u);
			log.info("The User has been saved in the Database.");			
		}
		else log.info("This User is already present in the Database.");
	}
	
	public void edit(User u) {
		uR.save(u);
	}
	
	public Optional<User> getUserById(Long id) {
		return uR.findById(id);
	}
	
	public Optional<User> getUserByUsername(String u) {
		return uR.findUserByUsername(u);
	}
	
	public Optional<User> getUserByEmail(String e) {
		return uR.findUserByEmail(e);
	}	
	
	public List<User> getAllUsers(){
		return uR.findAll(PageRequest.of(0 , 2000)).getContent();
	}
	
	public Page<User> getAllUsers(Pageable p) {
		return uR.findAll(p);
	}
	
	public void deleteUserById(Long id) {
		uR.deleteById(id);
	}
	
	public void printList(List<User> list) {
		for (User l : list)
			log.info(l.toString());
	}
}
