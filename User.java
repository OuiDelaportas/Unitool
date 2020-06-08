import java.util.ArrayList;

public class User {

	private String uname;
	private String name;
	private String lname;
	private String id;
	private String pass;
	private String mail;
	private String school;
	private String dept;
	
	public User(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
		uname = string;
		name = string2;
		lname = string3;
		id = string4;
		pass = string5;
		mail = string6;
		school = string7;
		dept = string8;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public static String getUserName(ArrayList <User> users, String id) {
		for(User u: users) {
			if(u.getId().equals(id)) {
				return u.getLname();
			}
		}
		return null;
	}
}
