
public class User {

	private String name;
	private String id;
	
	public User(String text1, String text2) {//the data from the DB will be inserted in the constructor(that's how the user type will be known)
		/**
		 * prepei na mpoun mail, usertype, school,uname.
		 */
		name = text1;
		id = text2;
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
