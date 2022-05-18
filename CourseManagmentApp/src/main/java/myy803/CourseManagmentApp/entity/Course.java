package myy803.CourseManagmentApp.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "course")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",insertable=true, updatable=true)
	private int id;
	@Column(name = "year")
	private int year;
	@Column(name = "semester")
	private int semester;
	@Column(name = "title")
	private String title;
	@Column(name = "description")//Not Error
	private String description;
	
	@ManyToOne
	@JoinColumn(name="instructor_name")
	private Instructor instructor;
	
	public Course() {
	}
	public Course(int id, String title, String description, int year, int semester) {
		super();
		this.id = id;
		this.year = year;
		this.semester = semester;
		this.title = title;
		this.description = description;
	}
	
	//TODO Add user field 
	public Course(String title, String description, int year, int semester) {
		super();
		this.year = year;
		this.semester = semester;
		this.title = title;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Instructor getInstructor() {
		return instructor;
	}
	public void setInstructor(Instructor userId) {
		this.instructor = userId;
	}
	
	
	
	
	
	

}
