package myy803.CourseManagmentApp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Instructor;

@Repository
public interface CourseDAO extends JpaRepository<Course, Integer>{
	
	public Course findById(int id);

	public List<Course> findAllByInstructor(Instructor instructor);
	
	public void deleteById(int id);

}
