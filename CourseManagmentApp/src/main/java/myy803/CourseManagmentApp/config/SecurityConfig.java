package myy803.CourseManagmentApp.config;

import myy803.CourseManagmentApp.service.login.UserDetailsServiceImpl;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	

     
    @Bean
    public UserDetailsService userDetailsServicee() {
        return new UserDetailsServiceImpl();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	BCryptPasswordEncoder password=new BCryptPasswordEncoder();
        return password;
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServicee());
        authProvider.setPasswordEncoder(passwordEncoder());
        
         
        return authProvider;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

	// ZAS keep it simple - No need for custom login page
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		

		http.authorizeRequests()
			.antMatchers("/users/show_form", "/users/register*", "/","/bg2.jpg")
			.permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().defaultSuccessUrl("/users/home.html")
			.and()
			.logout().permitAll()
			.logoutSuccessUrl("/index.html");
					
	}
	
		
}






