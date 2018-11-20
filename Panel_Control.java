import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Panel_Control extends JPanel {

	Slider_Brightness brightness;
	Slider_Contrast contrast;
	Slider_Saturation sat;
	
	Slider_Highlights highlights;
	Slider_Shadows shadows;
	Slider_Edges edges;
	
	Slider_Magnifying mag;
	Slider_Blur blur;
	
	Button_Edge edge;
	Button_Revert revert;
	Button_Combine combine;
	Button_Paper paper;
	Button_Paper2 paper2;
	Button_Invert invert;
	
	My_Label mg;
	My_Label bl;
	
	public Panel_Control()
	{
		// set size / location for panel
		setSize(250, 700);
		setLocation(850, 0);
		
		setBackground(new Color(75, 75, 95));
		setLayout(null);
		
		
		// add slider and label for brightness adjustment
		My_Label br = new My_Label("Brightness", 25, 20);
		add(br);
			
		brightness = new Slider_Brightness(80, 20);
		add(brightness);
		
		
		// add slider and label for contrast adjustment
		My_Label ct = new My_Label("Contrast", 25, 55);
		add(ct);
			
		contrast = new Slider_Contrast(80, 55);
		add(contrast);
		
		
		// add slider and label for Saturation adjustment
		My_Label s = new My_Label("Saturation", 25, 90);
		add(s);
			
		sat = new Slider_Saturation(80, 90);
		add(sat);
		
		
		// add slider and label for highlights adjustment
		My_Label hl = new My_Label("Highlights", 25, 125);
		add(hl);
		
		highlights = new Slider_Highlights(80, 125);
		add(highlights);
		
		
		// add slider and label for shadows adjustment
		My_Label sd = new My_Label("Shadows", 25, 160);
		add(sd);
		
		
		shadows = new Slider_Shadows(80, 160);
		add(shadows);
		
		
		// add slider and label for edges adjustment
		My_Label eg = new My_Label("Edges", 25, 195);
		add(eg);
		
		edges = new Slider_Edges(80, 195);
		add(edges);

		
		// add button for edge detection
		edge = new Button_Edge(45, 250);
		add(edge);
		
		
		// add button for combining two photos
		combine = new Button_Combine(45, 290);
		add(combine);
		
		
		// add button for setting paper
		paper = new Button_Paper(45, 330);
		add(paper);
		
		paper2 = new Button_Paper2(117, 330);
		add(paper2);
		
		
		// add button for inverting
		invert = new Button_Invert(45, 370);
		add(invert);
		
		
		// add button for reverting to original
		revert = new Button_Revert(45, 410);
		add(revert);
		
		
		// add slider and label for "magnify"
		mg = new My_Label("Magnify", 50, 590);
		add(mg);
		
		mag = new Slider_Magnifying(60, 450);
		add(mag);
		
		
		// add slider and label for "blur"
		bl = new My_Label("Blur", 140, 590);
		add(bl);
		
		blur = new Slider_Blur(150, 450);
		add(blur);
		
		
		setVisible(true);
	}
	
	
	// Inner class for labels
	class My_Label extends JLabel
	{
		public My_Label(String str, int x, int y)
		{
			super(str);
			
			setLocation(x, y);
			setSize(50, 30);
			
			setBackground(new Color(75, 75, 95));
			setForeground(Color.WHITE);
			
			setFont(new Font("Monaco", Font.BOLD, 8));
			setOpaque(true);

			setHorizontalAlignment(SwingConstants.CENTER);
		}
	}
}
