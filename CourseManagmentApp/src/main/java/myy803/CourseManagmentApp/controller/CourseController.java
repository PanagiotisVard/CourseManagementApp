package myy803.CourseManagmentApp.controller;


import java.text.DecimalFormat;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Instructor;
import myy803.CourseManagmentApp.service.CourseService;
import myy803.CourseManagmentApp.service.InstructorService;
import myy803.CourseManagmentApp.service.statistics.StatisticStrategy;


@Controller
@RequestMapping("/courses")
public class CourseController {
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private InstructorService instructorService;
	
	
	@Autowired
	private ApplicationContext context;

	public CourseController() {
	}
	

	
	@GetMapping("/course")
	public String displayCourses(Model theModel) {
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Instructor instructor = instructorService.findByName(currentPrincipalName);

		List<Course> courses = courseService.findByInstructor(instructor);
		theModel.addAttribute("courses", courses);
		return "courses/course";
	}
	
	
	@GetMapping("/showFormForAdd")
	public String displayForm(Model theModel) {
		
	
		Course course = new Course();
		
		
		theModel.addAttribute("course", course);
		
		return "courses/course-form";
	}

	@PostMapping("/save")
	public String saveCourse(@ModelAttribute("course")Course theCourse, Model theModel) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		if (theCourse.getTitle().isEmpty()) {
			theModel.addAttribute("errorMessage", "Blank title");
			return "courses/course-form";
		}
		else if(theCourse.getDescription().isEmpty()) {
			theModel.addAttribute("errorMessage", "Blank description");
			return "courses/course-form";
		}
		
		else if(theCourse.getYear() < 1970) {
			theModel.addAttribute("errorMessage", "Invalid year");
			return "courses/course-form";
		}
		
		else if(theCourse.getSemester() <= 0) {
			theModel.addAttribute("errorMessage", "Invalid semester");
			return "courses/course-form";
		}
		courseService.save(currentPrincipalName, theCourse);
		return "redirect:/courses/course";
	}
	@GetMapping("/delete")
	public String deleteCourse(@RequestParam("courseId") int theId) {
		
		courseService.deleteById(theId);
		
		return "redirect:/courses/course";
		
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("courseId") int theId,Model theModel) {
		
		
		Course theCourse = courseService.findById(theId);
		
		theModel.addAttribute("course",theCourse);
		
		return "courses/course-form-update";
	}
	
	@GetMapping("/showStatistics")
	public String showStatistics(@RequestParam("courseId") int theId, @RequestParam("sType") String type, Model theModel) {
		theModel.addAttribute("cId", theId);
		theModel.addAttribute("sType", type);
		
		
		if (!type.isBlank()) {
			StatisticStrategy stat = (StatisticStrategy) context.getBean(type);

				
			double val = stat.calculateStatistic(theId);
			DecimalFormat df = new DecimalFormat("#.##");
			val = Double.valueOf(df.format(val));
			theModel.addAttribute("value",val);
			theModel.addAttribute(type+"Value", true);
			
		}
		return "courses/statistics-list";
		
	}
	
	


	
	@GetMapping("/setup")
	public String setup(@RequestParam("courseId") int theId, @RequestParam("sType") String type, Model theModel) {
		StatisticSetup stat = new StatisticSetup();
		stat.setCourseId(theId);
		stat.setsType(type);
		theModel.addAttribute("cId", theId);
		theModel.addAttribute("sType", type);
		theModel.addAttribute("stat",stat);
		return "courses/statistics-setup";
	}
	@PostMapping("/saveStat")
	public String saveStat(@ModelAttribute("course") StatisticSetup theStatisticSetup, Model theModel) {
		
		if(theStatisticSetup.getPercentile() <= 0 || theStatisticSetup.getPercentile() > 100) {
			StatisticSetup stat = new StatisticSetup();
			stat.setCourseId(theStatisticSetup.getCourseId());
			stat.setsType(theStatisticSetup.getsType());
			theModel.addAttribute("cId", theStatisticSetup.getCourseId());
			theModel.addAttribute("sType", theStatisticSetup.getsType());
			theModel.addAttribute("stat",stat);
			
			theModel.addAttribute("errorMessage", "Invalid value for percentile it must be in range of 1 to 100");
			return "courses/statistics-setup";
		}
		StatisticStrategy stat = (StatisticStrategy) context.getBean("Percentile");
		
		stat.setPercentile(theStatisticSetup.getPercentile());
		
		return "redirect:/courses/showStatistics?courseId="+theStatisticSetup.getCourseId()+"&sType="+theStatisticSetup.getsType();
		
		
	}	
}
