package myy803.CourseManagmentApp.controller;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import myy803.CourseManagmentApp.entity.Instructor;
import myy803.CourseManagmentApp.entity.UserData;
import myy803.CourseManagmentApp.service.InstructorService;
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
  locations = "classpath:applicationTest.properties")
@AutoConfigureMockMvc

class AppControllerTest {

	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	InstructorService instructorService;
	
	@Autowired
	AppController appController;
	
	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
		//System.exit(0);
    }
	
	
	@Test
	void testStudentControllerIsNotNull() {
		
		Assertions.assertNotNull(appController);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	@Test 
	void testListStudentsReturnsPage() throws Exception {
		
		
		mockMvc.perform(get("/users/show_form")).
		andExpect(status().isOk()).
		andExpect(view().name("users/register_form"));
	}
	
	@Test 
	void testRegister() throws Exception {
		
		UserData userData = new UserData("test","test@example.com","testP");
		
		when(instructorService.findByName("test")).thenReturn(null);
		doNothing().when(instructorService).register(userData);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("name", userData.getName());
	    multiValueMap.add("email", userData.getEmail() );
	    multiValueMap.add("password", userData.getPassword());
	    
	    mockMvc.perform(
	    		post("/users/register")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/index.html"));
	}
	
	@Test 
	void testRegisterNullName() throws Exception {
		
		UserData userData = new UserData("","test","test");
		
		doNothing().when(instructorService).register(userData);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("name", userData.getName());
	    multiValueMap.add("email", userData.getEmail() );
	    multiValueMap.add("password", userData.getPassword());
	    
	    mockMvc.perform(
	    		post("/users/register")
			    .params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("users/register_form")).
				andExpect(model().attribute("errorMessage", "Blank name"));
	}
	
	@Test 
	void testRegisterUserExists() throws Exception {
		
		UserData userData = new UserData("test","test@example.com","testP");
		Instructor instructor = new Instructor("test","test","test");
		
		when(instructorService.findByName("test")).thenReturn(instructor);
		doNothing().when(instructorService).register(userData);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("name", userData.getName());
	    multiValueMap.add("email", userData.getEmail() );
	    multiValueMap.add("password", userData.getPassword());
	    
	    mockMvc.perform(
	    		post("/users/register")
			    .params(multiValueMap))
			    .andExpect(status().isOk())
				.andExpect(view().name("users/register_form")).
				andExpect(model().attribute("errorMessage", "User exists"));
	}
	
	@Test 
	void testRegisterWrongEmailFormat() throws Exception {
		
		UserData userData = new UserData("test","test","test");
		
		
		when(instructorService.findByName("test")).thenReturn(null);
		doNothing().when(instructorService).register(userData);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("name", userData.getName());
	    multiValueMap.add("email", userData.getEmail() );
	    multiValueMap.add("password", userData.getPassword());
	    
	    mockMvc.perform(
	    		post("/users/register")
			    .params(multiValueMap))
			    .andExpect(status().isOk())
				.andExpect(view().name("users/register_form")).
				andExpect(model().attribute("errorMessage", "Wrong email format"));
	}
	
}
