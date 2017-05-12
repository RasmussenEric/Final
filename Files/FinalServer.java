import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FinalServer extends JFrame
{
	
	private JTextField userInput;
	private JTextArea chatArea;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket serverPort;
	private Socket connection;
	
	//create text box, setsize and setvisible
	//listen for actions
	public FinalServer()
	{
	
		super("AP Compsci Messenger");
		userInput = new JTextField();
		userInput.setEditable(false);
		userInput.addActionListener(
			new ActionListener()
			{
				public void actionperformed(ActionEvent event)
				{
					sendMessage(event.getActionCommand());
					userText.setText("");
				}
			}
		);
		
		//adds a border the size of userInput
		add(userInput, Borderlayout.SOUTH);
		//makes chat box, sets pixels, makes it scrollable
		chatArea = new JTextArea();
		add(JScrollPane(chatArea));
		setSize(600,300);
		setVisible(true);
	}
	
	public void Start()
	{
		//left number is port, right is # of people on server
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
				Display("Server was closed.");
			}
			finally
			{
				closeProgram();
			}
			
			
		}
		
		
	}
	
	public void Waiting() throws IOException
	{
		
		Display("Waiting for a connection...");
		//.accept waits for a connection to be given, makes new socket
		connection = serverPort.accept();
		//gets IP and converts it to a string
		Display("Connecting to " + connection.getInetAddress().getHostName());
		
	}
	
	public void setupConnection() throws IOException
	{
		
		//establishes streams using ports/sockets given
		//input takes in what they say, output gives what we say
		
		output = ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = ObjectInputStream(connection.getInputStream());
		Display("Setup Complete.");
		
	}
	
	
	
	
}