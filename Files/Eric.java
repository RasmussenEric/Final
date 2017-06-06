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
import java.awt.Image;
import java.awt.Color;

public class Eric extends JFrame
{
	
	private JTextField userInput;
	private JTextArea chatArea;
	private ObjectOutputStream output;
	private ObjectOutputStream outputPictures;
	private ObjectInputStream input;
	private ObjectInputStream inputPictures;
	private ServerSocket serverPort;
	private Socket connection;
	private PictureBox reciever;
	private BufferedImage img;
	
	//create text box, setsize and setvisible
	//listen for actions
	public Eric()
	{
	
		super("AP Compsci Messenger");
		userInput = new JTextField();
		userInput.setEditable(false);
		userInput.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					sendMessage(event.getActionCommand());
					userInput.setText("");
				}
			}
		);
		
		//adds a border the size of userInput
		add(userInput, BorderLayout.SOUTH);
		//makes chat box, sets pixels, makes it scrollable
		chatArea = new JTextArea();
		add(new JScrollPane(chatArea));
		setSize(600,300);
		//Color mix = new Color(150, 60, 255);
		//chatArea.setBackground(mix);
		setVisible(true);
	
	
	}
	
	
	
	public void Start()
	{
		
		//left number is port, right is # of people on server
		try
		{
			
			serverPort = new ServerSocket(7777, 10);
			while(true)
			{
				try
				{
			
			
					connectToServer();
					setupStreams();
					whileChatting();
			
				}
				catch(EOFException end)
				{
					showMessage("\nServer was closed.");
				}
				finally
				{
					closeProgram();
				}
			}
			
		}
		catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
		
	}
	
	//waits for a connection attempt
	public void connectToServer() throws IOException
	{
		
		showMessage("Waiting for a connection...");
		//.accept waits for a connection to be given, makes new socket
		connection = serverPort.accept();
		//gets IP and converts it to a string
		showMessage("\nConnecting to " + connection.getInetAddress().getHostName());
		
	}
	
	//establishes streams
	public void setupStreams() throws IOException
	{
		
		//establishes streams using ports/sockets given
		//input takes in what they say, output gives what we say
		
		output = new ObjectOutputStream(connection.getOutputStream());
		outputPictures = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		outputPictures.flush();
		input = new ObjectInputStream(connection.getInputStream());
		inputPictures = new ObjectInputStream(connection.getInputStream());
		showMessage("Setup Complete.");
		
	}
	
	//need method for connection while typing
	public void whileChatting() throws IOException
	{
		//tests streams
		userInput.setEditable(true);
		String message = "\n Test";
		sendMessage(message);
		
		do
		{
			try
			{
				/*
				if(input.readObject().getClass().equals(BufferedImage.class))
				{
					//try and accept PictureBox object
					try
					{		
						
						BufferedImage img = (BufferedImage) ImageIO.read(ImageIO.createImageInputStream(input));

						PictureBox reciever = new PictureBox(img);
						
					}
					catch(Exception e)
					{
						System.out.println("Well that didn't work now did it.");
					}
				}
				else
				{
					try
					{
						message = (String) input.readObject();
						showMessage("\n" + message);
					}
					catch(ClassNotFoundException classNotfoundException)
					{
						showMessage("\n CAN'T DISPLAY MESSAGE");
					}
				}
				*/
				
				
				
				
				
				try
				{
					message = (String) input.readObject();
				}
				catch(EOFException e)
				{
					System.out.println("Not String");
				}
				catch(ClassCastException e)
				{
					//img = (BufferedImage) ImageIO.read(ImageIO.createImageInputStream(inputPictures));
					message = "placeholder";
				}
				catch(OptionalDataException e)
				{
					System.out.println("Still not string");
					message = "placeholder";
				}
				
				if(message.indexOf("sendpicture") == -1 & !message.equals("placeholder"))
				{
					try
					{
						message = (String) input.readObject();
						showMessage("\n" + message);
					}
					catch(Exception e)
					{
						System.out.println("Damn.");
					}
					
				}
				else
				{
					
					img = (BufferedImage) ImageIO.read(ImageIO.createImageInputStream(input));
					reciever = new PictureBox(img);
					
				}

				
				
				
				
			}
			catch(ClassNotFoundException classNotFoundException)
			{
				showMessage("Can't recieve message.");
			}
			
			
			//typing "end" will end the connection
		}while(!message.equals("Client - END"));
		
		
		
	}
	
	public void sendMessage(String message)
	{
		try
		{
			if(message.indexOf("sendpicture") != -1)
			{
				
				String[] split = message.split(" ");
				String fileName = "";
		
				for(int i = 0; i < split.length; i++)
				{
					if(split[i].indexOf(".jpg") != -1)
					{
						fileName = split[i];
				
					}
				}
			
				PictureBox test = new PictureBox(fileName);
				
				output.flush();

				File f = new File("C:\\Users\\rasmussene7186\\Desktop\\Test\\Final\\Files\\Pictures\\" + fileName);
				
				BufferedImage send = (ImageIO.read(f));
				
				ImageIO.write(send, "JPG", output);
				
				output.flush();
			}
			
			//sends message
			output.writeObject("SERVER - " + message);
			output.flush();
			showMessage("\nSERVER - " + message);
		}
		catch(IOException ioException)
		{
			chatArea.append("Can't send message.");
		}

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
	
	public void closeProgram()
	{
		showMessage("\n Closing connections... \n");
		userInput.setEditable(false);
		try
		{
			output.close();
			input.close();
			connection.close();
		}
		catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
	}
	
}