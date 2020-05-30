
public class User {

	private String uname;
	private String name;
	private String lname;
	private String id;
	private String pass;
	private String mail;
	private String school;
	
	public User(String string, String string2, String string3, String string4, String string5, String string6, String string7) {
		uname = string;
		name = string2;
		lname = string3;
		id = string4;
		pass = string5;
		mail = string6;
		school = string7;
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
}
