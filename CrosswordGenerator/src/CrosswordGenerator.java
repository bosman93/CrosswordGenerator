import graphics.MainFrame;

import java.awt.EventQueue;


public class CrosswordGenerator {
	public static void main(String[] args)
	{		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame();
			}
		});
	}
}
