package myy803.CourseManagmentApp.DAO;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import myy803.CourseManagmentApp.dao.CourseDAO;
import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Instructor;

@SpringBootTest
@TestPropertySource(
 locations = "classpath:applicationTest.properties")
public class CourseDAOTest {
	
	
	@Autowired
	CourseDAO courseDAO;
	
	
	
	
	private Instructor instructor;
	
	@BeforeEach
	public void setUp() {
		instructor = new Instructor();
		instructor.setName("maria1998");
		instructor.setEmail("den paizei rolo");
		instructor.setPassword("den paizei rolo");
	}
	
	@Test
	void testCourseDAONotNull() {
		Assertions.assertNotNull(courseDAO);
		Assertions.assertNotNull(instructor);
	}
	
	@Test
	@Sql(statements = "DELETE FROM course;", executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@Sql(statements = "ALTER TABLE course AUTO_INCREMENT = 1;")
	void testSave() {
		Course course = new Course("testTitle", "test", 0, 0);
		course.setInstructor(instructor);
		courseDAO.save(course);
		Assertions.assertNotNull(courseDAO.findById(1));
		Assertions.assertEquals("testTitle", courseDAO.findById(1).getTitle());	
	}
	
	@Test
	@Sql("insert-data.sql")
	@Sql(statements = "DELETE FROM course;", executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testFindById() {

		Course storedCourse = courseDAO.findById(1);
		Assertions.assertNotNull(storedCourse);
		Assertions.assertEquals("test", storedCourse.getTitle());
	}
	
	@Test
	@Sql("insert-data.sql")
	@Sql(statements = "DELETE FROM course;", executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testFindByUserId() {
		List<Course> courses = courseDAO.findAllByInstructor(instructor);
		Assertions.assertEquals(1, courses.size());
	}

	
	@Test
	@Sql("insert-data.sql")
	@Sql(statements = "DELETE FROM course;", executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testDeleteById() {
		Assertions.assertEquals("test", courseDAO.findById(1).getTitle());
		courseDAO.deleteById(1);
		Assertions.assertNull(courseDAO.findById(1));
		Assertions.assertEquals(0, courseDAO.findAll().size());
		
	}
	
	@Test
	@Sql("insert-data.sql")
	@Sql(statements = "DELETE FROM course;", executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testFindByIdNotExists() {
		Course storedCourse = courseDAO.findById(23);
		Assertions.assertNull(storedCourse);
		
	}
	
	
	@Test
	@Sql("insert-data.sql")
	@Sql(statements = "DELETE FROM course;", executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testFindByInstructorNotExists() {
		instructor.setName("kitsio");
		List<Course> courses = courseDAO.findAllByInstructor(instructor);
		Assertions.assertEquals(0,courses.size());
	}
	
	
	
	@Test
	@Sql("insert-data.sql")
	@Sql(statements = "DELETE FROM course;", executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testDeleteByIdNotExists() {
		Exception exception =  Assertions.assertThrows(EmptyResultDataAccessException.class, 
				()->{courseDAO.deleteById(100);});
		Assertions.assertTrue(exception.getMessage().contains("entity with id 100 exists"));
		
	
		
	}
	


}
