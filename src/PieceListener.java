import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class PieceListener implements KeyListener {

	private Piece currPiece;
	Screen parent;
	public PieceListener(Piece piece, Screen parent){
		currPiece = piece;
		this.parent = parent;
	}

	public void setPiece(Piece p){
		System.out.println("setting new piece ");
		currPiece = p;
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			currPiece.incX(-1);
			parent.update();
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			currPiece.incX(1);
			parent.update();
		}
		else if(e.getKeyCode() == KeyEvent.VK_A){
			currPiece.flip(true);
			parent.update();
		}
		else if(e.getKeyCode() == KeyEvent.VK_D){
			currPiece.flip(false);
			parent.update();
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			currPiece.incDown();
			parent.update();
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE){
			parent.instantDrop();
			parent.tick();
			parent.update();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
