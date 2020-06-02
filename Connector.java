import java.sql.*;

public class Connector {
	private static Connection connection = null;                                             // The database connection object.
    private static Statement statement; 	
    private PreparedStatement PrepStmt;  
    private static ResultSet rsr;                                                    
	
    private String uName;
    private String Pass;
    private String URL;
    //private String host = "localhost";
    private String database;
    private static String ID;
    
    
    public Connector(String user, String passwd, String url, String DB) {
    	uName = user;
    	Pass = passwd;
    	URL = url;
    	database = DB;
    }
    
    /**
     * Connects to the database. 
     */
    
    public boolean connectToDB() throws Exception {
        try {
          // Setup the connection with the DB
          connection = DriverManager.getConnection(URL + database + "?" + "user=" + uName + "&password=" + Pass );
        }catch (Exception e) {
              throw e;
        }
        if(connection != null)
        	return true;
        else
        	return false;
    }
    
    /**
     * Checks the login credentials. Needs to be updated to match the DB that i will create some day.
     */
    
    public String checkCredentials(String uname, String pass, String userType) {
    	try {
			PrepStmt = connection.prepareStatement("SELECT id,password,user_type FROM user;");
			rsr = PrepStmt.executeQuery();
			while(rsr.next()) {
				if(uname.equals(rsr.getString("id")) && pass.equals(rsr.getString("password"))) {
					userType = rsr.getString("user_type");
					ID = uname;
					rsr.close();
					return userType;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return userType;
    }
    
    public static void GetInfo(String userType) {
    	try {
			statement = connection.createStatement();
			rsr = statement.executeQuery("SELECT * FROM user");
			while(rsr.next()) {
				if(userType.equals("stud")) {
					if(ID.equals(rsr.getString("id"))) {
						MainStudentPage.setUsernameLabelText(rsr.getString("id"));
						MainStudentPage.setSemesterLabelText(rsr.getString("semester"));
						MainStudentPage.setSchoolLabelText(rsr.getString("school"));
						break;
					}
				}else if(userType.equals("prof")) {
					if(ID.equals(rsr.getString("id"))) {
						MainProfessorPage.setUsernameLabelText(rsr.getString("id"));
						MainProfessorPage.setSchoolLabelText(rsr.getString("school"));
						break;
					}
				}else {
					if(ID.equals(rsr.getString("id"))) {
						MainSecretaryPage.setUsernameLabelText(rsr.getString("id"));
						MainSecretaryPage.setSchoolLabelText(rsr.getString("school"));
						break;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
    
    public static ResultSet getCourses() {
    	try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM courses WHERE id = ?");
			stmt.setString(1, ID);
			rsr = stmt.executeQuery();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsr;
    }
    
    /**
     * Closes the statement so that it can execute a different query.
     */
    
    public static void close(Statement st)
    {
        try
        {
            if (st != null)
            {
                st.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Closes the result set so that it can parse different data.
     */
    
    public static void close(ResultSet rs)
    {
        try
        {
            if (rs != null)
            {
                rs.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Close the connection to the DB
     */
    
    public static void close(Connection connection)
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
}
