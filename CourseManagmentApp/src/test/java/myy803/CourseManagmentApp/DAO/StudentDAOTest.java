package myy803.CourseManagmentApp.DAO;

import org.junit.jupiter.api.Test;


import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import myy803.CourseManagmentApp.dao.StudentDAO;
import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Instructor;
import myy803.CourseManagmentApp.entity.Student;

@SpringBootTest
@TestPropertySource(
 locations = "classpath:applicationTest.properties")
@Sql(statements="delete from course; delete from student;")
public class StudentDAOTest {
	
	@Autowired
	StudentDAO studentDAO;
	
	private Course course;
	
	@BeforeEach
	public void setUp() {
		course= new Course(1, "testing", "tedffg", 2222, 123);
		Instructor instructor = new Instructor("maria1998","de paizei rolo","de paizei rolo");
		course.setInstructor(instructor);
	}
	
	@Test
	@Sql("insert-course.sql")
	@Sql("insert-student.sql")
	@Sql(statements="delete from course; delete from student;",executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testFindById() {
		
		Student theStudent=studentDAO.findById(1);
		Assertions.assertNotNull(theStudent);
		Assertions.assertEquals("kitsio", theStudent.getName());
		Assertions.assertEquals(1, theStudent.getCourse().getId());
		
	}
	
	@Test
	@Sql(statements = "ALTER TABLE course AUTO_INCREMENT = 1;")
	@Sql(statements = "ALTER TABLE student AUTO_INCREMENT = 1;")
	@Sql("insert-course.sql")
	@Sql(statements="delete from course; delete from student;",executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testSave() {
		
		Student student = new Student(1,course, "kitsio", 12334, 1234, 10, 9, 9.5);
		studentDAO.save(student);
		Student storedStudent= studentDAO.findById(1);
		Assertions.assertNotNull(storedStudent);
		Assertions.assertEquals("kitsio", storedStudent.getName());
		Assertions.assertEquals(1, storedStudent.getCourse().getId());
	}
	
	@Test
	@Sql("insert-course.sql")
	@Sql("insert-student.sql")
	@Sql(statements="delete from course; delete from student;",executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testFindByIdNotExists() {
		
		Student theStudent=studentDAO.findById(0);
		Assertions.assertNull(theStudent);
		
	}
	

	@Test
	@Sql("insert-course.sql")
	@Sql("insert-student.sql")
	@Sql(statements="delete from course; delete from student;",executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testFindAllByInstructor() {
		List<Student> students=studentDAO.findByCourse(course);
		Assertions.assertNotNull(students);
		Assertions.assertEquals(1,students.size());
		
	}
	
	@Test
	@Sql("insert-course.sql")
	@Sql("insert-student.sql")
	@Sql(statements="delete from course; delete from student;",executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testFindAllByInstructorNtExists() {
		Course badCourse=new Course(2, "testing", "tedffg", 2222, 123);
		List<Student> students=studentDAO.findByCourse(badCourse);
		Assertions.assertNotNull(students);
		Assertions.assertEquals(0,students.size());
		
	}
	
	@Test
	@Sql("insert-course.sql")
	@Sql("insert-student.sql")
	@Sql(statements="delete from course; delete from student;",executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testDeleteById() {
		
		studentDAO.deleteById(1);
		Student theStudent=studentDAO.findById(1);
		
		Assertions.assertNull(theStudent);
		
	}
	
	@Test
	@Sql("insert-course.sql")
	@Sql("insert-student.sql")
	@Sql(statements="delete from course; delete from student;",executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testDeleteByIdNotExists() {
		
		Exception exception =  Assertions.assertThrows(EmptyResultDataAccessException.class, 
				()->{studentDAO.deleteById(100);});
		Assertions.assertTrue(exception.getMessage().contains("entity with id 100 exists"));
		
		
	}
	

}