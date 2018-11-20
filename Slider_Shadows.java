import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Slider_Shadows extends JSlider implements ChangeListener {
	
	static final int MIN = -30;
	static final int MAX = 30;
	static final int INIT = 0;
	
	int factor = 0;
		
	// slider for shadows
	public Slider_Shadows (int x, int y)
	{
		super(JSlider.HORIZONTAL, MIN, MAX, INIT);
		
		// set location and size
		setLocation(x, y);
		setSize(140, 30);
		
		// set tick marks
		setMajorTickSpacing(10);
		setMinorTickSpacing(2);
		
		addChangeListener(this);
		
		MyMouseListener ml = new MyMouseListener();
		
		addMouseListener(ml);
		addMouseMotionListener(ml);
		
		setVisible(true);
	}
	
	public void stateChanged (ChangeEvent e)
	{	
		factor = this.getValue();

		Main.frame.image.draw_image(change_shadows(factor), false);		
	}
	
	class MyMouseListener extends MouseAdapter implements MouseMotionListener
	{			
		public void mouseReleased (MouseEvent e) {			
			Main.frame.image.change_currentImg(change_shadows(factor));
		}
	}
	
	// returns an instance of BufferedImage with changed brightness
	public Integer[][][] change_shadows (int factor)
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
								
				int r = init_pixels[i][j][0];
				int g = init_pixels[i][j][1];
				int b = init_pixels[i][j][2];
				
				// if the pixel is 'dark', add factor
				if (r + g + b < 200) {
					r += factor;
					g += factor;
					b += factor;
				}
				
				else if (r + g + b < 300) {
					r += factor * 0.75;
					g += factor * 0.75;
					b += factor * 0.75;
				}
				
				else if (r + g + b < 400) {
					r += factor * 0.5;
					g += factor * 0.5;
					b += factor * 0.5;
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
