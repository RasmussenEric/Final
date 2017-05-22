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

public class PictureBox extends JFrame
{

	private JTextArea chatArea;
	
	public PictureBox(String name)
	{
		
		super(name);
		setSize(400, 400);
		setVisible(true);
		
		chatArea = new JTextArea(400, 400);
		add(new JScrollPane(chatArea));
		
	}
	
	public void Start()
	{
		
		
		
		//while(true)
		//{
			
			//Waiting();
			
		//}

		
	}
	
	public void Waiting()
	{
		showMessage("Waiting...");
	}
	
	public void sendPicture(String message)
	{
		
		String[] split = message.split(" ");
		
		for(int i = 0; i < split.length; i++)
		{
			if(split[i].indexOf(".jpg") != -1)
			{
				String fileName = split[i];
				loadPicture(fileName);
			}
		}
		
	
		
	}
	
	public void loadPicture(String fileName)
	{	
		BufferedImage image = null;
	
		try
		{
			image = ImageIO.read(new File(fileName));
			System.out.println("Loaded Correctly")
		}
		catch(IOException ioException)
		{
			showMessage("Couldn't load picture.");
		}
		
		//or use this code
		
		//try
		//{
		//	URL url = new URL("C:/Users/rasmussene7186/Desktop/Test/Final/Files/Pictures/" + fileName);
		//	image = ImageIO.read(url);
		//	
		//}
		//catch(IOException ioException)
		//{
		//	showMessage("Couldn't load picture.");
		//}
		
		//Graphics g = image.getGraphics();
		//g.drawImage(image, 0, 0, null);
		//Graphics.drawImage(image, 0, 0, null);
		
		//Graphics2D graphics2d = bufferedImage.createGraphics();
		//graphics2d.drawImage(image,0,0,null);
	}
	
	
	public void showMessage(String message)
	{
		SwingUtilities.invokeLater(
			new Runnable()
			{
				public void run()
				{
					chatArea.append(message);
				}
			}
		);
		
	}
	
	
}