package myy803.CourseManagmentApp.service.statistics;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;

import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Student;
import myy803.CourseManagmentApp.service.CourseService;
import myy803.CourseManagmentApp.service.StudentService;

//@Service
public abstract class TemplateStatisticStrategy implements StatisticStrategy{
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentService studentService;
	
	protected DescriptiveStatistics descriptiveStatistics;
	private List<Student> students;
	private List<Double> grades;
	
	public TemplateStatisticStrategy() {
		descriptiveStatistics = new DescriptiveStatistics();
		grades = new ArrayList<Double>();
	}

	@Override
	public double calculateStatistic(int courseId) {
		Course course = courseService.findById(courseId);
		students = studentService.findByCourse(course);
		prepareDataSet();
		return doActualCalculation();
	}
	
	protected abstract double doActualCalculation();
	
	private void prepareDataSet() {
		grades.clear();
		descriptiveStatistics.clear();
		
		for (Student student : students) {
			grades.add(student.getOverallGrade());
		}
		
		for (Double grade : grades) {
			descriptiveStatistics.addValue(grade);
		}
		
	}

	@Override
	public void setPercentile(int percentile) {
	}
	
	

}
