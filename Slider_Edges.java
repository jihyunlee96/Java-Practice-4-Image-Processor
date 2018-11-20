import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Slider_Edges extends JSlider implements ChangeListener {
	
	static final int MIN = 0;
	static final int MAX = 40;
	static final int INIT = 0;
	
	int factor = 0;
		
	// slider for edges
	public Slider_Edges (int x, int y)
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

		Main.frame.image.draw_image(change_edges(factor), true);		
	}
	
	class MyMouseListener extends MouseAdapter implements MouseMotionListener
	{			
		public void mouseReleased (MouseEvent e) {			
			Main.frame.image.change_currentImg(change_edges(factor));
		}
	}
	
	public Integer[][][] change_edges (int factor)
	{						
		// brings initial image's pixels
		Integer[][][] init_pixels = Main.frame.image.current_pixels;
		
		boolean edge = false;
		boolean semi_edge = false;
		
		int intensity_threshold = 100;
		int semi_intensity_threshold = 70;
		
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
					else if (Math.abs(leftIntensity - rightIntensity) > semi_intensity_threshold)
						semi_edge = true;
					
					// if big difference in intensity (up - down), detect it as edge
					if (edge == false) {
						Integer[] upPixel = init_pixels[row][col];
						Integer[] downPixel = init_pixels[row+1][col];
						
						int upIntensity = upPixel[0] + upPixel[1] + upPixel[2];
						int downIntensity = downPixel[0] + downPixel[1] + downPixel[2];
						
						if (Math.abs(upIntensity - downIntensity) > intensity_threshold)
							edge = true;
						else if (Math.abs(upIntensity - downIntensity) > semi_intensity_threshold)
							semi_edge = true;
					}
				}
				
				if (edge) {
					new_pixels[row][col][0] = init_pixels[row][col][0] - factor;
					new_pixels[row][col][1] = init_pixels[row][col][1] - factor;
					new_pixels[row][col][2] = init_pixels[row][col][2] - factor;
				}
				
				else if (semi_edge) {
					new_pixels[row][col][0] = init_pixels[row][col][0] - (int)(factor * 0.2);
					new_pixels[row][col][1] = init_pixels[row][col][1] - (int)(factor * 0.2);
					new_pixels[row][col][2] = init_pixels[row][col][2] - (int)(factor * 0.2);
				}
				
				else {
					new_pixels[row][col][0] = init_pixels[row][col][0] + factor;
					new_pixels[row][col][1] = init_pixels[row][col][1] + factor;
					new_pixels[row][col][2] = init_pixels[row][col][2] + factor;
				}
			}
		}
				
		return new_pixels;
	}
	
	public void init_value()
	{
		setValue(INIT);
	}
}
