import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Button_Paper extends JButton implements ActionListener {
	
	BufferedImage img1;
	BufferedImage img2;
		
	public Button_Paper(int x, int y)
	{
		super("Paper 1");
		
		setSize(71, 27);
		setLocation(x, y);
				
		setFont(new Font("Monaco", Font.BOLD, 10));
		addActionListener(this);
	}
	
	public void actionPerformed (ActionEvent e)
	{
		// load two images to combine
		img1 = Main.frame.image.current_img;
		img2 = load_image();
		
		// calls a method to combine two images with chosen options
		Main.frame.image.change_currentImg(combine(img1, img2));
	}
	
	public BufferedImage load_image()
	{
		/*FileDialog dialog = new FileDialog(Main.frame, "Load File", FileDialog.LOAD);
		dialog.setVisible(true);
		
		String path = dialog.getDirectory() + dialog.getFile();
		System.out.println(path);*/
		
		BufferedImage img = null;
		
		String path = "/Users/jihyunlee/Downloads/KakaoTalk_Photo_2018-06-30-01-10-36_52.jpeg";
		
		try {
			img = ImageIO.read(new File(path));
		}

		catch(Exception e) { 
			System.out.println("Exception!");
			System.exit(0);
		}
		
		return img;
	}
	
	public Integer[][][] combine(BufferedImage img1, BufferedImage img2)
	{		
		// reads initial image's width and height
		int width = img1.getWidth();
		int height = img1.getHeight();
				
		// create Integer[][][] for new pixels
		Integer[][][] new_pixels = new Integer[width][height][3];
				
		for (int i = 0; i < width; i ++) {
			for (int j = 0; j < height; j ++) {
							
				Color color1 = new Color(img1.getRGB(i, j));
				Color color2 = new Color(img2.getRGB(i, j));
				
				int r1 = color1.getRed();
				int g1 = color1.getGreen();
				int b1 = color1.getBlue();
				
				int r2 = (int)(color2.getRed() * 1.5);
				int g2 = (int)(color2.getGreen() * 1.5);
				int b2 = (int)(color2.getBlue() * 1.5);
					
				new_pixels[i][j][0] = (int)(((r1 * 0.65) + (r2 * 0.35)) * 1.5 - 140);
				new_pixels[i][j][1] = (int)(((g1 * 0.65) + (g2 * 0.35)) * 1.5 - 140);
				new_pixels[i][j][2] = (int)(((b1 * 0.65) + (b2 * 0.35)) * 1.5 - 140);	
			}
		}
		return new_pixels;
	}		
}