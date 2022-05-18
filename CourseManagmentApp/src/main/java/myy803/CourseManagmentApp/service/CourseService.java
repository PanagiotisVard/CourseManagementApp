package myy803.CourseManagmentApp.service;

import java.util.List;

import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Instructor;



public interface CourseService {
	public List<Course> findAll();
	
	public Course findById(int theId);
	
	public void save(String instructorName, Course theCourse);
	
	public void deleteById(int theId);

	public List<Course> findByInstructor(Instructor instructor);
}
