import javax.swing.JFrame;

public class ClientTest
{
	public static void main(String[]args)
	{
		FinalClient y;
		y = new FinalClient("127.0.0.1");
		y.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		y.startRunning();
	}
}