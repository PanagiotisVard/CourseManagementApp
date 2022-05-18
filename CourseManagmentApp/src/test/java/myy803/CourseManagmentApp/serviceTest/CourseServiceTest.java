package myy803.CourseManagmentApp.serviceTest;

import static org.mockito.Mockito.verify;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import myy803.CourseManagmentApp.dao.CourseDAO;
import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Instructor;
import myy803.CourseManagmentApp.service.CourseService;
import myy803.CourseManagmentApp.service.InstructorService;

@SpringBootTest
@TestPropertySource(
		  locations = "classpath:applicationTest.properties")
class CourseServiceTest {
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	InstructorService userService;
	
	@MockBean
	CourseDAO courseDAO;

	@Test
	void testCourseServiceIsNotNull(){
		Assertions.assertNotNull(courseService);
	}
	
	@Test
	void testFindById() {
		Mockito.when(courseDAO.findById(1)).thenReturn(new Course("testCourse", "testCourse desc", 2022, 1));
		Course course = courseService.findById(1);
		Assertions.assertNotNull(course);
		Assertions.assertEquals("testCourse", course.getTitle());
		Assertions.assertEquals("testCourse desc", course.getDescription());
		Assertions.assertEquals(2022, course.getYear());
		Assertions.assertEquals(1, course.getSemester());
	}
	
	@Test
	void testSave() {
		Instructor instructor = userService.findByName("maria1998");
		Assertions.assertEquals("maria1998", instructor.getName());
		Course savedCourse = new Course(11,"title", "test", 0, 0);
		savedCourse.setInstructor(instructor);
		courseService.save("maria1998",savedCourse);
		verify(courseDAO).save(savedCourse);

	}
	@Test
	void testFindByAllByInstructor() {
		Instructor instructor = userService.findByName("maria1998");
		List<Course> courses = new ArrayList<>();
		Course course1 = new Course(1, "title", "desc", 0, 0);
		Course course2=new Course(2, "title2", "desc2", 0, 0);
		
		course1.setInstructor(instructor);course2.setInstructor(instructor);
		courses.add(course1);
		courses.add(course2);
		
		Mockito.when(courseDAO.findAllByInstructor(instructor)).thenReturn(courses);
		Assertions.assertEquals(2, courseService.findByInstructor(instructor).size());
		
	}
	@Test
	void testFindAll() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "title", "desc", 0, 0));
		courses.add(new Course(2, "title2", "desc2", 0, 0));
		Mockito.when(courseDAO.findAll()).thenReturn(courses);
		Assertions.assertEquals(2, courseService.findAll().size());;
	}
	@Test
	void testDeleteById() {
		courseService.deleteById(1);
		verify(courseDAO).deleteById(1);
		
	}

}
