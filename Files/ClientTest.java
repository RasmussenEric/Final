import javax.swing.JFrame;

public class ClientTest
{
	public static void main(String[]args)
	{
		FinalClient y;
		y = new FinalClient("localhost");
		y.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		y.startRunning();
	}
}