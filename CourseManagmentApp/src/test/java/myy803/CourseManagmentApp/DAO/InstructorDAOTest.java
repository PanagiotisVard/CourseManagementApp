package myy803.CourseManagmentApp.DAO;



import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import myy803.CourseManagmentApp.dao.InstructorDAO;

import myy803.CourseManagmentApp.entity.Instructor;

@SpringBootTest
@TestPropertySource(
 locations = "classpath:applicationTest.properties")
class InstructorDAOTest {
	
	@Autowired
	InstructorDAO userDAO;

	
	
	@Test
	void testFindByName() {
		Instructor storedInstructor = userDAO.findByName("maria1998");
		Assertions.assertNotNull(storedInstructor);
		Assertions.assertEquals("maria1998", storedInstructor.getEmail());
	}
	
	@Test
	@Sql(statements="delete from instructor where name='testName';",executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testSave() {
		Instructor instructor = new Instructor("testName","testPassword","testEmail");
		userDAO.save(instructor);
		
		Instructor storedInstructor = userDAO.findByName("testName");
		Assertions.assertNotNull(storedInstructor);
		Assertions.assertEquals("testPassword", storedInstructor.getPassword());
		Assertions.assertEquals("testEmail", storedInstructor.getEmail());
	}
	
	@Test
	void testFindByNameNotExists() {
		Instructor storedInstructor = userDAO.findByName("testName");
		Assertions.assertNull(storedInstructor);
		
	}
	

}
