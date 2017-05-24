import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.geom.*;
import java.io.File;
import java.awt.Graphics;
import java.io.FileReader;

public class PictureBox extends JPanel
{

	private JFrame frame;

	
	public PictureBox(String name)
	{
		
		frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(name);
		frame.setSize(400, 400);
		frame.setVisible(true);
		//frame.add(new PictureBox(name));

	}
	
	public void sendPicture(String message) throws IOException
	{
		
		String[] split = message.split(" ");
		
		for(int i = 0; i < split.length; i++)
		{
			if(split[i].indexOf(".jpg") != -1)
			{
				String fileName = split[i];
				
				try
				{
					loadPicture(fileName);
				}
				catch(IOException e)
				{
					System.out.println("Can't call loadPicture");
				}
			}
		}
		
	
		
	}
	
	public void loadPicture(String fileName) throws IOException
	{
	
		try
		{
			
			File f = new File("C:\\Users\\rasmussene7186\\Desktop\\Test\\Final\\Files\\Pictures\\snake.jpg");
			
			BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
			
			image = ImageIO.read(f);
			
			System.out.println("Loaded Correctly");
			
			Graphics g = image.getGraphics();
			
			paintComponent(g, image);
			
			//g.drawImage(image, 0, 0, null);
			
			//JPanel pane = new JPanel();
			//{
			//	Graphics g = image.getGraphics();
			//	g.drawImage(image, 0, 0, null);
			//}
			//frame.add(pane);
			
			
		}
		catch(IOException ioException)
		{
			ioException.printStackTrace();
			System.out.println("Couldn't load picture.");
		}

		
		//Graphics g = image.getGraphics();
		//super.paintComponent(g);
		//g.drawImage(image, 0, 0, null);
		
	}
	
	public void paintComponent(Graphics g, BufferedImage image)
	{
		
		g.drawImage(image, 0, 0, null);
		repaint();
		
	}
	
}