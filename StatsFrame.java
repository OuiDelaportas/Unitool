import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StatsFrame extends JFrame{
	public StatsFrame(String name, float passed, float examined) {
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 191);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel nameLabel = new JLabel("\u038C\u03BD\u03BF\u03BC\u03B1 \u039C\u03B1\u03B8\u03AE\u03BC\u03B1\u03C4\u03BF\u03C2 :");
		nameLabel.setBounds(74, 11, 124, 15);
		panel.add(nameLabel);
		
		JLabel percentLabel = new JLabel("\u03A0\u03BF\u03C3\u03BF\u03C3\u03C4\u03CC \u0395\u03C0\u03B9\u03C4\u03C5\u03C7\u03AF\u03B1\u03C2 :");
		percentLabel.setBounds(10, 90, 124, 15);
		panel.add(percentLabel);
		
		JLabel diffLabel = new JLabel("\u0392\u03B1\u03B8\u03BC\u03CC\u03C2 \u0394\u03C5\u03C3\u03BA\u03BF\u03BB\u03AF\u03B1\u03C2 :");
		diffLabel.setBounds(208, 90, 133, 15);
		panel.add(diffLabel);
		
		JLabel nameSetLabel = new JLabel("");
		nameSetLabel.setText(name);
		nameSetLabel.setBounds(192, 11, 133, 14);
		panel.add(nameSetLabel);
		
		JLabel successLabel = new JLabel("");
		float per = passed*100/examined;
		successLabel.setText(String.format("%.2f %s", per, "%"));
		successLabel.setBounds(128, 90, 75, 14);
		panel.add(successLabel);
		
		JLabel difficultyLabel = new JLabel("");
		float rPer = 100-per;
		difficultyLabel.setText(String.format("%.2f %s", rPer, "%"));
		difficultyLabel.setBounds(333, 90, 91, 14);
		panel.add(difficultyLabel);
		
		JButton okButton = new JButton("\u039F\u039A");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		okButton.setBounds(165, 142, 89, 23);
		panel.add(okButton);
	}
}
