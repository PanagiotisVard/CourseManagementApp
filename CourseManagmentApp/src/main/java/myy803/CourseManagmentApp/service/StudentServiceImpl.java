package myy803.CourseManagmentApp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.CourseManagmentApp.dao.StudentDAO;
import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Student;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDAO studentRepository ;



	@Override
	@Transactional
	public Student findById(int theId) {
		return studentRepository.findById(theId);
	}

	@Override
	@Transactional
	public void save(Student student) {
		studentRepository.save(student);

	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		studentRepository.deleteById(theId);
	}

	@Override
	@Transactional
	public List<Student> findByCourse(Course course) {
		return studentRepository.findByCourse(course);
	}

	@Override
	public double calculateOverallGrade(Student student) {
		double examsGrade = student.getExamsGrade();
		double projectGrade = student.getProjectGrade();
		double examsWeight = student.getExamsWeight();
		double projectWeight = student.getProjectWeight();
		double finalGrade = examsGrade*examsWeight+projectGrade*projectWeight;	

		student.setOverallGrade(finalGrade);
		studentRepository.save(student);
		
		return finalGrade;
	}
	
	
	
	
	
	
	

}
