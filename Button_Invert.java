import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Button_Invert extends JButton implements ActionListener {
	
	boolean is_on = false;
		
	public Button_Invert(int x, int y)
	{
		super("Invert");
		
		setSize(145, 27);
		setLocation(x, y);
				
		setFont(new Font("Monaco", Font.BOLD, 10));
		addActionListener(this);
	}
	
	public void actionPerformed (ActionEvent e)
	{
		is_on = !is_on;
		
		if(is_on) {
			Main.frame.image.draw_image(invert(), false);
			setForeground(Color.RED);
		}
		else {
			Main.frame.image.change_currentImg(Main.frame.image.current_pixels);
			setForeground(Color.BLACK);
		}
	}
	
	public Integer[][][] invert()
	{
		// brings initial image's pixels
		Integer[][][] init_pixels = Main.frame.image.current_pixels;
				
		if (init_pixels == null) return null;
		
		// reads initial image's width and height
		int width = init_pixels.length;
		int height = init_pixels[0].length;
		
		// create Integer[][][] for new pixels
		Integer[][][] new_pixels = new Integer[width][height][3];
				
		for (int i = 0; i < width; i ++) {
			for (int j = 0; j < height; j ++) {
								
				int r = 275 - init_pixels[i][j][0];
				int g = 275 - init_pixels[i][j][1];
				int b = 275 - init_pixels[i][j][2];
				
				new_pixels[i][j][0] = r;
				new_pixels[i][j][1] = g;
				new_pixels[i][j][2] = b;
			}
		}
						
		return new_pixels;
	}
}