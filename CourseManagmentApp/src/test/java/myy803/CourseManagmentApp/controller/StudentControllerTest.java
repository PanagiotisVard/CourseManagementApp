package myy803.CourseManagmentApp.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Student;
import myy803.CourseManagmentApp.service.CourseService;
import myy803.CourseManagmentApp.service.StudentService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
  locations = "classpath:applicationTest.properties")
@AutoConfigureMockMvc
public class StudentControllerTest {

	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	CourseService courseService;
	@MockBean
	StudentService studentService;
	
	@Autowired
	StudentController studentController;
	
	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
		//System.exit(0);
    }
	
	@Test
	void testStudentControllerIsNotNull() {
		Assertions.assertNotNull(studentController);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testListStudentsReturnsPage() throws Exception {
		
		Course course = new Course(10, "testcourse", "testdescription", 2022, 5);
		ArrayList<Student> students  = new ArrayList<>();
		
		for(int i=0;i<5;i++)
			students.add(new Student(1+i, null, null, 0, 0, 4+i, 4+i, 4+i));
		
		
		
		
		when(courseService.findById(10)).thenReturn(course);
		when(studentService.findByCourse(course)).thenReturn(students);		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("courseId", Integer.toString(10));
		mockMvc.perform(get("/students/students").params(multiValueMap)).
		andExpect(status().isOk()).
		andExpect(view().name("students/students"));
	}


	@WithMockUser(value = "maria1998")
	@Test 
	void testShowFormForAdd() throws Exception {
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("courseId", Integer.toString(10));
		mockMvc.perform(get("/students/showFormForAdd").params(multiValueMap)).
		andExpect(status().isOk()).
		andExpect(view().name("students/student-form"));
		
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testShowFormForUpdate() throws Exception {
		
		Course course = new Course(10, "testcourse", "testdescription", 2022, 5);
		when(studentService.findById(10)).thenReturn(new Student(10,course,"test",5,2004,5,5,5));
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("studentId", Integer.toString(10));
		mockMvc.perform(get("/students/showFormForUpdate").params(multiValueMap)).
		andExpect(status().isOk()).
		andExpect(view().name("students/student-form"));
		
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testShowFormForGrades() throws Exception {
		
		Course course = new Course(10, "testcourse", "testdescription", 2022, 5);
		when(studentService.findById(10)).thenReturn(new Student(10,course,"test",5,2004,5,5,5));
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("studentId", Integer.toString(10));
		mockMvc.perform(get("/students/showFormForGrades").params(multiValueMap)).
		andExpect(status().isOk()).
		andExpect(view().name("students/grades-form"));
		
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testSaveStudentReturnsPage() throws Exception {
		
		Course course = new Course(10, "testcourse", "testdescription", 2022, 5);
		Student student = new Student(10,course,"test",5,2004,5,5,5);
		
		when(courseService.findById(10)).thenReturn(course);
		doNothing().when(studentService).save(student);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(student.getId()));
	    multiValueMap.add("semester", Integer.toString(student.getSemester()));
	    multiValueMap.add("year", Integer.toString(student.getYear()));
	    multiValueMap.add("name", student.getName());
	    
		mockMvc.perform(get("/students/students").param("courseId", Integer.toString(10)));
	    
	    mockMvc.perform(
	    		post("/students/save")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/students/students?courseId="+course.getId()));
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testSaveStudentReturnsPageNullName() throws Exception {
		
		Course course = new Course(10, "testcourse", "testdescription", 2022, 5);
		Student student = new Student(10,course,"",5,2004,5,5,5);
		doNothing().when(studentService).save(student);
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(student.getId()));
	    multiValueMap.add("semester", Integer.toString(student.getSemester()));
	    multiValueMap.add("year", Integer.toString(student.getYear()));
	    multiValueMap.add("name", student.getName());
	    
	    mockMvc.perform(
	    		post("/students/save")
			    .params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("students/student-form")).
				andExpect(model().attribute("errorMessage", "Empty name"));
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testSaveStudentsReturnsPageWrongSemester() throws Exception {
		
		Course course = new Course(10, "testcourse", "testdescription", 2022, 5);
		Student student = new Student(10,course,"test",0,2004,5,5,5);
		doNothing().when(studentService).save(student);
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(student.getId()));
	    multiValueMap.add("semester", Integer.toString(student.getSemester()));
	    multiValueMap.add("year", Integer.toString(student.getYear()));
	    multiValueMap.add("name", student.getName());
	    
	    mockMvc.perform(
	    		post("/students/save")
			    .params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("students/student-form")).
				andExpect(model().attribute("errorMessage", "Invalid semester"));
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testSaveGradesReturnsPage() throws Exception{
		
		Course course = new Course(10, "testcourse", "testdescription", 2022, 5);
		Student student = new Student(10,course,"test",5,2004,5,5,5);
		student.setProjectWeight(0.5);
		student.setExamsWeight(0.5);
		
		when(courseService.findById(10)).thenReturn(course);
		when(studentService.calculateOverallGrade(student)).thenReturn(5.0);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(student.getId()));
	    multiValueMap.add("semester", Integer.toString(student.getSemester()));
	    multiValueMap.add("year", Integer.toString(student.getYear()));
	    multiValueMap.add("name", student.getName());
	    multiValueMap.add("projectGrade",Double.toString(student.getProjectGrade()));
	    multiValueMap.add("examsGrade",Double.toString(student.getExamsGrade()));
	    multiValueMap.add("projectWeight",Double.toString(student.getProjectWeight()));
	    multiValueMap.add("examsWeight",Double.toString(student.getExamsWeight()));
	    
		mockMvc.perform(get("/students/students").param("courseId", Integer.toString(10)));
	    
	    mockMvc.perform(
	    		post("/students/save-grades")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/students/students?courseId="+course.getId()));
		
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testSaveGradesReturnsPageInvalidWeight() throws Exception{
		
		Course course = new Course(10, "testcourse", "testdescription", 2022, 5);
		Student student = new Student(10,course,"test",5,2004,5,5,5);
		student.setProjectWeight(0.5);
		student.setExamsWeight(1.5);
		
		when(courseService.findById(10)).thenReturn(course);
		when(studentService.calculateOverallGrade(student)).thenReturn(5.0);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(student.getId()));
	    multiValueMap.add("semester", Integer.toString(student.getSemester()));
	    multiValueMap.add("year", Integer.toString(student.getYear()));
	    multiValueMap.add("name", student.getName());
	    multiValueMap.add("projectGrade",Double.toString(student.getProjectGrade()));
	    multiValueMap.add("examsGrade",Double.toString(student.getExamsGrade()));
	    multiValueMap.add("projectWeight",Double.toString(student.getProjectWeight()));
	    multiValueMap.add("examsWeight",Double.toString(student.getExamsWeight()));
	    
		mockMvc.perform(get("/students/students").param("courseId", Integer.toString(10)));
	    
	    mockMvc.perform(
	    		post("/students/save-grades")
			    .params(multiValueMap))
	    		.andExpect(status().isOk())
	    		.andExpect(view().name("students/grades-form")).
	    		andExpect(model().attribute("errorMessage","Weight must be between in 0-1!"));
		
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testSaveGradesReturnsPageInvalidGrade() throws Exception{
		
		Course course = new Course(10, "testcourse", "testdescription", 2022, 5);
		Student student = new Student(10,course,"test",5,2004,11,5,5);
		student.setProjectWeight(0.5);
		student.setExamsWeight(0.5);
		
		when(courseService.findById(10)).thenReturn(course);
		when(studentService.calculateOverallGrade(student)).thenReturn(5.0);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(student.getId()));
	    multiValueMap.add("semester", Integer.toString(student.getSemester()));
	    multiValueMap.add("year", Integer.toString(student.getYear()));
	    multiValueMap.add("name", student.getName());
	    multiValueMap.add("projectGrade",Double.toString(student.getProjectGrade()));
	    multiValueMap.add("examsGrade",Double.toString(student.getExamsGrade()));
	    multiValueMap.add("projectWeight",Double.toString(student.getProjectWeight()));
	    multiValueMap.add("examsWeight",Double.toString(student.getExamsWeight()));
	    
		mockMvc.perform(get("/students/students").param("courseId", Integer.toString(10)));
	    
	    mockMvc.perform(
	    		post("/students/save-grades")
			    .params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("students/grades-form")).
				andExpect(model().attribute("errorMessage","Grade must be between in 0-10!"));
		
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testDelete() throws Exception {

		Course course = new Course(10, "testcourse", "testdescription", 2022, 5);
			
		when(courseService.findById(10)).thenReturn(course);
		doNothing().when(studentService).deleteById(1);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("studentId", Integer.toString(1));
		mockMvc.perform(get("/students/students").param("courseId", Integer.toString(10)));
	    mockMvc.perform(
	    		get("/students/delete")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/students/students?courseId=10"));
		
	}
	
}
