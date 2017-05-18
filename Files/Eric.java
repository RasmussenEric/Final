import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.geom.*;

public class Eric extends JFrame
{
	
	private JTextField userInput;
	private JTextArea chatArea;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket serverPort;
	private Socket connection;
	
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
			
			
					Waiting();
					setupConnection();
					Typing();
			
				}
				catch(EOFException end)
				{
					Display("\nServer was closed.");
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
	public void Waiting() throws IOException
	{
		
		Display("Waiting for a connection...");
		//.accept waits for a connection to be given, makes new socket
		connection = serverPort.accept();
		//gets IP and converts it to a string
		Display("\nConnecting to " + connection.getInetAddress().getHostName());
		
	}
	
	//establishes streams
	public void setupConnection() throws IOException
	{
		
		//establishes streams using ports/sockets given
		//input takes in what they say, output gives what we say
		
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		Display("Setup Complete.");
		
	}
	
	//need method for connection while typing
	public void Typing() throws IOException
	{
		//tests streams
		userInput.setEditable(true);
		String message = "\n Test";
		sendMessage(message);
		
		do
		{
			try
			{
				//recieves object from client, turns it into a string and displays it
				message = (String) input.readObject();
				Display("\n" + message);
			}
			catch(ClassNotFoundException classNotFoundException)
			{
				Display("Can't recieve message.");
			}
			
			
			//typing "end" will end the connection
		}while(!message.equals("Client - End"));
		
		
		
	}
	
	public void sendMessage(String message)
	{
		
		//if(message.indexOf(".") != -1)
		//{
			//String lower = message.toLowerCase();
			//String[] check = message.split(".");
			//int pos = 0;
			//System.out.println(check.length);
	
			//if(check[1].equals("jpg"))
			//{
				//pos = lower.indexOf(".") + 1;
				//String ending = message.substring(pos, message.length() - 1);
				//Display(ending);
			//}	
		//}
		
		try
		{
			
			//System.out.print(message);
			
			//if(message.indexOf(".") != -1)
			//{

				//int pos = message.indexOf("jpg");
				//String ending = message.substring(pos, message.length());
				//System.out.println(ending);
				
			//}
			
			if(message.indexOf("sendpicture") != 1)
			{
				
				sendPicture(message)

			}
			else
			{
				//sends message
				output.writeObject("SERVER - " + message);
				output.flush();
				Display("\nSERVER - " + message);
				
			}
		}
		catch(IOException ioException)
		{
			chatArea.append("Can't send message.");
		}

	}
	
	public void Display(String message)
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
	
	public void sendPicture(String message)
	{
		
		String[] split = message.split(" ");
		
		for(int i = 0; i < split.length; i++)
		{
			if(split[i].indexOf(".jpg") != -1)
			{
				String fileName = split[i];
			}
		}
		
		loadPicture(fileName);
		
	}
	
	public void loadPicture(String file name)
	{
		try
		{
			BufferedImage image = new BufferedImage(new File(fileName));
		}
		catch(IOException ioException)
		{
			Display("Couldn't load picture.");
		}
		
		
		
		
	}
	
	
	
	
	public void closeProgram()
	{
		Display("\n Closing connections... \n");
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