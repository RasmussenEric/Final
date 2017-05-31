import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;


public class FinalClient extends JFrame
{
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	
	//constructor
	public FinalClient(String host)
	{
		super("Client");
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					sendMessage(event.getActionCommand());
					userText.setText("");
				}
			}
		);
		add(userText, BorderLayout.SOUTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		setSize(600, 300);
		setVisible(true);
	}
	
	public void startRunning()
	{
		try{
			connectToServer();
			setupStreams();
			whileChatting();
		}catch(EOFException eofException){
			showMessage("\n Client ended connection");
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			closeSnS();
		}
	}
	
	//connect to server
	private void connectToServer() throws IOException
	{
		showMessage("Attempting connection... \n");
		connection = new Socket(InetAddress.getByName(serverIP), 7777);
		showMessage("Connected to: " + connection.getInetAddress().getHostName());
	}
	
	//set up streams to send and receive messages
	private void setupStreams() throws IOException
	{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Connected");
	}
	
	//while chatting with server
	private void whileChatting() throws IOException
	{
		ableToType(true);
		do
		{	
			try
			{
				if(input.readObject().getClass().equals(PictureBox.class))
				{
					//try and accept PictureBox object
					try
					{		
						//PictureBox test = (PictureBox) input.readObject();
						
						BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(input));
						
						JFrame frame = new JFrame();
						
						frame.setLayout(new BorderLayout());
						frame.setTitle("Client Picture");
						frame.setSize(400, 400);
						frame.setVisible(true);
						
						JLabel background = new JLabel(new ImageIcon(img));

						frame.add(background);

						background.setLayout(new FlowLayout());
						
						
						
						
						
						
						
						
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
			}
			catch(ClassNotFoundException e)
			{
				showMessage("Ruh Roh");
			}
			
			
		}while(!message.equals("SERVER - END"));
	}
	
	//close the streams and sockets
	private void closeSnS()
	{
		showMessage("\n Closing connection...\n");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	//send messages to server
	private void sendMessage(String message){
		try{
			output.writeObject("CLIENT - " + message);
			output.flush();
			showMessage("\nCLIENT - " + message);
		}catch(IOException ioException){
			chatWindow.append("\nMESSAGE FAILED TO SEND");
		}
	}
	
	//update chatWindow
	private void showMessage(final String message)
	{
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(message);
				}
			}
		);
	}
	
	//allows user to type
	private void ableToType(final boolean tof)
	{
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					userText.setEditable(tof);
				}
			}
		);
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	