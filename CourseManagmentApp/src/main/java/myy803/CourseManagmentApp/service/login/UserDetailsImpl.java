package myy803.CourseManagmentApp.service.login;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import myy803.CourseManagmentApp.entity.Instructor;

public class UserDetailsImpl implements UserDetails {
	
	private Instructor instructor;
	
	
	
	public UserDetailsImpl(Instructor instructor) {
		super();
		this.instructor = instructor;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return instructor.getPassword();
	}

	@Override
	public String getUsername() {
		return instructor.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	

}
