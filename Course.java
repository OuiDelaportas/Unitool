
public class Course {

	private String name;
	private String id;
	private String professor;
	
	
	public Course(String name, String id, String professor) {
		/**
		 * Prepei na mpoun semester, people that passed, people that were examined, school, dept, major, isws kai id foithth. 
		 */
		this.name = name;
		this.id = id;
		this.professor = professor;
	}
	
	
	public String getcName() {
		return name;
	}
	public void setcName(String name) {
		this.name = name;
	}
	public String getcId() {
		return id;
	}
	public void setcId(String id) {
		this.id = id;
	}
	public String getcProfessor() {
		return professor;
	}
	public void setcProfessor(String professor) {
		this.professor = professor;
	}
	
	
}
