import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Slider_Magnifying extends JSlider implements ChangeListener {
	
	static final int MIN = 100;
	static final int MAX = 300;
	static final int INIT = 100;
	
	int factor = 0;
	
	// slider for magnifying glass
	public Slider_Magnifying (int x, int y)
	{
		super(JSlider.VERTICAL, MIN, MAX, INIT);
		
		// set location and size
		setLocation(x, y);
		setSize(30, 140);
		
		// set tick marks
		setMajorTickSpacing(10);
		setMinorTickSpacing(2);
		
		addChangeListener(this);
		
		setVisible(true);
	}
	
	public void stateChanged (ChangeEvent e)
	{	
		factor = this.getValue();
		Main.frame.image.mag_factor = (double) factor / 100;
		
		if (factor > 100) {
			if (Main.frame.image.magnifying_mod == false) {
				Main.frame.image.set_magMode(true);
			}
		}
		
		else {
			Main.frame.image.set_magMode(false);		
		}
	}
	
	public Integer[][][] magnified (Integer[][][] current_pixels, Point mousePoint, double factor)
	{	
		// reads current image's width and height
		int width = current_pixels.length;
		int height = current_pixels[0].length;
		
		int frame_radius = 100;
				
		int mouse_x = (int)(mousePoint.getX() * 1.169);
		int mouse_y = (int)(mousePoint.getY() * 1.173);
		
		
		// to prevent magnifying glass to go out of photo
		if (mouse_x < frame_radius)  mouse_x = frame_radius;
		if (mouse_x > (width - frame_radius)) mouse_x =  width - frame_radius;
		
		if (mouse_y < frame_radius)  mouse_y = frame_radius;
		if (mouse_y > (height - frame_radius)) mouse_y =  height - frame_radius;

		
		// create Integer[][][] for new pixels
		Integer[][][] new_pixels = new Integer[width][height][3];
		
		
		for (int i = 0; i < width; i ++) {
			for (int j = 0; j < height; j ++) {
								
				int r = 0;
				int g = 0;
				int b = 0;
				
				// draw magnifying glass
				if ((int)Math.abs(mouse_x - i) <= frame_radius && (int)Math.abs(mouse_y - j) <= frame_radius)
				{
					// draw magnified area
					if ((int)Math.abs(mouse_x - i) <= frame_radius - 6 && (int)Math.abs(mouse_y - j) <= frame_radius - 6) {
						r = current_pixels[(int)((i + mouse_x) / factor)][(int)((j + mouse_y) / factor)][0];
						g = current_pixels[(int)((i + mouse_x) / factor)][(int)((j + mouse_y) / factor)][1];
						b = current_pixels[(int)((i + mouse_x) / factor)][(int)((j + mouse_y) / factor)][2];
					}
					
					else {
						// draw frame of magnifying glass
						r = 215;
						g = 215;
						b = 235;
					}
				}
				
				else {
					r = current_pixels[i][j][0];
					g = current_pixels[i][j][1];
					b = current_pixels[i][j][2];
				}
				
				new_pixels[i][j][0] = r;
				new_pixels[i][j][1] = g;
				new_pixels[i][j][2] = b;
			}
		}
		
		return new_pixels;
	}
	
	public void init_value()
	{
		setValue(INIT);
	}
}