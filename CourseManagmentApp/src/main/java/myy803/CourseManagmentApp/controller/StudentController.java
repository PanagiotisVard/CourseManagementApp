package myy803.CourseManagmentApp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myy803.CourseManagmentApp.entity.Course;

import myy803.CourseManagmentApp.entity.Student;
import myy803.CourseManagmentApp.service.CourseService;
import myy803.CourseManagmentApp.service.StudentService;


@Controller
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CourseService courseService;
	
	private Course course;
	
	@GetMapping("/students")
	public String displayStudent(@RequestParam("courseId") int courseId, Model theModel) {
		
		course = courseService.findById(courseId);
		
		List<Student> students = studentService.findByCourse(course);
		theModel.addAttribute("cId", courseId);
		theModel.addAttribute("students", students);
		
		return "students/students";
	
	}
	
	@GetMapping("/showFormForAdd")
	public String displayForm(@RequestParam("courseId") int courseId, Model theModel) {
	
		Student student = new Student();
		theModel.addAttribute("student",student);
		theModel.addAttribute("cId", courseId);
		
		return "students/student-form";
	}
	
	@PostMapping("/save")
	public String saveStudent(@ModelAttribute("student")Student theStudent, Model theModel) {
		
		theStudent.setCourse(course);
		int courseId = theStudent.getCourse().getId();
		
		
		
		if(theStudent.getName().isEmpty()) {
			List<Student> students = studentService.findByCourse(course);
			theModel.addAttribute("cId", courseId);
			theModel.addAttribute("students", students);
			theModel.addAttribute("errorMessage", "Empty name");
			return "students/student-form";
		}
		
		else if(theStudent.getYear()<1970) {
			List<Student> students = studentService.findByCourse(course);
			theModel.addAttribute("cId", courseId);
			theModel.addAttribute("students", students);
			theModel.addAttribute("errorMessage", "Invalid year");
			return "students/student-form";
		}
		
		else if(theStudent.getSemester()<=0) {
			List<Student> students = studentService.findByCourse(course);
			theModel.addAttribute("cId", courseId);
			theModel.addAttribute("students", students);
			theModel.addAttribute("errorMessage", "Invalid semester");
			return "students/student-form";
		}
		
		studentService.save(theStudent);
		return "redirect:/students/students?courseId="+course.getId();
	}
	
	@GetMapping("/delete")
	public String deleteStudent(@RequestParam("studentId") int theId) {
		
		studentService.deleteById(theId);
		
		return "redirect:/students/students?courseId="+course.getId();
		
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentId") int theId,Model theModel) {
		
		Student theStudent = studentService.findById(theId);
		
		theModel.addAttribute("student",theStudent);
		theModel.addAttribute("cId", theStudent.getCourse().getId());
		
		return "students/student-form";
	}
	
	@PostMapping("/save-grades")
	public String saveStudentGrades(@ModelAttribute("student")Student theStudent,Model theModel) {
		theStudent.setCourse(course);
		
		if((theStudent.getExamsGrade() < 0 || theStudent.getExamsGrade() > 10) || 
		(theStudent.getProjectGrade() < 0 || theStudent.getProjectGrade() > 10)) {
			theModel.addAttribute("student",theStudent);
			theModel.addAttribute("cId", theStudent.getCourse().getId());
			theModel.addAttribute("errorMessage","Grade must be between in 0-10!");
			return "students/grades-form";
		}
		
		else if((theStudent.getExamsWeight() < 0 || theStudent.getExamsWeight() > 1) || 
		(theStudent.getProjectWeight() < 0 || theStudent.getProjectWeight() > 1)) {
			theModel.addAttribute("student",theStudent);
			theModel.addAttribute("cId", theStudent.getCourse().getId());
			theModel.addAttribute("errorMessage","Weight must be between in 0-1!");
			return "students/grades-form";
		}
		
		
		
		studentService.calculateOverallGrade(theStudent);
		return "redirect:/students/students?courseId="+course.getId();
	}
	
	@GetMapping("/showFormForGrades")
	public String showFormForGrades(@RequestParam("studentId") int theId,Model theModel) {
		Student theStudent = studentService.findById(theId);
		theModel.addAttribute("student",theStudent);
		theModel.addAttribute("cId", theStudent.getCourse().getId());
		
		return "students/grades-form";
	}


}
