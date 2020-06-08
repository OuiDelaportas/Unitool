import java.util.ArrayList;

public class Course {

	private String name;
	private String ID;
	private String professorID;
	private String cid;
	private String grade;
	private String semester;
	private String dept;
	private float examined;
	private float passed;
	private String school;

	public Course(String name, String id, String professor, String cid, String grade, String semester, String dept, float passed, float examined, String school) {
		this.name = name;
		this.ID = id;
		this.professorID = professor;
		this.cid = cid;
		this.grade = grade;
		this.semester = semester;
		this.dept = dept;
		this.passed = passed;
		this.examined = examined;
		this.school = school;
	}
	
	public float getExamined() {
		return examined;
	}

	public void setExamined(int examined) {
		this.examined = examined;
	}

	public float getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
		this.passed = passed;
	}
	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getcName() {
		return name;
	}
	public void setcName(String name) {
		this.name = name;
	}
	public String getId() {
		return ID;
	}
	public void setId(String id) {
		this.ID = id;
	}
	public String getcProfessor() {
		return professorID;
	}
	public void setcProfessor(String professor) {
		this.professorID = professor;
	}
	public static String getCourseID(ArrayList <Course> courses, String name) {
		for(Course c: courses) {
			if(c.getcName().equals(name)) {
				return c.getCid();
			}
		}
		return null;
	}
	public static String getCourseName(ArrayList <Course> courses, String id) {
		for(Course c: courses) {
			if(c.getCid().equals(id)) {
				return c.getcName();
			}
		}
		return null;
	}
}
