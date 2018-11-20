import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Slider_Blur extends JSlider implements ChangeListener {
	
	static final int MIN = 0;
	static final int MAX = 15;
	static final int INIT = 0;
	
	int factor = 0;
	
	// slider for magnifying glass
	public Slider_Blur (int x, int y)
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
		Main.frame.image.blur_factor = factor;
		
		if (factor > INIT) {
			if (Main.frame.image.magnifying_mod == false) {
				Main.frame.image.set_blurMode(true);
			}
		}
		
		else {
			Main.frame.image.set_blurMode(false);		
		}
	}
	
	public Integer[][][] blur_img (Integer[][][] current_pixels, Point mousePoint, int factor)
	{	
		// reads current image's width and height
		int width = current_pixels.length;
		int height = current_pixels[0].length;
		
		int frame_radius = factor;
				
		int mouse_x = (int)(mousePoint.getX() * 1.198);
		int mouse_y = (int)(mousePoint.getY() * 1.180);
		
		
		// to prevent blur eraser to go out of photo
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
				
				// draw blurred img
				if ((int)Math.abs(mouse_x - i) <= factor - 1 && (int)Math.abs(mouse_y - j) <= factor - 1) 
				{
					for (int n = 0; n < 3; n ++) {
						r = (current_pixels[i][j][0] + current_pixels[i-1][j][0] + current_pixels[i+1][j][0] 
								+ current_pixels[i][j-1][0] + current_pixels[i][j+1][0]) / 5;
						
						g = (current_pixels[i][j][1] + current_pixels[i-1][j][1] + current_pixels[i+1][j][1] 
								+ current_pixels[i][j-1][1] + current_pixels[i][j+1][1]) / 5;
						
						b = (current_pixels[i][j][2] + current_pixels[i-1][j][2] + current_pixels[i+1][j][2] 
								+ current_pixels[i][j-1][2] + current_pixels[i][j+1][2]) / 5;
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
	
	public Integer[][][] blur_box (Integer[][][] current_pixels, Point mousePoint, int factor)
	{	
		// reads current image's width and height
		int width = current_pixels.length;
		int height = current_pixels[0].length;
		
		int frame_radius = factor;
				
		int mouse_x = (int)(mousePoint.getX() * 1.198);
		int mouse_y = (int)(mousePoint.getY() * 1.180);
				
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
				
				// draw blur eraser
				if ((int)Math.abs(mouse_x - i) <= factor && (int)Math.abs(mouse_y - j) <= factor)
				{
					// draw frame of blur eraser
					r = 215;
					g = 215;
					b = 235;						
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