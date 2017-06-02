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


public class EricFinalClient extends JFrame
{
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	private PictureBox reciever;
	private BufferedImage img;
	private int counter = 0;
	
	//constructor
	public EricFinalClient(String host)
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
				if(counter == 0)
				{
					message = (String) input.readObject();
				}
				
				if(message.indexOf("sendpicture") == -1)
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
					
					message = "Picture Sent";
					
					counter = 1;
				}
			
			
			
			
			}
			catch(Exception e)
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
	private void sendMessage(String message)
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
			output.writeObject("CLIENT - " + message);
			output.flush();
			showMessage("\nCLIENT - " + message);
		}
		catch(IOException ioException)
		{
			chatWindow.append("Can't send message.");
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
	
	
	
	
	
	
	
	
	
	
	
	
	