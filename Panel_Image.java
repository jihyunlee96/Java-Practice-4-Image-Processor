import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

public class Panel_Image extends JPanel {
	
	int width;
	int height;
	
	int print_width;
	int print_height;
	
	Integer[][][] init_pixels;
	public Integer[][][] current_pixels;
	
	BufferedImage current_img;
	
	Vector<BufferedImage> history = new Vector<BufferedImage>();
	int history_index = 0;
	
	Point mouse;
			
	// magnifying glass mode
	public boolean magnifying_mod = false;
	public double mag_factor = 0;
	
	// blur mode
	public boolean blur_mod = false;
	public int blur_factor = 0;
			
	public Panel_Image ()
	{
		setLayout(null);
		
		setBackground(new Color(25, 25, 45));
		
		MyMouseListener ml = new MyMouseListener();
		
		addMouseListener(ml);
		addMouseMotionListener(ml);
	}
	
	class MyMouseListener extends MouseAdapter implements MouseMotionListener
	{	
		public void mousePressed (MouseEvent e) {
			
			// default mode
			if (init_pixels != null && magnifying_mod == false && blur_mod == false)
				draw_image(init_pixels, false);
			
			// magnifying glass mode
			else if (magnifying_mod == true)
			{
				draw_image(Main.frame.control.mag.magnified(current_pixels, e.getPoint(), mag_factor), false);
			}
			
			// blur mode
			else if (blur_mod == true)
			{
				change_currentImg(Main.frame.control.blur.blur_img(current_pixels, e.getPoint(), blur_factor));
			}
		}
		
		public void mouseReleased (MouseEvent e) {
			
			// default mode
			if (init_pixels != null && blur_mod == false)
				draw_image(current_pixels, false);
		}
		
		public void mouseDragged (MouseEvent e) {
			
			// magnifying glass mode
			if (magnifying_mod == true && blur_mod == false)
			{
				draw_image(Main.frame.control.mag.magnified(current_pixels, e.getPoint(), mag_factor), false);
			}
		}
		
		public void mouseMoved (MouseEvent e) {
			
			// blur mode
			if (blur_mod == true)
			{
				draw_image(Main.frame.control.blur.blur_box(current_pixels, e.getPoint(), blur_factor), false);
			}
		}
	}

	public void load_image(BufferedImage img)
	{									
		// load image and save it as Int[][][] init_pixels
		// also, copy it to current_pixels
		if (img != null) {			
			width = img.getWidth();
			height = img.getHeight();
			
			init_pixels = new Integer[width][height][3];
			current_pixels = new Integer[width][height][3];
			
			for (int i = 0; i < width; i ++) {
				for (int j = 0; j < height; j ++) {
					
					Color color = new Color(img.getRGB(i, j));
					
					int r = color.getRed();
					int g = color.getGreen();
					int b = color.getBlue();
					
					init_pixels[i][j][0] = r;
					init_pixels[i][j][1] = g;
					init_pixels[i][j][2] = b;
					
					current_pixels[i][j][0] = r;
					current_pixels[i][j][1] = g;
					current_pixels[i][j][2] = b;
				}
			}		
			
			// draw initial image with its pixels
			draw_image(init_pixels, true);
		}
	}
	
	public void change_currentImg(Integer[][][] pixels)
	{	
		current_pixels = pixels;
		
		draw_image(current_pixels, true);
	}
	
	public void draw_image(Integer[][][] pixels, boolean set_history)
	{					
		int set_width = 800;
		int set_height = 600;
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < width; i ++) {
			for (int j = 0; j < height; j ++) {
				
				// when creating buffered image to draw, adjust r, g, b values
				int r = adjust(pixels[i][j][0]);
				int g = adjust(pixels[i][j][1]);
				int b = adjust(pixels[i][j][2]);
				
				image.setRGB(i, j, new Color(r, g, b).getRGB());				
			}
		}
		
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);		
		
		double width_ratio = (double)set_width / width;
		double height_ratio = (double)set_height / height;
		
		if(width_ratio >= height_ratio) {
			print_width = (int)(width * height_ratio);
			print_height = (int)(height * height_ratio);
		}
		else {
			print_width = (int)(width * width_ratio);
			print_height = (int)(height * width_ratio);
		}				
		
		g2.drawImage (image, 20, 30, print_width, print_height, null);

		current_img = image;
		
		if (set_history) {
			if(history_index >= history.size() - 1) {
				history.add(current_img);
				history_index ++;
			}
			else {
				history.set(++ history_index, current_img);
			}
		}
		
		g2.dispose();
	}
	
	// overload
	public void draw_image(BufferedImage img, boolean set_history)
	{					
		int set_width = 800;
		int set_height = 600;
		
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);		
		
		double width_ratio = (double)set_width / width;
		double height_ratio = (double)set_height / height;
		
		if(width_ratio >= height_ratio) {
			print_width = (int)(width * height_ratio);
			print_height = (int)(height * height_ratio);
		}
		else {
			print_width = (int)(width * width_ratio);
			print_height = (int)(height * width_ratio);
		}				
		
		g2.drawImage (img, 20, 30, print_width, print_height, null);

		current_img = img;
		
		g2.dispose();
	}
	
	public int adjust (int n)
	{
		if (n < 0)
			n = 0;
		
		else if (n >= 256)
			n = 255;
		
		return n;
	}
	
	public void set_magMode (boolean b)
	{
		magnifying_mod = b;
		
		if (magnifying_mod == true) {			
			Main.frame.control.mg.setForeground(Color.RED);
		}
		
		else {
			Main.frame.control.mg.setForeground(Color.WHITE);
		}		
	}
	
	public void set_blurMode (boolean b)
	{
		blur_mod = b;
		
		if (blur_mod == true) {			
			Main.frame.control.bl.setForeground(Color.RED);
		}
		
		else {
			Main.frame.control.bl.setForeground(Color.WHITE);
		}		
	}
}