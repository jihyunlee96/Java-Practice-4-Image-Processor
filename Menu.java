import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Color;
import java.awt.FileDialog;

import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;


public class Menu extends JMenuBar implements ActionListener {	
	
	public Menu () {
		super();		
				
		JMenu file = new JMenu("File");
		this.add(file);
		
		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(this);
		file.add(load);
		
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(this);
		file.add(save);
		
		
		JMenu edit = new JMenu("Edit");
		this.add(edit);
		
		JMenuItem undo = new JMenuItem("Undo");
		undo.addActionListener(this);
		edit.add(undo);
		
		JMenuItem redo = new JMenuItem("Redo");
		redo.addActionListener(this);
		edit.add(redo);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == "Load") 
		{
			BufferedImage img = load_image();
			
			Main.frame.image.load_image(img);
		}
		
		else if (e.getActionCommand() == "Save")
		{
			save_image (Main.frame.image.current_img);
		}
		
		else if (e.getActionCommand() == "Undo")
		{			
			Main.frame.image.draw_image(Main.frame.image.history.get(--Main.frame.image.history_index), false);
		}
		
		else if (e.getActionCommand() == "Redo")
		{
			int index = Main.frame.image.history_index + 1;
			Main.frame.image.history_index += 1;
			
			Main.frame.image.draw_image(Main.frame.image.history.get(index), false);
		}
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
		}
		
		return img;
	}
	
	public void save_image (BufferedImage img)
	{
		FileDialog dialog = new FileDialog(Main.frame, "Save File", FileDialog.SAVE);
		dialog.setVisible(true);
		
		String path = dialog.getDirectory() + dialog.getFile();
		
		
		try {
			ImageIO.write(img, "png", new File(path));
		}

		catch(Exception e) { 
			System.out.println("Exception!");
		}
	}
}
