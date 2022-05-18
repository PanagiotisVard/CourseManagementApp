package myy803.CourseManagmentApp.serviceTest;


import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.mock.mockito.MockBean;



import myy803.CourseManagmentApp.dao.StudentDAO;
import myy803.CourseManagmentApp.entity.Course;

import myy803.CourseManagmentApp.entity.Student;
import myy803.CourseManagmentApp.service.CourseService;
import myy803.CourseManagmentApp.service.StudentService;


@SpringBootTest
@TestPropertySource(
		  locations = "classpath:applicationTest.properties")
class StudentServiceTest {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	CourseService courseService;
	
	@MockBean
	StudentDAO studentDAO;

	@Test
	void testStudentServiceIsNotNull(){
		Assertions.assertNotNull(studentService);
		Assertions.assertNotNull(courseService);
	}
	
	@Test
	void testFindById() {
		Course someCourse = new Course("testCourse", "testCourse desc", 2022, 1);
		Mockito.when(studentDAO.findById(1)).thenReturn(new Student(1,someCourse,"kitsio",6, 2022, 6,6,6));
		Student student=studentService.findById(1);
		Assertions.assertNotNull(student);
		Assertions.assertEquals("kitsio", student.getName());
		Assertions.assertEquals(6, student.getSemester());
		Assertions.assertEquals(2022, student.getYear());
		Assertions.assertEquals(6, student.getProjectGrade());
		Assertions.assertEquals(6, student.getExamsGrade());
		Assertions.assertEquals(6, student.getOverallGrade());
	}
	
	@Test
	void testSave() {
		Course course = new Course("testCourse", "testCourse desc", 2022, 1);
		Student savedStudent = new Student(1,course,"kitsio",6, 2022, 6,6,6);
		studentService.save(savedStudent);
		verify(studentDAO).save(savedStudent);
		

	}
	@Test
	void testFindByCourse() {
		Course course = new Course(1,"testCourse", "testCourse desc", 2022, 1);
		List<Student> students = new ArrayList<Student>();
		students.add(new Student(1,course,"kitsio",6, 2022, 6,6,6));
		students.add(new Student(2,course,"kitsio1",6, 2022, 6,7,7));
		students.add(new Student(3,course,"kitsio2",6, 2022, 7,7,7));
		Mockito.when(studentDAO.findByCourse(course)).thenReturn(students);
		Assertions.assertEquals(3, studentService.findByCourse(course).size());
		
	}

	@Test
	void testDeleteById() {
		studentService.deleteById(1);
		verify(studentDAO).deleteById(1);
		
	}
	
	@Test
	void testCalculateOverallGrade() {
		Course course = new Course("testCourse", "testCourse desc", 2022, 1);
		Student student = new Student(1,course,"kitsio",6, 2022, 6,6,6);
		student.setExamsWeight(0.5);
		student.setProjectWeight(0.5);
		Double overallGrade = studentService.calculateOverallGrade(student);
		Assertions.assertEquals(6, overallGrade);
	}

	

}
