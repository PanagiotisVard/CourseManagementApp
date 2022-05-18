package myy803.CourseManagmentApp.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Student;

@Repository
public interface StudentDAO extends JpaRepository<Student, Integer>{
	
	
	public Student findById(int theId);
	

	
	public void deleteById(int theId);
	
	public List<Student> findByCourse(Course course);

}
