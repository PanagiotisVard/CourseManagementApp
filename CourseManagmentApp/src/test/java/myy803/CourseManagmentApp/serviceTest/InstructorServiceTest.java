package myy803.CourseManagmentApp.serviceTest;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import myy803.CourseManagmentApp.dao.InstructorDAO;
import myy803.CourseManagmentApp.entity.Instructor;
import myy803.CourseManagmentApp.entity.UserData;
import myy803.CourseManagmentApp.service.InstructorService;

@SpringBootTest
@TestPropertySource(
		  locations = "classpath:applicationTest.properties")
class InstructorServiceTest {


	
	@Autowired
	InstructorService instructorService;
	
	@MockBean
	InstructorDAO instructorDAO;

	@Test
	void testStudentServiceIsNotNull(){
		Assertions.assertNotNull(instructorService);
	}
	
	@Test
	void testFindAll() {
		List<Instructor> instructors = new ArrayList<Instructor>();
		
		instructors.add(new Instructor("instructor1","password1","instructor1@mail.edu"));
		instructors.add(new Instructor("instructor2","password2","instructor2@mail.edu"));
		instructors.add(new Instructor("instructor3","password3","instructor3@mail.edu"));
		Mockito.when(instructorDAO.findAll()).thenReturn(instructors);
		Assertions.assertEquals(3,instructorService.findAll().size());
	}
	
	@Test
	void testFindByName() {
		Mockito.when(instructorDAO.findByName("instructor1")).thenReturn(new Instructor("instructor1","password1","instructor1@mail.edu"));
		Instructor instructor = instructorService.findByName("instructor1");
		Assertions.assertNotNull(instructor);
		Assertions.assertEquals("instructor1", instructor.getName());
		Assertions.assertEquals("instructor1@mail.edu", instructor.getEmail());
		Assertions.assertEquals("password1", instructor.getPassword());
	}
	
	@Test
	void testRegister() {
		UserData userData=new UserData("instructor1","instructor1@mail.edu","password1");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		instructorService.register(userData);
		ArgumentCaptor<Instructor> argument = ArgumentCaptor.forClass(Instructor.class);
		
		verify(instructorDAO).save(argument.capture());
		Assertions.assertEquals("instructor1", argument.getValue().getName());

		Assertions.assertEquals("instructor1@mail.edu", argument.getValue().getEmail());
		Assertions.assertTrue(encoder.matches("password1", argument.getValue().getPassword()));
		
		
	}
	
}
