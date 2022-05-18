package myy803.CourseManagmentApp.serviceTest.statistics;


import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import myy803.CourseManagmentApp.entity.Course;
import myy803.CourseManagmentApp.entity.Student;
import myy803.CourseManagmentApp.service.CourseService;
import myy803.CourseManagmentApp.service.StudentService;
import myy803.CourseManagmentApp.service.statistics.StatisticStrategy;
@SpringBootTest
@TestPropertySource(
		  locations = "classpath:applicationTest.properties")
class StatisticsServiceTest {
	
	@Autowired
	private ApplicationContext context;
	
	@MockBean
	private CourseService courseService;
	
	@MockBean
	private StudentService studentService;
	
	static List<Student> students;
	
	@BeforeAll
	static void setup() {
		students  = new ArrayList<>();
		
		for(int i=0;i<5;i++)
			students.add(new Student(1+i, null, null, 0, 0, 4+i, 4+i, 4+i));

	}

	
	@Test
	void testMaxStatistic() {
		StatisticStrategy stat = (StatisticStrategy) context.getBean("Max");
		
		when(courseService.findById(1)).thenReturn(new Course("test", "description", 1, 1970));
		
		when(studentService.findByCourse((Course) any(Course.class))).thenReturn(students);
		assertEquals(8.0, stat.calculateStatistic(1));
		
	}
	
	@Test
	void testMinStatistic() {
		StatisticStrategy stat = (StatisticStrategy) context.getBean("Min");
		
		when(courseService.findById(1)).thenReturn(new Course("test", "description", 1, 1970));
		
		when(studentService.findByCourse((Course) any(Course.class))).thenReturn(students);
		assertEquals(4.0, stat.calculateStatistic(1));
		
	}
	
	@Test
	void testMeanStatistic() {
		StatisticStrategy stat = (StatisticStrategy) context.getBean("Mean");
		
		when(courseService.findById(1)).thenReturn(new Course("test", "description", 1, 1970));
		
		when(studentService.findByCourse((Course) any(Course.class))).thenReturn(students);
		assertEquals(6.0, stat.calculateStatistic(1));
	}
	
	@Test
	void testSkewnessStatistic() {
		StatisticStrategy stat = (StatisticStrategy) context.getBean("Skewness");
		
		when(courseService.findById(1)).thenReturn(new Course("test", "description", 1, 1970));
		
		when(studentService.findByCourse((Course) any(Course.class))).thenReturn(students);
		assertEquals(0, stat.calculateStatistic(1));
	}
	
	@Test
	void testKurtosisStatistic() {
		StatisticStrategy stat = (StatisticStrategy) context.getBean("Kurtosis");
		
		when(courseService.findById(1)).thenReturn(new Course("test", "description", 1, 1970));
		
		when(studentService.findByCourse((Course) any(Course.class))).thenReturn(students);
		assertEquals(-1.2, stat.calculateStatistic(1), 0.001);
	}
	
	@Test
	void testMedianStatistic() {
		StatisticStrategy stat = (StatisticStrategy) context.getBean("Median");
		
		when(courseService.findById(1)).thenReturn(new Course("test", "description", 1, 1970));
		
		when(studentService.findByCourse((Course) any(Course.class))).thenReturn(students);
		assertEquals(6.0, stat.calculateStatistic(1));
	}
	
	
	@Test
	void testStandardDeviationStatistic() {
		StatisticStrategy stat = (StatisticStrategy) context.getBean("StandardDeviation");
		
		when(courseService.findById(1)).thenReturn(new Course("test", "description", 1, 1970));
		
		when(studentService.findByCourse((Course) any(Course.class))).thenReturn(students);
		assertEquals(1.5811, stat.calculateStatistic(1), 0.001);
	}
	
	@Test
	void testVarianceStatistic() {
		StatisticStrategy stat = (StatisticStrategy) context.getBean("Variance");
		
		when(courseService.findById(1)).thenReturn(new Course("test", "description", 1, 1970));
		
		when(studentService.findByCourse((Course) any(Course.class))).thenReturn(students);
		assertEquals(2.5, stat.calculateStatistic(1));
	}
	
	@Test
	void testPercentilesStatistic() {
		StatisticStrategy stat = (StatisticStrategy) context.getBean("Percentile");
		
		when(courseService.findById(1)).thenReturn(new Course("test", "description", 1, 1970));
		
		when(studentService.findByCourse((Course) any(Course.class))).thenReturn(students);
		stat.setPercentile(10);
		assertEquals(4, stat.calculateStatistic(1));
		stat.setPercentile(25);
		assertEquals(4.5, stat.calculateStatistic(1));
		stat.setPercentile(50);
		assertEquals(6, stat.calculateStatistic(1));
		stat.setPercentile(75);
		assertEquals(7.5, stat.calculateStatistic(1));
		stat.setPercentile(90);
		assertEquals(8, stat.calculateStatistic(1));
	}


}
