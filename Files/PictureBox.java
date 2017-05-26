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
import java.awt.Color;

public class PictureBox extends JComponent
{

	private JFrame frame;
	private BufferedImage image;
	//private Image image;
	private JButton b1;
	private JLabel l1;
	//private Eric test2;
	private String fileName = "";
	
	public PictureBox(String fileName) 
	{
		
		frame = new JFrame();
		
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Pictures");
		frame.setSize(400, 400);
		frame.setVisible(true);
		
		JLabel background = new JLabel(new ImageIcon("C:\\Users\\rasmussene7186\\Desktop\\Test\\Final\\Files\\Pictures\\" + fileName));

		frame.add(background);

		background.setLayout(new FlowLayout());

		l1 = new JLabel("Here is a button");
		b1 = new JButton("I am a button");
			
		b1.addActionListener(
		new ActionListener()
		{
			
			public void actionPerformed(ActionEvent event)
			{
				
				System.out.println("Refreshing");
				
			}
			
			
		}
		);
		
		background.add(l1);
		background.add(b1);
		
		//frame.getContentPane().setBackground(Color.red);

		//test2 = new Eric();
		
		
	}
	
	//public PictureBox(Image image)
	//{
	//	this.image = image;
	//}
	
	public void sendPicture(String message) throws IOException
	{
		
		String[] split = message.split(" ");
		
		for(int i = 0; i < split.length; i++)
		{
			if(split[i].indexOf(".jpg") != -1)
			{
				fileName = split[i];
				
				/*
				try
				{
					loadPicture(fileName);
				}
				catch(IOException e)
				{
					System.out.println("Can't call loadPicture");
				}
				*/
			}
		}
		
	}
	
	/*
	public void loadPicture(String fileName) throws IOException
	{
	
		try
		{
			
			File f = new File("C:\\Users\\rasmussene7186\\Desktop\\Test\\Final\\Files\\Pictures\\" + fileName);
			
			image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
			
			image = ImageIO.read(f);
			
			System.out.println("Loaded Correctly");
			
			
			
			//frame.setContentPane((image));
			
			//Graphics g = image.getGraphics();
			
			//paintComponent(g, image);
			
			//frame.setBackground(Color.yellow);
			
			//g.drawImage(image, 0, 0, null);
			
			//frame.getContentPane().setBackground(Color.red);
			
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
	
	*/
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	
	
	
	
	
	
}