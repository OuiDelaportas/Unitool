import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainProfessorPage extends JFrame {
	
	JLayeredPane layeredPane = new JLayeredPane();
	static JLabel usernameLabel = new JLabel();
	static JLabel schoolLabel = new JLabel();
	JPanel coursePanel = new JPanel();
	JPanel newsPanel = new JPanel();
	JPanel optionsPanel = new JPanel();
	JPanel chatPanel = new JPanel();
	JPanel plannerPanel = new JPanel();
	JPanel statsPanel = new JPanel();
	JTable courseTable = null;
	JTable statsTable = null;
	User newUser = null;
	ResultSet rsr;
	ArrayList <Course> courses = new ArrayList();
	ArrayList <User> users = new ArrayList();
	String check = null;
	
	/**
	 * Create the frame.
	 */
	public MainProfessorPage() {
		Connector.GetInfo("prof");
		
		setBounds(new Rectangle(0, 0, 1030, 540));
		getContentPane().setLayout(null);
		
		/*
		 * Panels get filled
		 */
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
		
		rsr = Connector.getCourses();
		DefaultTableModel courseTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος",  "Εξάμηνο"}, 0);
		DefaultTableModel statsTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος", "Εξάμηνο", "Διδάσκων"}, 0);
		try {
			while(rsr.next()) {
				if(rsr.getString("school").equals(schoolLabel.getText())) {
					Course newCourse = new Course(rsr.getString("course_name"), rsr.getString("id"), rsr.getString("prof_id"), rsr.getString("course_id"), 
							Connector.getGrades(rsr.getString("course_id")), rsr.getString("semester"), rsr.getString("dept"), rsr.getFloat("passed"),
							rsr.getFloat("examined"), rsr.getString("school"));
					courses .add(newCourse);
					if(usernameLabel.getText().equals(newCourse.getcProfessor()) && !newCourse.getcName().equals(check)) {
						courseTableModel.addRow(new Object[] {newCourse.getcName(), newCourse.getSemester()});
						check = newCourse.getcName();
					}
				}
			}
			rsr.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		courseTable = new JTable(courseTableModel) {
			public boolean isCellEditable(int row,int column){
			return false;
			}
		};
		
		for(Course c: courses) {
			if(schoolLabel.getText().equals(c.getSchool())) {
				statsTableModel.addRow(new Object[] {c.getcName(), c.getSemester(), getProfName(users, c.getcProfessor())});
			}
		}
		
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
		 * Buttons and panels are created
		 */
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.DARK_GRAY);
		infoPanel.setBounds(0, 0, 165, 500);
		getContentPane().add(infoPanel);
		infoPanel.setLayout(null);
		
		JButton courseButton = new JButton("\u03A4\u03B1 \u03BC\u03B1\u03B8\u03AE\u03BC\u03B1\u03C4\u03AC \u03BC\u03BF\u03C5");
		courseButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		courseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(coursePanel);
			}
		});
		courseButton.setBounds(10, 150, 145, 23);
		infoPanel.add(courseButton);
		
		JButton statsButton = new JButton("\u03A3\u03C4\u03B1\u03C4\u03B9\u03C3\u03C4\u03B9\u03BA\u03AC");
		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(statsPanel);
			}
		});
		statsButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statsButton.setBounds(10, 200, 145, 23);
		infoPanel.add(statsButton);
		
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
		
		schoolLabel.setForeground(Color.WHITE);
		schoolLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		schoolLabel.setBounds(10, 30, 110, 14);
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
		layeredPane.add(coursePanel, "name_39667471417200");
		coursePanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(courseTable);
		scrollPane.setBounds(10, 11, 665, 478);
		coursePanel.add(scrollPane);
		
		statsPanel.setBackground(Color.GRAY);
		layeredPane.add(statsPanel, "name_27716693154100");
		statsPanel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane(statsTable);
		scrollPane_1.setBounds(10, 11, 665, 478);
		statsPanel.add(scrollPane_1);
		
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