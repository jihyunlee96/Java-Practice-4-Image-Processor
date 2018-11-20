import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Slider_Brightness extends JSlider implements ChangeListener {
	
	static final int MIN = -250;
	static final int MAX = 250;
	static final int INIT = 0;
	
	int factor = 0;
		
	// slider for brightness
	public Slider_Brightness (int x, int y)
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

		Main.frame.image.draw_image(change_brightness(factor), false);		
	}
	
	class MyMouseListener extends MouseAdapter implements MouseMotionListener
	{			
		public void mouseReleased (MouseEvent e) {			
			Main.frame.image.change_currentImg(change_brightness(factor));
		}
	}
	
	// returns an instance of BufferedImage with changed brightness
	public Integer[][][] change_brightness (int factor)
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
								
				int r = init_pixels[i][j][0] + factor;
				int g = init_pixels[i][j][1] + factor;
				int b = init_pixels[i][j][2] + factor;
				
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
