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
import myy803.CourseManagmentApp.entity.Instructor;
import myy803.CourseManagmentApp.entity.Student;
import myy803.CourseManagmentApp.service.CourseService;
import myy803.CourseManagmentApp.service.StudentService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
  locations = "classpath:applicationTest.properties")
@AutoConfigureMockMvc
public class CourseControllerTest {

	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	CourseService courseService;
	@MockBean
	StudentService studentService;
	
	@Autowired
	CourseController courseController;
	
	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
		//System.exit(0);
    }
	
	@Test
	void testEmployeeControllerIsNotNull() {
		Assertions.assertNotNull(courseController);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testListCoursesReturnsPage() throws Exception {
		mockMvc.perform(get("/courses/course")).
		andExpect(status().isOk()).
		andExpect(view().name("courses/course"));
	}

	@WithMockUser(value = "maria1998")
	@Test 
	void testSaveCourseReturnsPage() throws Exception {
		Course course = new Course(100, "testsave", "Lola", 1971,9);
		course.setInstructor(new Instructor("maria1998", null, null));
		doNothing().when(courseService).save("maria1998", course);
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(course.getId()));
	    multiValueMap.add("title", course.getTitle());
	    multiValueMap.add("description", course.getDescription());
	    multiValueMap.add("semester", Integer.toString(course.getSemester()));
	    multiValueMap.add("year", Integer.toString(course.getYear()));
	    
	    mockMvc.perform(
	    		post("/courses/save")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/courses/course"));
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testSaveCourseReturnsPageNullTitle() throws Exception {
		Course course = new Course(100, "", "Lola", 1971,9);
		course.setInstructor(new Instructor("maria1998", null, null));
		doNothing().when(courseService).save("maria1998", course);
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(course.getId()));
	    multiValueMap.add("title", course.getTitle());
	    multiValueMap.add("description", course.getDescription());
	    multiValueMap.add("semester", Integer.toString(course.getSemester()));
	    multiValueMap.add("year", Integer.toString(course.getYear()));
	    
	    mockMvc.perform(
	    		post("/courses/save")
			    .params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("courses/course-form")).
				andExpect(model().attribute("errorMessage", "Blank title"));
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testSaveCourseReturnsPageWrongSemester() throws Exception {
		Course course = new Course(100, "testsave", "Lola", 1971,0);
		course.setInstructor(new Instructor("maria1998", null, null));
		doNothing().when(courseService).save("maria1998", course);
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(course.getId()));
	    multiValueMap.add("title", course.getTitle());
	    multiValueMap.add("description", course.getDescription());
	    multiValueMap.add("semester", Integer.toString(course.getSemester()));
	    multiValueMap.add("year", Integer.toString(course.getYear()));
	    
	    mockMvc.perform(
	    		post("/courses/save")
			    .params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("courses/course-form")).
				andExpect(model().attribute("errorMessage", "Invalid semester"));
	}
	
	//TODO
	//Test save with null some values
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testStatisticReturnsPage() throws Exception {

		Course course = new Course(10, "testcourse", "testdescription", 2022, 5);
		ArrayList<Student> students  = new ArrayList<>();
		
		for(int i=0;i<5;i++)
			students.add(new Student(1+i, null, null, 0, 0, 4+i, 4+i, 4+i));
		
		
		
		
		when(courseService.findById(10)).thenReturn(course);
		when(studentService.findByCourse(course)).thenReturn(students);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("courseId", Integer.toString(10));
	    multiValueMap.add("sType", "Max");
	    mockMvc.perform(
	    		get("/courses/showStatistics")
			    .params(multiValueMap))
				.andExpect(status().isOk()).
				andExpect(model().attribute("value", 8.0)).
				andExpect(view().name("courses/statistics-list"));
	}
	
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testShowFormForAdd() throws Exception {
		mockMvc.perform(get("/courses/showFormForAdd")).
		andExpect(status().isOk()).
		andExpect(view().name("courses/course-form"));
		
		
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testShowFormForUpdate() throws Exception {
		Course course = new Course(10, "testcourse", "testdescription", 2022, 5);
		when(courseService.findById(10)).thenReturn(course);
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("courseId", Integer.toString(10));
	    
	    mockMvc.perform(
	    		get("/courses/showFormForUpdate")
			    .params(multiValueMap))
				.andExpect(status().isOk()).
				andExpect(view().name("courses/course-form-update"));
		
		
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testSetup() throws Exception {
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("courseId", Integer.toString(10));
	    multiValueMap.add("sType", "Min");
		
		mockMvc.perform(get("/courses/setup")
		.params(multiValueMap))
		.andExpect(status().isOk()).
		andExpect(view().name("courses/statistics-setup"));
		
		
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testSaveStatInvalidPercentile() throws Exception {
		StatisticSetup statisticSetup = new StatisticSetup();
		statisticSetup.setCourseId(10);
		statisticSetup.setPercentile(0);
		statisticSetup.setsType("Percentile");
		
		
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("courseId", Integer.toString(statisticSetup.getCourseId()));
	    multiValueMap.add("percentile", Integer.toString(statisticSetup.getPercentile()));
	    multiValueMap.add("sType", statisticSetup.getsType());
	
	    mockMvc.perform(
	    		post("/courses/saveStat")
			    .params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("courses/statistics-setup")).
				andExpect(model().attribute("errorMessage", "Invalid value for percentile it must be in range of 1 to 100"));
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testSaveStatDoublePercentile() throws Exception {
		StatisticSetup statisticSetup = new StatisticSetup();
		statisticSetup.setCourseId(10);
		//statisticSetup.setPercentile(101.1);
		statisticSetup.setsType("Percentile");
		
		
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("courseId", Integer.toString(statisticSetup.getCourseId()));
	    multiValueMap.add("percentile", Double.toString(99.1));
	    multiValueMap.add("sType", statisticSetup.getsType());
	
	    mockMvc.perform(
	    		post("/courses/saveStat")
			    .params(multiValueMap))
				.andExpect(status().isBadRequest());
	}
	
	@WithMockUser(value = "maria1998")
	@Test 
	void testSaveStatDoublePercentileInvalidOverPercentile() throws Exception {
		StatisticSetup statisticSetup = new StatisticSetup();
		statisticSetup.setCourseId(10);
	    statisticSetup.setPercentile(101);
		statisticSetup.setsType("Percentile");
		
		
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("courseId", Integer.toString(statisticSetup.getCourseId()));
	    multiValueMap.add("percentile", Integer.toString(statisticSetup.getPercentile()));
	    multiValueMap.add("sType", statisticSetup.getsType());
	
	    mockMvc.perform(
	    		post("/courses/saveStat")
			    .params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("courses/statistics-setup")).
				andExpect(model().attribute("errorMessage", "Invalid value for percentile it must be in range of 1 to 100"));
	}
	@WithMockUser(value = "maria1998")
	@Test 
	void testDelete() throws Exception {
		
		doNothing().when(courseService).deleteById(1);
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("courseId", Integer.toString(1));
		
	    mockMvc.perform(
	    		get("/courses/delete")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/courses/course"));
		
	}
	
	
	
}
