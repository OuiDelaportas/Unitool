import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MainStudentPage extends JFrame {
	
	
	JLayeredPane layeredPane = new JLayeredPane();
	static JLabel usernameLabel = new JLabel();
	static JLabel schoolLabel = new JLabel();
	static JLabel semesterLabel = new JLabel();
	JPanel optionsPanel = new JPanel();
	JPanel formPanel = new JPanel();
	JPanel requestPanel = new JPanel();
	JPanel gradePanel = new JPanel();
	JPanel coursePanel = new JPanel();
	JPanel plannerPanel = new JPanel();
	JPanel chatPanel = new JPanel();
	JPanel newsPanel = new JPanel();
	JPanel statsPanel = new JPanel();
	JTextArea textArea = new JTextArea();
	Course newCourse = null;
	ArrayList <Course> courses = new ArrayList();
	JTable gradeTable = null;
	JTable courseTable = null;
	JTable formTable = null;
	JTable statsTable = null;
	ResultSet rsr;
	User newUser = null;
	ArrayList <User> users = new ArrayList();
	ArrayList <String> courseSelected = new ArrayList();
	
	/**
	 * Create the frame.
	 */
	public MainStudentPage() {
		Connector.GetInfo("stud");
		
		setBounds(new Rectangle(0, 0, 1030, 540));
		getContentPane().setLayout(null);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.DARK_GRAY);
		infoPanel.setBounds(0, 0, 165, 500);
		getContentPane().add(infoPanel);
		infoPanel.setLayout(null);
		
		JButton courseButton = new JButton("Τα μαθήματα μου");
		courseButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		courseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(coursePanel);
			}
		});
		courseButton.setBounds(10, 150, 145, 23);
		infoPanel.add(courseButton);
		
		JButton statsButton = new JButton("Στατιστικά");
		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(statsPanel);
			}
		});
		statsButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statsButton.setBounds(10, 200, 145, 23);
		infoPanel.add(statsButton);
		/*
		 * The grade list is fetched.
		 */
		rsr = Connector.getCourses();
		DefaultTableModel gradeTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος", "Βαθμός Μαθήματος", "Εξάμηνο"}, 0);
		DefaultTableModel courseTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος",  "Εξάμηνο"}, 0);
		DefaultTableModel formTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος", "Εξάμηνο", "Διδάσκων"}, 0);
		DefaultTableModel statsTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος", "Εξάμηνο", "Διδάσκων"}, 0);
		try {
			while(rsr.next()) {
				if(rsr.getString("school").equals(schoolLabel.getText())) {
					newCourse = new Course(rsr.getString("course_name"), rsr.getString("id"), rsr.getString("prof_id"), rsr.getString("course_id"), 
							Connector.getGrades(rsr.getString("course_id")), rsr.getString("semester"), rsr.getString("dept"), rsr.getFloat("passed"),
							rsr.getFloat("examined"), rsr.getString("school"));
					courses.add(newCourse);
					if(usernameLabel.getText().equals(newCourse.getId())) {
						gradeTableModel.addRow(new Object[] {newCourse.getcName(), newCourse.getGrade(), newCourse.getSemester()});;
						courseTableModel.addRow(new Object[] {newCourse.getcName(), newCourse.getSemester()});
					}
				}
			}
			rsr.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gradeTable = new JTable(gradeTableModel) {
			public boolean isCellEditable(int row,int column){
			return false;
			}
		};
		courseTable = new JTable(courseTableModel) {
			public boolean isCellEditable(int row,int column){
			return false;
			}
		};
		
		rsr = Connector.getUsers();	
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
		/*
		 * Secretary's office has to update the list with courses
		 */
		for(Course c: courses) {
			statsTableModel.addRow(new Object[] {c.getcName(), c.getSemester(), getProfName(users, c.getcProfessor())});
			if(c.getId().equals(usernameLabel.getText()) && c.getSemester().compareTo(semesterLabel.getText())<=0) {
				formTableModel.addRow(new Object[] {c.getcName(), c.getSemester(), getProfName(users, c.getcProfessor())});
			}
		}
		formTable = new JTable(formTableModel) {
			public boolean isCellEditable(int row,int column){
			return false;
			}
		};
		formTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String value = formTable.getValueAt(formTable.getSelectedRow(), 0).toString();
				if(!courseSelected.contains(value)) {
					courseSelected.add(value);
				}
			}
		});
		
		statsTable = new JTable(statsTableModel) {
			public boolean isCellEditable(int row,int column){
			return false;
			}
		};
		statsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String value = statsTable.getValueAt(statsTable.getSelectedRow(), 0).toString();
				for(Course c: courses) {
					if(c.getcName().equals(value)) {
						StatsFrame sFrame = new StatsFrame(value, c.getPassed(), c.getExamined());
						sFrame.setSize(450, 250);
						sFrame.setVisible(true);
						break;
					}
				}
				
			}
		});
		
		/*
		 * Buttons and labels customized
		 */
		JButton gradeButton = new JButton("Βαθμολογίες");
		gradeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(gradePanel);
			}
		});
		gradeButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		gradeButton.setBounds(10, 250, 145, 23);
		infoPanel.add(gradeButton);
		
		JButton selectionButton = new JButton("\u0394\u03B7\u03BB\u03CE\u03C3\u03B5\u03B9\u03C2");
		selectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(formPanel);
			}
		});
		selectionButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		selectionButton.setBounds(10, 300, 145, 23);
		infoPanel.add(selectionButton);
		
		JButton requestButton = new JButton("\u0391\u03B9\u03C4\u03AE\u03C3\u03B5\u03B9\u03C2");
		requestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(requestPanel);
			}
		});
		requestButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		requestButton.setBounds(10, 350, 145, 23);
		infoPanel.add(requestButton);
		
		JButton logoutButton = new JButton("\u0391\u03C0\u03BF\u03C3\u03CD\u03BD\u03B4\u03B5\u03C3\u03B7");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Connector.close();
				Login frame = new Login();
			}
		});
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		logoutButton.setBounds(10, 466, 145, 23);
		infoPanel.add(logoutButton);
		
		usernameLabel.setForeground(Color.WHITE);
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		usernameLabel.setBounds(10, 10, 110, 14);
		infoPanel.add(usernameLabel);
		
		semesterLabel.setForeground(Color.WHITE);
		semesterLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		semesterLabel.setBounds(10, 30, 110, 14);
		infoPanel.add(semesterLabel);
		
		schoolLabel.setForeground(Color.WHITE);
		schoolLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		schoolLabel.setBounds(10, 50, 110, 14);
		infoPanel.add(schoolLabel);
		
		JPanel mainpagePanel = new JPanel();
		mainpagePanel.setBounds(165, 0, 685, 500);
		getContentPane().add(mainpagePanel);
		mainpagePanel.setLayout(new CardLayout(0, 0));
		
		mainpagePanel.add(layeredPane, "name_23958904944300");
		layeredPane.setLayout(new CardLayout(0, 0));
		
		newsPanel.setBackground(Color.GRAY);
		layeredPane.add(newsPanel, "name_23978823487000");
		
		chatPanel.setBackground(Color.GRAY);
		layeredPane.add(chatPanel, "name_24018466741200");

		plannerPanel.setBackground(Color.GRAY);
		layeredPane.add(plannerPanel, "name_24020787422800");
		
		coursePanel.setBackground(Color.GRAY);
		layeredPane.add(coursePanel, "name_39396003979000");
		coursePanel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane(courseTable);
		scrollPane_1.setBounds(10, 11, 665, 478);
		coursePanel.add(scrollPane_1);
		
		gradePanel.setBackground(Color.GRAY);
		layeredPane.add(gradePanel, "name_38718138169000");
		gradePanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(gradeTable);
		scrollPane.setBounds(10, 11, 665, 478);
		gradePanel.add(scrollPane);
		requestPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		requestPanel.setBackground(Color.GRAY);
		layeredPane.add(requestPanel, "name_38762156049700");
		requestPanel.setLayout(null);
		
		textArea.setBounds(10, 363, 665, 126);
		requestPanel.add(textArea);
		
		JButton cButton = new JButton("Πιστοποιητικό Αναλυτικής Βαθμολογίας");
		cButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(User u: users) {
					if(u.getId().equals(usernameLabel.getText())) {
						Connector.updateRequests(cButton.getText(), u.getSchool(), textArea.getText(), 1);
					}
				}
				textArea.setText(null);
			}
		});
		cButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cButton.setBounds(10, 90, 300, 65);
		requestPanel.add(cButton);
		
		JButton gButton = new JButton("Πιστοποιητικό Σπουδών");
		gButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(User u: users) {
					if(u.getId().equals(usernameLabel.getText())) {
						Connector.updateRequests(gButton.getText(), u.getSchool(), textArea.getText(), 2);
					}
				}
				textArea.setText(null);
			}
		});
		gButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		gButton.setBounds(360, 90, 315, 65);
		requestPanel.add(gButton);
		
		JLabel lblNewLabel = new JLabel("Λεπτομέρειες - Σχόλια");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(185, 310, 250, 35);
		requestPanel.add(lblNewLabel);
		
		formPanel.setBackground(Color.GRAY);
		layeredPane.add(formPanel, "name_38765711160400");
		formPanel.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane(formTable);
		scrollPane_2.setBounds(10, 11, 665, 440);
		formPanel.add(scrollPane_2);
		
		JButton formButton = new JButton("Επιβεβαίωση Δήλωσης");
		formButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connector.updateForms(courseSelected);
			}
		});
		formButton.setBounds(222, 462, 232, 27);
		formPanel.add(formButton);
		
		statsPanel.setBackground(Color.GRAY);
		layeredPane.add(statsPanel, "name_4371977862900");
		statsPanel.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane(statsTable);
		scrollPane_3.setBounds(10, 11, 665, 478);
		statsPanel.add(scrollPane_3);
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBackground(Color.DARK_GRAY);
		optionsPanel.setBounds(850, 0, 165, 500);
		getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);
		
		JButton chatButton = new JButton("Chat");
		chatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(chatPanel);
			}
		});
		chatButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chatButton.setBounds(10, 150, 145, 23);
		optionsPanel.add(chatButton);
		
		JButton plannerButton = new JButton("Planner");
		plannerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(plannerPanel);
			}
		});
		plannerButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		plannerButton.setBounds(10, 200, 145, 23);
		optionsPanel.add(plannerButton);
		
		JButton newsButton = new JButton("News");
		newsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(newsPanel);
			}
		});
		newsButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		newsButton.setBounds(10, 250, 145, 23);
		optionsPanel.add(newsButton);
		
	}
	
	private void switchPanels(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	public static void setUsernameLabelText(String text) {
		usernameLabel.setText(text);
    }
	
	public static void setSemesterLabelText(String text) {
		semesterLabel.setText(text);
    }
	
	public static void setSchoolLabelText(String text) {
		schoolLabel.setText(text);
    }
	
	public String getProfName(ArrayList <User> users, String id) {
		for(User u: users) {
			if(u.getId().equals(id)) {
				return u.getLname();
			}
		}
		return null;
	}
	
	 public boolean isCellEditable(int row, int column) {
	       return false;
	 }
}
