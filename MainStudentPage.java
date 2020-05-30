import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainStudentPage extends JFrame {
	
	
	JLayeredPane layeredPane = new JLayeredPane();
	static JLabel usernameLabel = new JLabel();
	static JLabel schoolLabel = new JLabel();
	static JLabel semesterLabel = new JLabel();
	/**
	 * Create the frame.
	 */
	public MainStudentPage() {
		setBounds(new Rectangle(0, 0, 1030, 540));
		getContentPane().setLayout(null);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.DARK_GRAY);
		infoPanel.setBounds(0, 0, 165, 500);
		getContentPane().add(infoPanel);
		infoPanel.setLayout(null);
		
		JButton courseButton = new JButton("\u03A4\u03B1 \u03BC\u03B1\u03B8\u03AE\u03BC\u03B1\u03C4\u03AC \u03BC\u03BF\u03C5");
		courseButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		courseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		courseButton.setBounds(10, 150, 145, 23);
		infoPanel.add(courseButton);
		
		JButton statsButton = new JButton("\u03A3\u03C4\u03B1\u03C4\u03B9\u03C3\u03C4\u03B9\u03BA\u03AC");
		statsButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statsButton.setBounds(10, 200, 145, 23);
		infoPanel.add(statsButton);
		
		JButton gradeButton = new JButton("\u0392\u03B1\u03B8\u03BC\u03BF\u03BB\u03BF\u03B3\u03AF\u03B5\u03C2");
		gradeButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		gradeButton.setBounds(10, 250, 145, 23);
		infoPanel.add(gradeButton);
		
		JButton selectionButton = new JButton("\u0394\u03B7\u03BB\u03CE\u03C3\u03B5\u03B9\u03C2");
		selectionButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		selectionButton.setBounds(10, 300, 145, 23);
		infoPanel.add(selectionButton);
		
		JButton requestButton = new JButton("\u0391\u03B9\u03C4\u03AE\u03C3\u03B5\u03B9\u03C2");
		requestButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		requestButton.setBounds(10, 350, 145, 23);
		infoPanel.add(requestButton);
		
		JButton logoutButton = new JButton("\u0391\u03C0\u03BF\u03C3\u03CD\u03BD\u03B4\u03B5\u03C3\u03B7");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login frame = new Login();
			}
		});
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		logoutButton.setBounds(10, 466, 145, 23);
		infoPanel.add(logoutButton);
		
		Connector.GetInfo("stud");
		
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
		
		JPanel newsPanel = new JPanel();
		newsPanel.setBackground(Color.GRAY);
		layeredPane.add(newsPanel, "name_23978823487000");
		
		JPanel chatPanel = new JPanel();
		chatPanel.setBackground(Color.GRAY);
		layeredPane.add(chatPanel, "name_24018466741200");
		
		JPanel plannerPanel = new JPanel();
		plannerPanel.setBackground(Color.GRAY);
		layeredPane.add(plannerPanel, "name_24020787422800");
		
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
}
