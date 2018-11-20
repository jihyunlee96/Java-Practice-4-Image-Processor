import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame_Main extends JFrame {
	
	Menu menu;
	Panel_Image image;
	Panel_Control control;
	
	public Frame_Main ()
	{
		// set title, size, location of frame
		setTitle("My Image Processor");
		setSize(1100, 700);
		setLocation(0, 0);
		
		
		// add menu bar
		menu = new Menu();
		setJMenuBar(menu);
		
		
		// add panel_image
		image = new Panel_Image();
		setContentPane(image);
		
		
		// add panel_control
		control = new Panel_Control();
		add(control);
		
		
		// set layout option, etc.
		setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setResizable(false);
		setVisible(true);
	}
}
	