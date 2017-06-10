import javax.swing.JFrame;

public class ClientTest
{
	public static void main(String[]args)
	{
		EricFinalClient y;
		y = new EricFinalClient("localhost");
		y.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		y.startRunning();
	}
}