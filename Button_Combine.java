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

public class Button_Combine extends JButton implements ActionListener {
	
	BufferedImage img1;
	BufferedImage img2;
		
	public Button_Combine(int x, int y)
	{
		super("Merge");
		
		setSize(145, 27);
		setLocation(x, y);
				
		setFont(new Font("Monaco", Font.BOLD, 10));
		addActionListener(this);
	}
	
	public void actionPerformed (ActionEvent e)
	{
		boolean isHorizontal = false;
		int ratio1 = 0;
		int ratio2 = 0;
		
		// horizontal / vertical option set
		String input1 = JOptionPane.showInputDialog("Horizontal or Vertical?");
		
		if (input1.equalsIgnoreCase("Horizontal"))
			isHorizontal = true;			
				
		// ratio set
		String input2 = JOptionPane.showInputDialog("Input a ratio (ex. 1:1, 2:3...)");
		
		String[] str = input2.split(":");
		ratio1 = Integer.parseInt(str[0]);
		ratio2 = Integer.parseInt(str[1]);
		
		// load two images to combine
		img1 = Main.frame.image.current_img;
		img2 = load_image();
		
		// calls a method to combine two images with chosen options
		if (isHorizontal)
			Main.frame.image.change_currentImg(horizontal_combine(img1, img2, ratio1, ratio2));

		else
			Main.frame.image.change_currentImg(vertical_combine(img1, img2, ratio1, ratio2));
	}
	
	public void parse_ratio(String input, int ratio1, int ratio2)
	{

	}
	
	public BufferedImage load_image()
	{
		FileDialog dialog = new FileDialog(Main.frame, "Load File", FileDialog.LOAD);
		dialog.setVisible(true);
		
		String path = dialog.getDirectory() + dialog.getFile();
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File(path));
		}

		catch(Exception e) { 
			System.out.println("Exception!");
			System.exit(0);
		}
		
		return img;
	}
	
	public Integer[][][] horizontal_combine(BufferedImage img1, BufferedImage img2, int ratio1, int ratio2)
	{		
		// reads initial image's width and height
		int width = img1.getWidth();
		int height = img1.getHeight();
		
		int turning_point = (ratio1 * height) / (ratio1 + ratio2);
		
		// create Integer[][][] for new pixels
		Integer[][][] new_pixels = new Integer[width][height][3];
				
		for (int i = 0; i < width; i ++) {
			for (int j = 0; j < height; j ++) {
							
				Color color1 = new Color(img1.getRGB(i, j));
				Color color2 = new Color(img2.getRGB(i, j));
				
				int r1 = color1.getRed();
				int g1 = color1.getGreen();
				int b1 = color1.getBlue();
				
				int r2 = color2.getRed();
				int g2 = color2.getGreen();
				int b2 = color2.getBlue();
				
				// 터닝 포인트 지점으로부터의 거리
				int factor = turning_point - j;
				
				// 터닝 포인트 지점에서 50픽셀 이하 떨어져 있다면
				if (-100 < factor && factor < 100)
				{
					double factor_1 = 0;
					double factor_2 = 0;
					
					// 터닝 포인트 왼쪽에 있다면
					if (j <= turning_point) {
						factor_1 = (double)(50 - factor/2) / 100;
						factor_2 = (double)(50 + factor/2) / 100;
					}
					
					// 터닝 포인트 오른쪽에 있다면
					else {
						factor_1 = (double)(50 + factor/2) / 100;
						factor_2 = (double)(50 - factor/2) / 100;
					}
					
					new_pixels[i][j][0] = (int)((r1 * factor_1) + (r2 * factor_2));
					new_pixels[i][j][1] = (int)((g1 * factor_1) + (g2 * factor_2));
					new_pixels[i][j][2] = (int)((b1 * factor_1) + (b2 * factor_2));
				}
				
				// 터닝 포인트 왼쪽에 있다면
				else if (j <= turning_point) {
					new_pixels[i][j][0] = r1;
					new_pixels[i][j][1] = g1;
					new_pixels[i][j][2] = b1;
				}
				
				// 터닝 포인트 오른쪽에 있다면
				else {
					new_pixels[i][j][0] = r2;
					new_pixels[i][j][1] = g2;
					new_pixels[i][j][2] = b2;
				}
			}
		}
						
		return new_pixels;
	}
	
	public Integer[][][] vertical_combine(BufferedImage img1, BufferedImage img2, int ratio1, int ratio2)
	{		
		// reads initial image's width and height
		int width = img1.getWidth();
		int height = img1.getHeight();
		
		int turning_point = (ratio1 * width) / (ratio1 + ratio2);
		
		// create Integer[][][] for new pixels
		Integer[][][] new_pixels = new Integer[width][height][3];
				
		for (int i = 0; i < width; i ++) {
			for (int j = 0; j < height; j ++) {
							
				Color color1 = new Color(img1.getRGB(i, j));
				Color color2 = new Color(img2.getRGB(i, j));
				
				int r1 = color1.getRed();
				int g1 = color1.getGreen();
				int b1 = color1.getBlue();
				
				int r2 = color2.getRed();
				int g2 = color2.getGreen();
				int b2 = color2.getBlue();
				
				// 터닝 포인트 지점으로부터의 거리
				int factor = turning_point - i;
				
				// 터닝 포인트 지점에서 50픽셀 이하 떨어져 있다면
				if (-100 < factor && factor < 100)
				{
					double factor_1 = 0;
					double factor_2 = 0;
					
					// 터닝 포인트 왼쪽에 있다면
					if (i <= turning_point) {
						factor_1 = (double)(50 - factor/2) / 100;
						factor_2 = (double)(50 + factor/2) / 100;
					}
					
					// 터닝 포인트 오른쪽에 있다면
					else {
						factor_1 = (double)(50 + factor/2) / 100;
						factor_2 = (double)(50 - factor/2) / 100;
					}
					
					new_pixels[i][j][0] = (int)((r1 * factor_1) + (r2 * factor_2));
					new_pixels[i][j][1] = (int)((g1 * factor_1) + (g2 * factor_2));
					new_pixels[i][j][2] = (int)((b1 * factor_1) + (b2 * factor_2));
				}
				
				// 터닝 포인트 왼쪽에 있다면
				else if (i <= turning_point) {
					new_pixels[i][j][0] = r1;
					new_pixels[i][j][1] = g1;
					new_pixels[i][j][2] = b1;
				}
				
				// 터닝 포인트 오른쪽에 있다면
				else {
					new_pixels[i][j][0] = r2;
					new_pixels[i][j][1] = g2;
					new_pixels[i][j][2] = b2;
				}
			}
		}
						
		return new_pixels;
	}
			
}