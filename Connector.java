import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.awt.AwtCalendar;
import com.mindfusion.scheduling.model.Appointment;

public class Connector {
	private static Connection connection = null;
    private static Statement statement; 	
    private PreparedStatement PrepStmt;  
    private static ResultSet rsr;                                                    
    private String uName;
    private String Pass;
    private String URL;
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
    /*
     * Fetches the user information(Username, School, semester)
     */
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
    /*
     * Fetches data about users
     */
    public static ArrayList<User> getUsers(User newUser, ArrayList <User> users) {
    	try {
			Statement stmt = connection.createStatement();
			rsr = stmt.executeQuery("SELECT * FROM user");
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			while(rsr.next()) {
					newUser = new User(rsr.getString("usname"), rsr.getString("first_name"), rsr.getString("last_name"), rsr.getString("id"), 
							rsr.getString("user_type"), rsr.getString("semester"), rsr.getString("school"), rsr.getString("dept"));
					users.add(newUser);
			}
			rsr.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
    /*
     * Fetches course names to show
     */
    public static ResultSet getCourses() {
    	try {
			Statement stmt = connection.createStatement();
			rsr = stmt.executeQuery("SELECT * FROM courses");
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsr;
    }
    /*
     * Gets grades depending on user id and course id
     */
    public static String getGrades(String courseID, String id) {
    	String gr = null ;
    	try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM grades WHERE id = ? AND course_id = ?");
			stmt.setString(1, id);
			stmt.setString(2,  courseID);
			rsr = stmt.executeQuery();
			while(rsr.next()) {
				gr = rsr.getString("grade");
			}
			rsr.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return gr;
    }
    /*
     * Updates the form table in the db according to the courses the student chose
     */
    public static void updateForms(ArrayList <String> courses) {
    	int rowCounter = 0;
    	PreparedStatement stmt = null;
		try {
			for(String i: courses) {
				rowCounter++;
				stmt = connection.prepareStatement("UPDATE forms SET course" + rowCounter +"=? WHERE id=?");
				stmt.setString(1, i);
				stmt.setString(2, ID);
				stmt.executeUpdate();
			}
			close(stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    /*
     * Updates the requests table in the db according to the certification the student chose
     */
    public static void updateRequests(String button, String school, String text, int i) {
    	int rowCounter = 0;
    	PreparedStatement stmt;
		try {
				stmt = connection.prepareStatement("UPDATE requests SET school=?, request?=?, info=? WHERE id=?");
				stmt.setString(1, school);
				stmt.setInt(2, i);
				stmt.setString(3, button);
				stmt.setString(4, text);
				stmt.setString(5, ID);
				stmt.executeUpdate();
				
				close(stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    /*
     * Fetches form table data
     */
    public static ResultSet getForms() {
    	try {
			Statement stmt = connection.createStatement();
			rsr = stmt.executeQuery("SELECT * FROM forms");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return rsr;
    }
    /*
     * Fetches request table data
     */
    public static ResultSet getRequests() {
    	try {
			Statement stmt = connection.createStatement();
			rsr = stmt.executeQuery("SELECT * FROM requests");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return rsr;
    }
    /*
     * Updates db based on grades in jtable gradeTable
     */
    public static void updateGrade(ArrayList<Course> courses, String grade, String id, String cName) {
    	PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("UPDATE grades SET grade=? WHERE id=? AND course_id=?");
			stmt.setString(1, grade);
			stmt.setString(2, id);
			stmt.setString(3, Course.getCourseID(courses, cName));
			stmt.executeUpdate();
			
			close(stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /*
     * Fetches the news table data
     */
    public static DefaultTableModel getNews(String school, JLabel schoolLabel, JLabel usernameLabel, ArrayList <Course> courses, ArrayList <User> users, DefaultTableModel newsTableModel) {
    	try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM news WHERE school=?");
			stmt.setString(1, school);
			rsr = stmt.executeQuery();

			while(rsr.next()) {
				if(schoolLabel.getText().equals(rsr.getString("school"))) {
					if(!((rsr.getString("course_id").equals(null) || rsr.getString("course_id").equals("")) && (rsr.getString("school").equals(null) || 
							rsr.getString("school").equals("")) && (rsr.getString("text").equals(null) || rsr.getString("text").equals("")) && 
							(rsr.getString("title").equals(null) || rsr.getString("title").equals("")))) {
						newsTableModel.addRow(new Object[] {Course.getCourseName(courses, rsr.getString("course_id")), rsr.getString("title"), 
							User.getUserName(users, rsr.getString("user_id")), rsr.getString("text")});
					}
				}
			}
			close(rsr);
			close(stmt);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return newsTableModel;
    }
    /*
     * updates the data in the news table. Available to secretaries and professors only
     */
    public static DefaultTableModel updateNews(String cID, String school, String text, String title, String id, 
    		DefaultTableModel newsTableModel, ArrayList <User> users, JLabel usernameLabel) {
    	PreparedStatement stmt;
    	try {
			stmt = connection.prepareStatement("UPDATE news SET course_id=?, school=?, text=?, title=? WHERE user_id=?");
			stmt.setString(1, cID);
			stmt.setString(2, school);
			stmt.setString(3, text);
			stmt.setString(4, title);
			stmt.setString(5, id);
			stmt.executeUpdate();
			
			close(stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	newsTableModel.addRow(new Object[] {"Νέα Προσθήκη", "Νέα Προσθήκη", User.getUserName(users, usernameLabel.getText()), "Νέα Προσθήκη"});
    	return newsTableModel;
    }
    /*
     * Fetches data from planner table
     */
    public static DefaultTableModel getPlans(String id, DefaultTableModel plannerTableModel) {
    	PreparedStatement stmt;
    	try {
    		stmt = connection.prepareStatement("SELECT * FROM planner WHERE id=?");
			stmt.setString(1, id);
			rsr = stmt.executeQuery();
			
			while(rsr.next()) {
					plannerTableModel.addRow(new Object[] {rsr.getString("event_name"), rsr.getString("date"), rsr.getString("hour"), rsr.getString("description")});
			}
			
			close(rsr);
			close(stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return plannerTableModel;
    }
    /*
     * Updates the planner table
     */
    public static DefaultTableModel setPlans(String name, String date, String hour, String desc, String counter, DefaultTableModel plannerTableModel) {
    	PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("UPDATE planner SET event_name=?, date=?, hour=?, description=? WHERE event_id=?");
			stmt.setString(1, name);
	    	stmt.setString(2, date);
	    	stmt.setString(3, hour);
	    	stmt.setString(4, desc);
	    	stmt.setString(5, counter);
	    	stmt.executeUpdate();
	    	
	    	close(stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return plannerTableModel;
    }
    /*
     * Deletes a plan
     */
    public static void deletePlan(String name, String date, String hour, String description) {
    	PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("UPDATE planner SET event_name=?, date=?, hour=?, description=? WHERE event_name=? AND date=? AND hour=? AND description=?");
			stmt.setString(1, null);
	    	stmt.setString(2, null);
	    	stmt.setString(3, null);
	    	stmt.setString(4, null);
	    	stmt.setString(5, name);
	    	stmt.setString(6, date);
	    	stmt.setString(7, hour);
	    	stmt.setString(8, description);
	    	stmt.executeUpdate();
	    	
	    	close(stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /*
     * gets free space to put a new planS
     */
    public static String getPlanFree() {
    	Statement stmt;
		try {
			stmt = connection.createStatement();
			rsr = stmt.executeQuery("SELECT * FROM planner");
			String name;
			String date;
			String hour;
			String desc;
			while(rsr.next()) {
				name = rsr.getString("event_name");
				date = rsr.getString("date");
				hour = rsr.getString("hour");
				desc = rsr.getString("description");
				if(rsr.wasNull()) {
					return rsr.getString("event_id");
				}else {
					JOptionPane.showMessageDialog(null, "Διάγραψε ένα γεγονός");
				}
			}
			
			close(rsr);
			close(stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ID;
    }
    /**
     * Closes the statement
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
     * Closes the result set
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
     * Closes the connection to the DB
     */
    
    public static void close()
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