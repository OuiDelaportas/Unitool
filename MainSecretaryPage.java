import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainSecretaryPage extends JFrame {
	
	JLayeredPane layeredPane = new JLayeredPane();
	static JLabel usernameLabel = new JLabel();
	static JLabel schoolLabel = new JLabel();
	JPanel optionsPanel = new JPanel();
	JPanel formPanel = new JPanel();
	JPanel requestPanel = new JPanel();
	JPanel gradePanel = new JPanel();
	JPanel plannerPanel = new JPanel();
	JPanel chatPanel = new JPanel();
	JPanel newsPanel = new JPanel();
	JPanel statsPanel = new JPanel();
	ArrayList <Course> courses = new ArrayList();
	JTable courseTable = null;
	JTable statsTable = null;
	JTable requestTable = null;
	JTable formTable = null;
	ResultSet rsr = null;
	Course newCourse = null;
	User newUser = null;
	ArrayList <User> users = new ArrayList();
	
	/**
	 * Create the frame.
	 */
	public MainSecretaryPage() {
		Connector.GetInfo("sec");
		
		setBounds(new Rectangle(0, 0, 1030, 540));
		getContentPane().setLayout(null);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.DARK_GRAY);
		infoPanel.setBounds(0, 0, 165, 500);
		getContentPane().add(infoPanel);
		infoPanel.setLayout(null);
		
		JButton statsButton = new JButton("\u03A3\u03C4\u03B1\u03C4\u03B9\u03C3\u03C4\u03B9\u03BA\u03AC");
		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(statsPanel);
			}
		});
		statsButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statsButton.setBounds(10, 150, 145, 23);
		infoPanel.add(statsButton);
		
		JButton gradeButton = new JButton("\u0392\u03B1\u03B8\u03BC\u03BF\u03BB\u03BF\u03B3\u03AF\u03B5\u03C2");
		gradeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(gradePanel);
			}
		});
		gradeButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		gradeButton.setBounds(10, 200, 145, 23);
		infoPanel.add(gradeButton);
		
		JButton selectionButton = new JButton("\u0394\u03B7\u03BB\u03CE\u03C3\u03B5\u03B9\u03C2");
		selectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(formPanel);
			}
		});
		selectionButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		selectionButton.setBounds(10, 250, 145, 23);
		infoPanel.add(selectionButton);
		
		JButton requestButton = new JButton("\u0391\u03B9\u03C4\u03AE\u03C3\u03B5\u03B9\u03C2");
		requestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(requestPanel);
			}
		});
		requestButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		requestButton.setBounds(10, 300, 145, 23);
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
		
		JPanel mainpagePanel = new JPanel();
		mainpagePanel.setBounds(165, 0, 685, 500);
		getContentPane().add(mainpagePanel);
		mainpagePanel.setLayout(new CardLayout(0, 0));
		
		mainpagePanel.add(layeredPane, "name_23958904944300");
		layeredPane.setLayout(new CardLayout(0, 0));
		
		newsPanel.setBackground(Color.GRAY);
		layeredPane.add(newsPanel, "name_23978823487000");
		newsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		chatPanel.setBackground(Color.GRAY);
		layeredPane.add(chatPanel, "name_24018466741200");
		
		plannerPanel.setBackground(Color.GRAY);
		layeredPane.add(plannerPanel, "name_24020787422800");
		
		gradePanel.setBackground(Color.GRAY);
		layeredPane.add(gradePanel, "name_38718138169000");
		gradePanel.setLayout(null);
		
		rsr = Connector.getUsers();
		DefaultTableModel courseTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος",  "Εξάμηνο"}, 0);
		DefaultTableModel statsTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος", "Εξάμηνο", "Διδάσκων"}, 0);
		DefaultTableModel formTableModel = new DefaultTableModel(new Object[] {"ΑΜ Φοιτητή", "Μάθημα", "Μάθημα", "Μάθημα", "Μάθημα", "Μάθημα", "Μάθημα", "Μάθημα", "Μάθημα"}, 0);
		DefaultTableModel requestTableModel = new DefaultTableModel(new Object[] {"ΑΜ Φοιτητή", "Είδος Βεβαίωσης", "Είδος Βεβαίωσης"}, 0);
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
		rsr = Connector.getCourses();
		try {
			while(rsr.next()) {
					newCourse = new Course(rsr.getString("course_name"), rsr.getString("id"), rsr.getString("prof_id"), rsr.getString("course_id"), 
							Connector.getGrades(rsr.getString("course_id")), rsr.getString("semester"), rsr.getString("dept"), rsr.getFloat("passed"),
							rsr.getFloat("examined"), rsr.getString("school"));
					courses.add(newCourse);
				if(schoolLabel.getText().equals(newCourse.getSchool())) {
					courseTableModel.addRow(new Object[] {newCourse.getcName(), newCourse.getSemester()});
				}
			}
			rsr.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rsr = Connector.getForms();
		try {
			while(rsr.next()) {
				for(User u: users) {
					if(u.getId().equals(rsr.getString("id"))) {
						if(u.getSchool().equals(schoolLabel.getText())) {
							formTableModel.addRow(new Object[] {rsr.getString("id"), rsr.getString("course1"), rsr.getString("course2"), rsr.getString("course3")
									, rsr.getString("course4"), rsr.getString("course5"), rsr.getString("course6"), rsr.getString("course7"), rsr.getString("course8")});
						}
					}
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		formTable = new JTable(formTableModel) {
			public boolean isCellEditable(int row,int column){
			return false;
			}
			public String getToolTipText(MouseEvent e) {
		        String tip = null;
		        java.awt.Point p = e.getPoint();
		        int rowIndex = rowAtPoint(p);
		        int colIndex = columnAtPoint(p);
		        int realColumnIndex = convertColumnIndexToModel(colIndex);
		        tip = (String) formTableModel.getValueAt(rowIndex, colIndex);
		        		
		        return tip;
			}
		};
		
		courseTable = new JTable(courseTableModel) {
			public boolean isCellEditable(int row,int column){
			return false;
			}
		};
		/*
		 * Statistics panel is filled
		 */
		for(Course c: courses) {
			if(schoolLabel.getText().equals(c.getSchool())) {
				statsTableModel.addRow(new Object[] {c.getcName(), c.getSemester(), getProfName(users, c.getcProfessor())});
			}
		}
		courseTable = new JTable(courseTableModel) {
			public boolean isCellEditable(int row,int column){
			return false;
			}
		};
		
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
		
		rsr = Connector.getRequests();
		try {
			while(rsr.next()) {
				requestTableModel.addRow(new Object[] {rsr.getString("id"), rsr.getString("request1"), rsr.getString("request2")});
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		requestTable = new JTable(requestTableModel);
		
		JScrollPane scrollPane = new JScrollPane(courseTable);
		scrollPane.setBounds(10, 11, 665, 478);
		gradePanel.add(scrollPane);
		
		requestPanel.setBackground(Color.GRAY);
		layeredPane.add(requestPanel, "name_38762156049700");
		requestPanel.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane(requestTable);
		scrollPane_2.setBounds(10, 11, 665, 478);
		requestPanel.add(scrollPane_2);
		
		formPanel.setBackground(Color.GRAY);
		layeredPane.add(formPanel, "name_38765711160400");
		formPanel.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane(formTable);
		scrollPane_3.setBounds(10, 11, 665, 478);
		formPanel.add(scrollPane_3);
		
		statsPanel.setBackground(Color.GRAY);
		layeredPane.add(statsPanel, "name_26293092121600");
		statsPanel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane(statsTable);
		scrollPane_1.setBounds(10, 11, 665, 478);
		statsPanel.add(scrollPane_1);
		
		optionsPanel.setBackground(Color.DARK_GRAY);
		optionsPanel.setBounds(850, 0, 165, 500);
		getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);
		
		usernameLabel.setForeground(Color.WHITE);
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		usernameLabel.setBounds(10, 10, 110, 14);
		infoPanel.add(usernameLabel);
		
		schoolLabel.setForeground(Color.WHITE);
		schoolLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		schoolLabel.setBounds(10, 30, 110, 14);
		infoPanel.add(schoolLabel);
		
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
	
	public String getProfName(ArrayList <User> users, String id) {
		for(User u: users) {
			if(u.getId().equals(id)) {
				return u.getLname();
			}
		}
		return null;
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
	
	public static void setSchoolLabelText(String text) {
		schoolLabel.setText(text);
    }
}