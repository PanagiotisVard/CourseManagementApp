package myy803.CourseManagmentApp.service;

import java.util.List;

import myy803.CourseManagmentApp.entity.Instructor;
import myy803.CourseManagmentApp.entity.UserData;

public interface InstructorService {
	
	public List<Instructor> findAll();
	
	public Instructor findByName(String theName);
	
	public void register(UserData userData);

}
