import javax.swing.JFrame;

public class ClientTest
{
	public static void main(String[]args)
	{
		Client y;
		y = new Client("localhost");
		y.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		y.startRunning();
	}
}