import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MainSecretaryPage extends JFrame {
	
	private JLayeredPane layeredPane = new JLayeredPane();
	private JPanel infoPanel = new JPanel();
	private JButton statsButton = new JButton("\u03A3\u03C4\u03B1\u03C4\u03B9\u03C3\u03C4\u03B9\u03BA\u03AC");
	private JButton gradeButton = new JButton("\u0392\u03B1\u03B8\u03BC\u03BF\u03BB\u03BF\u03B3\u03AF\u03B5\u03C2");
	private JButton selectionButton = new JButton("\u0394\u03B7\u03BB\u03CE\u03C3\u03B5\u03B9\u03C2");
	private JButton requestButton = new JButton("\u0391\u03B9\u03C4\u03AE\u03C3\u03B5\u03B9\u03C2");
	private JButton logoutButton = new JButton("\u0391\u03C0\u03BF\u03C3\u03CD\u03BD\u03B4\u03B5\u03C3\u03B7");
	private JPanel mainpagePanel = new JPanel();
	private JButton postButton = new JButton("Ανάρτηση");
	private JButton editButton = new JButton("Επεξεργασία");
	private JButton chatButton = new JButton("Chat");
	private JButton plannerButton = new JButton("Planner");
	private JButton newsButton = new JButton("News");
	static JLabel usernameLabel = new JLabel();
	static JLabel schoolLabel = new JLabel();
	private JPanel optionsPanel = new JPanel();
	private JPanel formPanel = new JPanel();
	private JPanel requestPanel = new JPanel();
	private JPanel gradePanel = new JPanel();
	private JPanel plannerPanel = new JPanel();
	private JPanel chatPanel = new JPanel();
	private JPanel newsPanel = new JPanel();
	private JPanel statsPanel = new JPanel();
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JScrollPane scrollPane_4;
	private ArrayList <Course> courses = new ArrayList();
	private JTable gradeTable;
	private JTable statsTable;
	private JTable requestTable;
	private JTable formTable;
	private JTable newsTable;
	private JTable plannerTable;
	private ResultSet rsr;
	private Course newCourse;
	private User newUser;
	private ArrayList <User> users = new ArrayList();
	private JTextField textField;
	private final JScrollPane scrollPane_5;
	private final JButton deleteButton = new JButton("Διαγραφή Γεγονότος");
	private final JButton saveButton = new JButton("Προσθήκη Γεγονότος");
	
	/**
	 * Create the frame.
	 */
	public MainSecretaryPage() {
		Connector.GetInfo("sec");
		/*
		 * Panel construction
		 */
		setBounds(new Rectangle(0, 0, 1030, 540));
		getContentPane().setLayout(null);
		
		infoPanel.setBackground(Color.DARK_GRAY);
		infoPanel.setBounds(0, 0, 165, 500);
		getContentPane().add(infoPanel);
		infoPanel.setLayout(null);
		
		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(statsPanel);
			}
		});
		statsButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statsButton.setBounds(10, 150, 145, 23);
		infoPanel.add(statsButton);
		
		gradeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(gradePanel);
			}
		});
		gradeButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		gradeButton.setBounds(10, 200, 145, 23);
		infoPanel.add(gradeButton);
		
		selectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(formPanel);
			}
		});
		selectionButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		selectionButton.setBounds(10, 250, 145, 23);
		infoPanel.add(selectionButton);
		
		requestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(requestPanel);
			}
		});
		requestButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		requestButton.setBounds(10, 300, 145, 23);
		infoPanel.add(requestButton);

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
		
		mainpagePanel.setBounds(165, 0, 685, 500);
		getContentPane().add(mainpagePanel);
		mainpagePanel.setLayout(new CardLayout(0, 0));
		
		mainpagePanel.add(layeredPane, "name_23958904944300");
		layeredPane.setLayout(new CardLayout(0, 0));
		
		newsPanel.setBackground(Color.GRAY);
		layeredPane.add(newsPanel, "name_23978823487000");
		newsPanel.setLayout(null);
		
		postButton.setBounds(222, 462, 232, 27);
		newsPanel.add(postButton);
		
		chatPanel.setBackground(Color.GRAY);
		layeredPane.add(chatPanel, "name_24018466741200");
		
		plannerPanel.setBackground(Color.GRAY);
		layeredPane.add(plannerPanel, "name_24020787422800");
		plannerPanel.setLayout(null);
		deleteButton.setBounds(43, 462, 232, 27);
		
		plannerPanel.add(deleteButton);
		saveButton.setBounds(410, 462, 232, 27);
		
		plannerPanel.add(saveButton);
		
		gradePanel.setBackground(Color.GRAY);
		layeredPane.add(gradePanel, "name_38718138169000");
		gradePanel.setLayout(null);
		
		/*
		 * Table models are created and tables get filled
		 */
		users = Connector.getUsers(newUser, users);
		DefaultTableModel gradeTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος", "Βαθμός Μαθήματος", "Εξάμηνο", "ΑΜ Φοιτητή"}, 0);
		DefaultTableModel statsTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος", "Εξάμηνο", "Διδάσκων"}, 0);
		DefaultTableModel formTableModel = new DefaultTableModel(new Object[] {"ΑΜ Φοιτητή", "Μάθημα", "Μάθημα", "Μάθημα", "Μάθημα", "Μάθημα", "Μάθημα", "Μάθημα", "Μάθημα"}, 0);
		DefaultTableModel requestTableModel = new DefaultTableModel(new Object[] {"ΑΜ Φοιτητή", "Είδος Βεβαίωσης", "Είδος Βεβαίωσης"}, 0);
		DefaultTableModel newsTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος", "Τίτλος", "Διδάσκων", "Μήνυμα"}, 0);
		/*
		 * Courses list gets filled along with table models
		 */
		rsr = Connector.getCourses();
		try {
			while(rsr.next()) {
					newCourse = new Course(rsr.getString("course_name"), rsr.getString("id"), rsr.getString("prof_id"), rsr.getString("course_id"), 
							Connector.getGrades(rsr.getString("course_id"), rsr.getString("id")), rsr.getString("semester"), rsr.getString("dept"), rsr.getFloat("passed"),
							rsr.getFloat("examined"), rsr.getString("school"));
					courses.add(newCourse);
				if(schoolLabel.getText().equals(newCourse.getSchool())) {
					gradeTableModel.addRow(new Object[] {newCourse.getcName(), Connector.getGrades(newCourse.getCid(), newCourse.getId()), 
							newCourse.getSemester(), newCourse.getId()});
				}
			}
			Connector.close(rsr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Form table models get filled
		 */
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
			Connector.close(rsr);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		 * form table created and filled 
		 */
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
		
		/*
		 * Statistics panel is filled
		 */
		for(Course c: courses) {
			if(schoolLabel.getText().equals(c.getSchool())) {
				statsTableModel.addRow(new Object[] {c.getcName(), c.getSemester(), User.getUserName(users, c.getcProfessor())});
			}
		}
		
		gradeTable = new JTable(gradeTableModel) {
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
		/*
		 * Requests panel gets filled
		 */
		rsr = Connector.getRequests();
		try {
			while(rsr.next()) {
				requestTableModel.addRow(new Object[] {rsr.getString("id"), rsr.getString("request1"), rsr.getString("request2")});
			}
			Connector.close(rsr);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		requestTable = new JTable(requestTableModel);
		
		scrollPane = new JScrollPane(gradeTable);
		scrollPane.setBounds(10, 11, 665, 402);
		gradePanel.add(scrollPane);
		
		textField = new JTextField("Προσθέστε το βαθμό προς αλλαγή");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(222, 424, 232, 27);
		gradePanel.add(textField);
		
		textField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			    textField.setText(""); 
			}
			public void focusLost(FocusEvent e) {
			}
		});
		/*
		 * news Panel table filled and hard-wired to change upon button press
		 */
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gradeTableModel.setValueAt(textField.getText(), gradeTable.getSelectedRow(), 1);
				Connector.updateGrade(courses, gradeTable.getValueAt(gradeTable.getSelectedRow(), 1).toString(), gradeTable.getValueAt(gradeTable.getSelectedRow(), 3).toString(), 
						gradeTable.getValueAt(gradeTable.getSelectedRow(), 0).toString());
			}
		});

		newsTable = new JTable(Connector.getNews(schoolLabel.getText(), schoolLabel, usernameLabel, courses, users, newsTableModel)) {
			public String getToolTipText(MouseEvent e) {
		        String tip = null;
		        java.awt.Point p = e.getPoint();
		        int rowIndex = rowAtPoint(p);
		        int colIndex = columnAtPoint(p);
		        int realColumnIndex = convertColumnIndexToModel(colIndex);
		        tip = (String) newsTableModel.getValueAt(rowIndex, colIndex);
		        		
		        return tip;
			}
			
		};
		newsTableModel.addRow(new Object[] {"Νέα Προσθήκη", "Νέα Προσθήκη", User.getUserName(users, usernameLabel.getText()), "Νέα Προσθήκη"});
		postButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cID = newsTable.getValueAt(newsTable.getSelectedRow(), 0).toString();
				String title = newsTable.getValueAt(newsTable.getSelectedRow(), 1).toString();
				String text = newsTable.getValueAt(newsTable.getSelectedRow(), 3).toString();
				String school = schoolLabel.getText();
				String id = usernameLabel.getText();
				Connector.updateNews(cID, school, text, title, id, newsTableModel, users, usernameLabel);
			}
		});
		/*
		 * Planner table created and filled
		 */
		DefaultTableModel plannerTableModel = new DefaultTableModel(new Object[] {"Όνομα Γεγονότος", "Ημερομηνία", "Ώρα", "Περιγραφή"}, 0);
		plannerTable = new JTable(Connector.getPlans(usernameLabel.getText(), plannerTableModel)) {
			public String getToolTipText(MouseEvent e) {
		        String tip = null;
		        java.awt.Point p = e.getPoint();
		        int rowIndex = rowAtPoint(p);
		        int colIndex = columnAtPoint(p);
		        int realColumnIndex = convertColumnIndexToModel(colIndex);
		        tip = (String) newsTableModel.getValueAt(rowIndex, colIndex);
		        		
		        return tip;
			}
			
		};
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connector.setPlans(plannerTable.getValueAt(plannerTable.getSelectedRow(), 0).toString(), plannerTable.getValueAt(plannerTable.getSelectedRow(), 1).toString(), 
						plannerTable.getValueAt(plannerTable.getSelectedRow(), 2).toString(), plannerTable.getValueAt(plannerTable.getSelectedRow(), 3).toString(),
						Connector.getPlanFree(), plannerTableModel);
			}
		});
		/*
		 * More buttons and panels get created
		 */
		editButton.setBounds(222, 462, 232, 27);
		gradePanel.add(editButton);
		
		scrollPane_5 = new JScrollPane(plannerTable);
		scrollPane_5.setBounds(10, 11, 665, 440);
		plannerPanel.add(scrollPane_5);
		
		scrollPane_4 = new JScrollPane(newsTable);
		scrollPane_4.setBounds(10, 11, 665, 440);
		newsPanel.add(scrollPane_4);
		
		requestPanel.setBackground(Color.GRAY);
		layeredPane.add(requestPanel, "name_38762156049700");
		requestPanel.setLayout(null);
		
		scrollPane_2 = new JScrollPane(requestTable);
		scrollPane_2.setBounds(10, 11, 665, 478);
		requestPanel.add(scrollPane_2);
		
		formPanel.setBackground(Color.GRAY);
		layeredPane.add(formPanel, "name_38765711160400");
		formPanel.setLayout(null);
		
		scrollPane_3 = new JScrollPane(formTable);
		scrollPane_3.setBounds(10, 11, 665, 478);
		formPanel.add(scrollPane_3);
		
		statsPanel.setBackground(Color.GRAY);
		layeredPane.add(statsPanel, "name_26293092121600");
		statsPanel.setLayout(null);
		
		scrollPane_1 = new JScrollPane(statsTable);
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
		
		chatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(chatPanel);
			}
		});
		chatButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chatButton.setBounds(10, 150, 145, 23);
		optionsPanel.add(chatButton);
		plannerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(plannerPanel);
			}
		});
		plannerButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		plannerButton.setBounds(10, 200, 145, 23);
		optionsPanel.add(plannerButton);
		

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
	
	public static void setSchoolLabelText(String text) {
		schoolLabel.setText(text);
    }
}