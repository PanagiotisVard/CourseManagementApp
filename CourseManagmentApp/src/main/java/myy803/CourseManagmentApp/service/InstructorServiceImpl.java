package myy803.CourseManagmentApp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import myy803.CourseManagmentApp.dao.InstructorDAO;
import myy803.CourseManagmentApp.entity.Instructor;
import myy803.CourseManagmentApp.entity.UserData;

@Service
public class InstructorServiceImpl implements InstructorService {
	
	@Autowired
	private InstructorDAO userRepository;
	
	
	
	public InstructorServiceImpl() {
		super();
	}
	
	
	@Autowired
	public InstructorServiceImpl(InstructorDAO userRepository) {
		this.userRepository = userRepository;
	}



	@Override
	@Transactional
	public List<Instructor> findAll() {
		return userRepository.findAll();
	}




	@Override
	@Transactional
	public Instructor findByName(String theName) {
		return userRepository.findByName(theName);
	}
	
	@Override
	@Transactional
	public void register(UserData userData) {
		
		Instructor instructor = new Instructor();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(userData.getPassword());
		instructor.setName(userData.getName());
		instructor.setEmail(userData.getEmail());
		instructor.setPassword(encodedPassword);
		userRepository.save(instructor);
		
	}
	

}
