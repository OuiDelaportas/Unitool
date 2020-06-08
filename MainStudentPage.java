import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mindfusion.common.DateTime;
import com.mindfusion.common.Duration;
import com.mindfusion.scheduling.CalendarAdapter;
import com.mindfusion.scheduling.CalendarView;
import com.mindfusion.scheduling.CustomDrawElements;
import com.mindfusion.scheduling.DrawEvent;
import com.mindfusion.scheduling.GroupType;
import com.mindfusion.scheduling.ItemConfirmEvent;
import com.mindfusion.scheduling.ThemeType;
import com.mindfusion.scheduling.awt.AwtCalendar;

public class MainStudentPage extends JFrame {
	
	
	private JLayeredPane layeredPane = new JLayeredPane();
	private static JLabel usernameLabel = new JLabel();
	private static JLabel schoolLabel = new JLabel();
	private static JLabel semesterLabel = new JLabel();
	private JPanel optionsPanel = new JPanel();
	private JPanel infoPanel = new JPanel();
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JScrollPane scrollPane_4;
	private JButton courseButton = new JButton("Τα μαθήματα μου");
	private JButton statsButton = new JButton("Στατιστικά");
	private JButton gradeButton = new JButton("Βαθμολογίες");
	private JButton selectionButton = new JButton("\u0394\u03B7\u03BB\u03CE\u03C3\u03B5\u03B9\u03C2");
	private JButton requestButton = new JButton("\u0391\u03B9\u03C4\u03AE\u03C3\u03B5\u03B9\u03C2");
	private JButton logoutButton = new JButton("\u0391\u03C0\u03BF\u03C3\u03CD\u03BD\u03B4\u03B5\u03C3\u03B7");
	private JPanel mainpagePanel = new JPanel();
	private JButton cButton = new JButton("Πιστοποιητικό Αναλυτικής Βαθμολογίας");
	private JButton gButton = new JButton("Πιστοποιητικό Σπουδών");
	private JLabel lblNewLabel = new JLabel("Λεπτομέρειες - Σχόλια");
	private JButton formButton = new JButton("Επιβεβαίωση Δήλωσης");
	private JButton chatButton = new JButton("Chat");
	private JButton plannerButton = new JButton("Planner");
	private JButton newsButton = new JButton("News");
	private JPanel formPanel = new JPanel();
	private JPanel requestPanel = new JPanel();
	private JPanel gradePanel = new JPanel();
	private JPanel coursePanel = new JPanel();
	private JPanel plannerPanel = new JPanel();
	private JPanel chatPanel = new JPanel();
	private JPanel newsPanel = new JPanel();
	private JPanel statsPanel = new JPanel();
	private JTextArea textArea = new JTextArea();
	private Course newCourse = null;
	private ArrayList <Course> courses = new ArrayList();
	private JTable gradeTable = null;
	private JTable courseTable = null;
	private JTable formTable = null;
	private JTable statsTable = null;
	private JTable newsTable = null;
	private ResultSet rsr;
	private User newUser = null;
	private ArrayList <User> users = new ArrayList();
	private ArrayList <String> courseSelected = new ArrayList();
	private ArrayList <String> text = new ArrayList();
	
	/**
	 * Create the frame.
	 */
	public MainStudentPage() {
		Connector.GetInfo("stud");
		
		setBounds(new Rectangle(0, 0, 1030, 540));
		getContentPane().setLayout(null);
		
		infoPanel.setBackground(Color.DARK_GRAY);
		infoPanel.setBounds(0, 0, 165, 500);
		getContentPane().add(infoPanel);
		infoPanel.setLayout(null);
		
		courseButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		courseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(coursePanel);
			}
		});
		courseButton.setBounds(10, 150, 145, 23);
		infoPanel.add(courseButton);
		
		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(statsPanel);
			}
		});
		statsButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statsButton.setBounds(10, 200, 145, 23);
		infoPanel.add(statsButton);
		/*
		 * The grade and course list is fetched.
		 */
		DefaultTableModel gradeTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος", "Βαθμός Μαθήματος", "Εξάμηνο"}, 0);
		DefaultTableModel courseTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος",  "Εξάμηνο"}, 0);
		DefaultTableModel newsTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος", "Τίτλος", "Διδάσκων", "Μήνυμα"}, 0);
		rsr = Connector.getCourses();
		try {
			while(rsr.next()) {
				if(rsr.getString("school").equals(schoolLabel.getText())) {
					newCourse = new Course(rsr.getString("course_name"), rsr.getString("id"), rsr.getString("prof_id"), rsr.getString("course_id"), 
							Connector.getGrades(rsr.getString("course_id"), rsr.getString("id")), rsr.getString("semester"), rsr.getString("dept"), rsr.getFloat("passed"),
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
		/*
		 * grade and course table made non-editable
		 */
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
		/*
		 * user list filled
		 */
		users = Connector.getUsers(newUser, users);	
		/*
		 * Statistics table and form table get filled
		 */
		DefaultTableModel statsTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος", "Εξάμηνο", "Διδάσκων"}, 0);
		DefaultTableModel formTableModel = new DefaultTableModel(new Object[] {"Όνομα Μαθήματος", "Εξάμηνο", "Διδάσκων"}, 0);
		/*
		 * Form table filled
		 */
		for(Course c: courses) {
			statsTableModel.addRow(new Object[] {c.getcName(), c.getSemester(), User.getUserName(users, c.getcProfessor())});
			if(c.getId().equals(usernameLabel.getText()) && c.getSemester().compareTo(semesterLabel.getText())<=0) {
				formTableModel.addRow(new Object[] {c.getcName(), c.getSemester(), User.getUserName(users, c.getcProfessor())});
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
		
		
		
		/*
		 * Stats Table filled and made non-editable
		 */
		
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
		 * News Table gets filled 
		 */
		newsTable = new JTable(Connector.getNews(schoolLabel.getText(), schoolLabel, usernameLabel, courses, users, newsTableModel)) {
			public boolean isCellEditable(int row,int column){
				return false;
				}
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
		
		
		/*
		 * Buttons, panels and labels customized
		 */
		gradeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(gradePanel);
			}
		});
		gradeButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		gradeButton.setBounds(10, 250, 145, 23);
		infoPanel.add(gradeButton);
		
		selectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(formPanel);
			}
		});
		selectionButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		selectionButton.setBounds(10, 300, 145, 23);
		infoPanel.add(selectionButton);
		
		requestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(requestPanel);
			}
		});
		requestButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		requestButton.setBounds(10, 350, 145, 23);
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
		
		mainpagePanel.setBounds(165, 0, 685, 500);
		getContentPane().add(mainpagePanel);
		mainpagePanel.setLayout(new CardLayout(0, 0));
		
		mainpagePanel.add(layeredPane, "name_23958904944300");
		layeredPane.setLayout(new CardLayout(0, 0));
		
		newsPanel.setBackground(Color.GRAY);
		layeredPane.add(newsPanel, "name_23978823487000");
		newsPanel.setLayout(null);
		
		scrollPane_4 = new JScrollPane(newsTable);
		scrollPane_4.setBounds(10, 11, 665, 478);
		newsPanel.add(scrollPane_4);
		
		chatPanel.setBackground(Color.GRAY);
		layeredPane.add(chatPanel, "name_24018466741200");

		plannerPanel.setBackground(Color.GRAY);
		layeredPane.add(plannerPanel, "name_24020787422800");
		plannerPanel.setLayout(null);
		
		coursePanel.setBackground(Color.GRAY);
		layeredPane.add(coursePanel, "name_39396003979000");
		coursePanel.setLayout(null);
		
		scrollPane_1 = new JScrollPane(courseTable);
		scrollPane_1.setBounds(10, 11, 665, 478);
		coursePanel.add(scrollPane_1);
		
		gradePanel.setBackground(Color.GRAY);
		layeredPane.add(gradePanel, "name_38718138169000");
		gradePanel.setLayout(null);
		
		scrollPane = new JScrollPane(gradeTable);
		scrollPane.setBounds(10, 11, 665, 478);
		gradePanel.add(scrollPane);
		requestPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		requestPanel.setBackground(Color.GRAY);
		layeredPane.add(requestPanel, "name_38762156049700");
		requestPanel.setLayout(null);
		
		textArea.setBounds(10, 363, 665, 126);
		requestPanel.add(textArea);
		
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
		
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(185, 310, 250, 35);
		requestPanel.add(lblNewLabel);
		
		formPanel.setBackground(Color.GRAY);
		layeredPane.add(formPanel, "name_38765711160400");
		formPanel.setLayout(null);
		
		scrollPane_2 = new JScrollPane(formTable);
		scrollPane_2.setBounds(10, 11, 665, 440);
		formPanel.add(scrollPane_2);
		
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
		
		scrollPane_3 = new JScrollPane(statsTable);
		scrollPane_3.setBounds(10, 11, 665, 478);
		statsPanel.add(scrollPane_3);
		
		optionsPanel.setBackground(Color.DARK_GRAY);
		optionsPanel.setBounds(850, 0, 165, 500);
		getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);
		
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
		
		
		//Calendar gets Created
		 
		AwtCalendar calendar = new AwtCalendar();
		calendar.setBounds(0, 0, 685, 500);
        calendar.beginInit();
        calendar.setCurrentView(CalendarView.Timetable);
        calendar.setTheme(ThemeType.Light);
        calendar.setCustomDraw(CustomDrawElements.TimetableItem);
        
        calendar.addCalendarListener(new CalendarAdapter() {
             public void itemCreating(ItemConfirmEvent e) {
                 onCalendarItemCreating(e);
             }

         });
        //Connector.getPlanner(calendar);

        for (int i = 1; i < 7; i++)
        	calendar.getTimetableSettings().getDates().add(DateTime.today().addDays(i));

        calendar.getTimetableSettings().setItemOffset(30);
        calendar.getTimetableSettings().setShowItemSpans(false);
        calendar.getTimetableSettings().setSnapInterval(Duration.fromMinutes(1));
        calendar.getTimetableSettings().setVisibleColumns(7);
        calendar.endInit();
        plannerPanel.add(calendar);
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
	
	public boolean isCellEditable(int row, int column) {
	       return false;
	}
	protected void onCalendarItemCreating(ItemConfirmEvent e) {
	       DateTime start = e.getItem().getStartTime();
	       DateTime end = e.getItem().getEndTime();
	}   
}
