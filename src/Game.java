import javax.swing.JFrame;


public class Game {
	public static void main(String args[]) throws InterruptedException{
		Screen scr = new Screen();
		scr.setVisible(true);
		
		
		JFrame gameFrame = new JFrame();
		gameFrame.setSize(scr.getWidth(), scr.getHeight());
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.add(scr);
		gameFrame.setVisible(true);
		scr.repaint();
		
		while(true){
			scr.tick();
			Thread.sleep(500);
		}
		
		
	}
}