package myy803.CourseManagmentApp.serviceTest.login;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import myy803.CourseManagmentApp.entity.Instructor;
import myy803.CourseManagmentApp.service.login.UserDetailsImpl;
@SpringBootTest
@TestPropertySource(
		  locations = "classpath:applicationTest.properties")
class UserDetailsTest {

	private UserDetailsImpl userDetails;
	private Instructor instructor;
	
	
	@BeforeEach
	void setup() {
		instructor = new Instructor("maria1998", "maraki", "maria1998@yahoo.com");
		userDetails = new UserDetailsImpl(instructor);
	}
	
	@Test
	void testGetUsername() {
		assertEquals("maria1998", userDetails.getUsername());
	}
	
	@Test
	void testGetPassword() {
		assertEquals("maraki", userDetails.getPassword());
	}
}
