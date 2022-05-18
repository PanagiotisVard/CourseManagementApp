package myy803.CourseManagmentApp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id")
	private Course course;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "semester")
	private int semester;
	
	@Column(name = "year")
	private int year;
	
	@Column(name = "project_grade")
	private double projectGrade;
	
	@Column(name = "exams_grade")
	private double examsGrade;
	
	@Column(name = "overall_grade")
	private double overallGrade;
	
	@Transient
	private double projectWeight;
	
	@Transient
	private double examsWeight;
	
	
	public Student() {
		
	}
	
	public Student(int id, Course course, String name, int semester, int year, double projectGrade, double examsGrade,
			double overallGrade) {
		super();
		this.id = id;
		this.course = course;
		this.name = name;
		this.semester = semester;
		this.year = year;
		this.projectGrade = projectGrade;
		this.examsGrade = examsGrade;
		this.overallGrade = overallGrade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public double getProjectGrade() {
		return projectGrade;
	}

	public void setProjectGrade(double projectGrade) {
		this.projectGrade = projectGrade;
	}

	public double getExamsGrade() {
		return examsGrade;
	}

	public void setExamsGrade(double examsGrade) {
		this.examsGrade = examsGrade;
	}

	public double getOverallGrade() {
		return overallGrade;
	}

	public void setOverallGrade(double overallGrade) {
		this.overallGrade = overallGrade;
	}

	public double getProjectWeight() {
		return projectWeight;
	}

	public void setProjectWeight(double projectWeight) {
		this.projectWeight = projectWeight;
	}

	public double getExamsWeight() {
		return examsWeight;
	}

	public void setExamsWeight(double examsWeight) {
		this.examsWeight = examsWeight;
	}
	
	
	
	

}
