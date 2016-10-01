
public class Block {
	private int x; 
	private int y;
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int in){
		x = in; 
	}
	public void setY(int in){
		y = in;
	}

	public void flipXY(){
		int temp = x;
		setX(getY());
		setY(temp);
	}
	
	public static Block[] deepCopy(Block[] a){
		Block[] copy = new Block[a.length];
		int i = 0;
		for(Block b : a){
			copy[i] = new Block(b.getX(), b.getY());
			i++;
		}
		return copy;
	}
	
	public Block(int x, int y){
		setX(x);
		setY(y);
	}
}