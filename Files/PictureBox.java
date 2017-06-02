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
import java.awt.Image;

public class PictureBox extends JComponent
{

	private JFrame frame;
	private BufferedImage image;
	private JButton b1;
	private JLabel l1;
	private String fileName = "";
	
	public PictureBox(String fileName) 
	{
		
		frame = new JFrame();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.setLayout(new BorderLayout());
		
		frame.setTitle("Server Picture");
		frame.setSize(400, 400);
		frame.setVisible(true);
		
		JLabel background = new JLabel(new ImageIcon("C:\\Users\\rasmussene7186\\Desktop\\Test\\Final\\Files\\Pictures\\" + fileName));

		frame.add(background);

		background.setLayout(new FlowLayout());

		/*
		
		l1 = new JLabel("Here is a button");
		b1 = new JButton("I am a button");
			
		b1.addActionListener(
		new ActionListener()
		{
			
			public void actionPerformed(ActionEvent event)
			{
				
				System.out.println("Refreshing");
				
				frame.add(background);
				frame.repaint();
				
			}
			
			
		}
		);
		
		background.add(l1);
		background.add(b1);

		*/
		
	}
	
	public PictureBox(BufferedImage img)
	{
		
		
		frame = new JFrame();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.setLayout(new BorderLayout());
		
		frame.setTitle("New Picture Recieved");
		frame.setSize(400, 400);
		frame.setVisible(true);
		
		JLabel background = new JLabel(new ImageIcon(img));

		frame.add(background);

		background.setLayout(new FlowLayout());
		
		/*
		
		l1 = new JLabel("Here is a button");
		b1 = new JButton("I am a button");
			
		b1.addActionListener(
		new ActionListener()
		{
			
			public void actionPerformed(ActionEvent event)
			{
				
				System.out.println("Refreshing");
				
				frame.add(background);
				frame.repaint();
				
			}
			
			
		}
		);
		
		background.add(l1);
		background.add(b1);
		
		*/
		
		
	}

	public void dispose()
	{
		frame.dispose();
	}

}