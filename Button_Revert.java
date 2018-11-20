import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Button_Revert extends JButton implements ActionListener {
		
	public Button_Revert(int x, int y)
	{
		super("Revert to Original");
		
		setSize(145, 27);
		setLocation(x, y);
		
		setForeground(Color.RED);
		
		setFont(new Font("Monaco", Font.BOLD, 10));
		addActionListener(this);
	}
	
	public void actionPerformed (ActionEvent e)
	{
		int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION);
		
		if (reply == JOptionPane.YES_OPTION) {
			Main.frame.image.change_currentImg(Main.frame.image.init_pixels);
			Main.frame.control.brightness.init_value();
			Main.frame.control.contrast.init_value();
			Main.frame.control.sat.init_value();
			Main.frame.control.highlights.init_value();
			Main.frame.control.shadows.init_value();
			Main.frame.control.edges.init_value();
			
			Main.frame.control.mag.init_value();
			Main.frame.control.blur.init_value();
		}
	}
	
}