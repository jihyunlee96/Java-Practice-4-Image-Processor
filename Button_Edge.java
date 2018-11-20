import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button_Edge extends JButton implements ActionListener {
	
	boolean button_on = false;
	
	public Button_Edge(int x, int y)
	{
		super("Edge Detection");
		
		setSize(145, 27);
		setLocation(x, y);
		
		setFont(new Font("Monaco", Font.BOLD, 10));
		addActionListener(this);
	}
	
	public void actionPerformed (ActionEvent e)
	{
		button_on = !button_on;
		
		if (button_on) {
			this.setForeground(Color.RED);
			Main.frame.image.draw_image(detect_edge(), false);
		}
		
		else {
			this.setForeground(Color.BLACK);
			Main.frame.image.draw_image(Main.frame.image.current_pixels, false);
		}
	}
	
	// method for edge detection
	public Integer[][][] detect_edge ()
	{						
		// brings initial image's pixels
		Integer[][][] init_pixels = Main.frame.image.init_pixels;
		
		boolean edge = false;
		
		int intensity_threshold = 50;
		
		if (init_pixels == null) return null;
		
		// reads initial image's width and height
		int width = init_pixels.length;
		int height = init_pixels[0].length;
		
		// create Integer[][][] for new pixels
		Integer[][][] new_pixels = new Integer[width][height][3];
				
		for (int row = 0; row < width; row ++) {
			for (int col = 0; col < height; col ++) {
				
				edge = false;
				
				if (col < height - 1 && row < width - 1) {
					Integer[] leftPixel = init_pixels[row][col];
					Integer[] rightPixel = init_pixels[row][col+1];
					
					// if big difference in intensity (left - right), detect it as edge
					int leftIntensity = leftPixel[0] + leftPixel[1] + leftPixel[2];
					int rightIntensity = rightPixel[0] + rightPixel[1] + rightPixel[2];
					
					if (Math.abs(leftIntensity - rightIntensity) > intensity_threshold)
						edge = true;
					
					// if big difference in intensity (up - down), detect it as edge
					if (edge == false) {
						Integer[] upPixel = init_pixels[row][col];
						Integer[] downPixel = init_pixels[row+1][col];
						
						int upIntensity = upPixel[0] + upPixel[1] + upPixel[2];
						int downIntensity = downPixel[0] + downPixel[1] + downPixel[2];
						
						if (Math.abs(upIntensity - downIntensity) > intensity_threshold)
							edge = true;
					}
				}
				
				if (edge) {
					new_pixels[row][col][0] = 0;
					new_pixels[row][col][1] = 0;
					new_pixels[row][col][2] = 0;
				}
				
				else {
					new_pixels[row][col][0] = 255;
					new_pixels[row][col][1] = 255;
					new_pixels[row][col][2] = 255;	
				}
			}
		}
				
		return new_pixels;
	}
}
