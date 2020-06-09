import java.awt.*; 
import javax.swing.*;
import java.awt.event.*;

public class Login extends JFrame{

	private JFrame frame;
	private JPanel panel = new JPanel();
	private JTextField userField;
	private JPasswordField passField;
	private JLabel userLabel;
	private JLabel lblNewLabel;
	private JButton loginButton = new JButton("Login");

	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setSize(425,425);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public Login() {
		frame = new JFrame("Welcome to Unitool - Login");
		frame.setVisible(true);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
	    int windowWidth = 400;
	    frame.setBounds(center.x - windowWidth / 2, center.y / 2, 430, 361);
	    
	    
		panel.setBackground(Color.DARK_GRAY);
		frame.setContentPane(panel);
		
		userField = new JTextField();
		userField.setBounds(132, 85, 148, 23);
		userField.addActionListener(action);
		panel.setLayout(null);
		userField.setHorizontalAlignment(SwingConstants.CENTER);
		userField.setForeground(Color.WHITE);
		userField.setBackground(Color.BLACK);
		panel.add(userField);
		userField.setColumns(10);
		
		passField = new JPasswordField();
		passField.setBounds(132, 155, 148, 23);
		passField.addActionListener(action);
		passField.setHorizontalAlignment(SwingConstants.CENTER);
		passField.setBackground(Color.BLACK);
		passField.setForeground(Color.WHITE);
		panel.add(passField);
		
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		loginButton.setBounds(162, 193, 89, 23);
		loginButton.addActionListener(action);
		
		
		loginButton.setBackground(Color.BLACK);
		loginButton.setOpaque(true);
		loginButton.setForeground(Color.WHITE);
		panel.add(loginButton);
		
		userLabel = new JLabel("Username");
		userLabel.setForeground(Color.WHITE);
		userLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setBounds(162, 60, 89, 14);
		panel.add(userLabel);
		
		lblNewLabel = new JLabel("Password");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(162, 130, 89, 14);
		panel.add(lblNewLabel);
	}
	
	final public Action action = new AbstractAction() {
        public void actionPerformed(ActionEvent e)
        {
        	/**
			 * The credentials get checked
			 */	
			String uname = userField.getText();
			String pass = new String (passField.getPassword());
			Connector CM = new Connector("root", "Vrady0ma0", "jdbc:mysql://localhost:3306/", "uni_db");
			try {
				if(!CM.connectToDB()) {
					CM.connectToDB();
				}
				String userType = "";
				if(CM.checkCredentials(uname, pass, userType).equals("stud")) {            
					//JOptionPane.showMessageDialog(null, "Congratulations on logging in!");
					/**
					 * The main student panel becomes visible
					 */
					frame.setVisible(false);
					MainStudentPage studPage = new MainStudentPage();
					studPage.setTitle("Unitool");
					studPage.setVisible(true);
					studPage.addWindowListener(new WindowAdapter()
					{
					    @Override
					    public void windowClosing(WindowEvent e)
					    {
					        super.windowClosing(e);
					        Connector.close();
					    }
					});
				}else if(CM.checkCredentials(uname, pass, userType).equals("prof")) {
					/**
					 * MPAINEI PANEL GIA PROFESSORIA
					 */
					frame.setVisible(false);
					MainProfessorPage profPage = new MainProfessorPage();
					profPage.setTitle("Unitool");
					profPage.setVisible(true);
					profPage.addWindowListener(new WindowAdapter()
					{
					    @Override
					    public void windowClosing(WindowEvent e)
					    {
					        super.windowClosing(e);
					        Connector.close();
					    }
					});
				}else if(CM.checkCredentials(uname, pass, userType).equals("sec")) {
					/**
					 * MPAINEI PANEL GIA GRAMMATEIA
					 */
					frame.setVisible(false);
					MainSecretaryPage secPage = new MainSecretaryPage();
					secPage.setTitle("Unitool");
					secPage.setVisible(true);
					secPage.addWindowListener(new WindowAdapter()
					{
					    @Override
					    public void windowClosing(WindowEvent e)
					    {
					        super.windowClosing(e);
					        Connector.close();
					    }
					});
				}else if(userType.equals("")){
					JOptionPane.showMessageDialog(null, "Error on login!\nPlease try again!");
				}
					
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
        }
    };
}