package myy803.CourseManagmentApp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.CourseManagmentApp.dao.CourseDAO;
import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Instructor;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseDAO courseRepository;
	
	@Autowired
	private InstructorService userService;

	@Override
	@Transactional
	public List<Course> findAll() {
		return courseRepository.findAll();
	}

	@Override
	@Transactional
	public Course findById(int theId) {
		return courseRepository.findById(theId);
	}

	@Override
	@Transactional
	public void save(String instructorName, Course theCourse) {
		Instructor instructor = userService.findByName(instructorName);
		theCourse.setInstructor(instructor);
	
		courseRepository.save(theCourse);
		
		
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		courseRepository.deleteById(theId);
		
	}
	
	@Override
	@Transactional
	public List<Course> findByInstructor(Instructor instructor) {
		return courseRepository.findAllByInstructor(instructor);
		
	}
	
	
		

}
