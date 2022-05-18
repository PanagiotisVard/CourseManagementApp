package myy803.CourseManagmentApp.service;

import java.util.List;

import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Student;

public interface StudentService {

	
	public Student findById(int theId);
	
	public void save(Student student);
	
	public void deleteById(int theId);
	
	public List<Student> findByCourse(Course course);
	
	public double calculateOverallGrade(Student student);

}
