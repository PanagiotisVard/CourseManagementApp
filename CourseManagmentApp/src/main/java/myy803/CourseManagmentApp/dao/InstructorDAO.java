package myy803.CourseManagmentApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.CourseManagmentApp.entity.Instructor;

@Repository
public interface InstructorDAO extends JpaRepository<Instructor, Integer>{
	
	public Instructor findByName(String name);

}
