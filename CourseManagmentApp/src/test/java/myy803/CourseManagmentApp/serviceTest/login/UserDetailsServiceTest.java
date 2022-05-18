package myy803.CourseManagmentApp.serviceTest.login;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;

import myy803.CourseManagmentApp.entity.Instructor;
import myy803.CourseManagmentApp.service.InstructorService;
import myy803.CourseManagmentApp.service.login.UserDetailsServiceImpl;
@SpringBootTest
@TestPropertySource(
		  locations = "classpath:applicationTest.properties")
class UserDetailsServiceTest {
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@MockBean
	private InstructorService instructorService;


	@Test
	void testLoadByUsername() {
		when(instructorService.findByName("maria1998")).thenReturn(new Instructor("maria1998", "password", null));
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("maria1998");
		assertEquals("maria1998", userDetails.getUsername());
		assertEquals("password", userDetails.getPassword());
	}
	
	@Test
	void testLoadByUsernameUserNotExists() {
		when(instructorService.findByName("maria1998")).thenReturn(null);
	
		Exception exception =  Assertions.assertThrows(UsernameNotFoundException.class, 
				()->{userDetailsServiceImpl.loadUserByUsername("maria1998");});
		
		
		Assertions.assertTrue(exception.getMessage().contains("User not found"));
	}

}
