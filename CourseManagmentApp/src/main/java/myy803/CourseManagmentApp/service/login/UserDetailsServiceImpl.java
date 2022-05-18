package myy803.CourseManagmentApp.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import myy803.CourseManagmentApp.entity.Instructor;
import myy803.CourseManagmentApp.service.InstructorService;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private InstructorService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
        Instructor user = userService.findByName(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }  
        return new UserDetailsImpl(user);
	}
	
	

}
