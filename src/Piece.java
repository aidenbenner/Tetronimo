import java.awt.Color;
import java.util.Random;


public class Piece {
	
	public static enum BlockType {
		I_BLOCK, 
		J_BLOCK, 
		L_BLOCK, 
		O_BLOCK,
		S_BLOCK, 
		T_BLOCK, 
		Z_BLOCK
	};

	
	public static Piece deepClone(Piece p){
		Piece pi = new Piece(p.parent, p.type, 0,0);
		pi.blocks = Block.deepCopy(p.blocks);
		return pi;
	}
	
	Color color; 
	public Color getColor(){
		return color; 
	}
	
	Screen parent;
	BlockType type; 
	public Block[] blocks = new Block[4];

	public Piece (Screen par, BlockType t, int x, int y){
		color = Utils.getRandColor();
		parent = par;
	  type = t;  
	  if(type == BlockType.I_BLOCK){
		 blocks[0] = new Block(x,y);
		 blocks[1] = new Block(x + 1, y);
		 blocks[2] = new Block(x + 2, y);
		 blocks[3] = new Block(x + 3, y);
	  }
	  if(type == BlockType.J_BLOCK){
		 blocks[0] = new Block(x,y);
		 blocks[3] = new Block(x, y + 1);
		 blocks[1] = new Block(x + 1, y);
		 blocks[2] = new Block(x + 2, y);
	  }
	  if(type == BlockType.L_BLOCK){
		 blocks[0] = new Block(x,y);
		 blocks[1] = new Block(x + 1, y);
		 blocks[2] = new Block(x + 2, y);
		 blocks[3] = new Block(x + 2, y + 1);
	  }
	  if(type == BlockType.O_BLOCK){
		 blocks[0] = new Block(x,y);
		 blocks[3] = new Block(x, y + 1);
		 blocks[1] = new Block(x + 1, y);
		 blocks[2] = new Block(x + 1, y + 1);
	  }
	  if(type == BlockType.S_BLOCK){
		 blocks[0] = new Block(x,y);
		 blocks[1] = new Block(x + 1, y);
		 blocks[2] = new Block(x + 1, y + 1);
		 blocks[3] = new Block(x + 2, y + 1);
	  }
	  if(type == BlockType.T_BLOCK){
		 blocks[0] = new Block(x,y);
		 blocks[1] = new Block(x + 1, y);
		 blocks[2] = new Block(x + 1, y + 1);
		 blocks[3] = new Block(x + 2, y);
	  }  
	  if(type == BlockType.Z_BLOCK){
		 blocks[0] = new Block(x,y + 1);
		 blocks[1] = new Block(x + 1, y + 1);
		 blocks[2] = new Block(x + 1, y );
		 blocks[3] = new Block(x + 2, y );
	  }  
	}

	
	public void tick(){
		for(Block b : blocks){
			b.setY(b.getY() + 1);
		}
	}
	
	public void incDown(){
		Block[] revert = Block.deepCopy(blocks);
		for(Block b : blocks){
			b.setY(b.getY() + 1);
		}
		if(!parent.checkValid(this)){
			blocks = Block.deepCopy(revert);
		}
	}

	public void incX(int x){
		Block[] revert = Block.deepCopy(blocks);
		for(Block b : blocks){
			b.setX(b.getX() + x);
		}

		if(!parent.checkValid(this)){
			blocks = Block.deepCopy(revert);
		}
	}

	static Random r = new Random();
	public static BlockType getRandomPiece(){
		return BlockType.values()[r.nextInt(BlockType.values().length)];
	}
	
	
	public void flip(boolean cw){
		Block[] revert = Block.deepCopy(blocks);
		int pivot = 1;
		int pivX = blocks[pivot].getX();
		int pivY = blocks[pivot].getY();
		for(int i = 0; i<blocks.length; i++){
			int cX = blocks[i].getX(); 
			int cY = blocks[i].getY(); 
			if(cw){
				blocks[i].setX(pivX + (cY - pivY));
				blocks[i].setY(pivY - (cX - pivX));
			}
			else {
				blocks[i].setX(pivX - (cY - pivY));
				blocks[i].setY(pivY + (cX - pivX));
			}
		}
		if(!parent.checkValid(this)){
			blocks = Block.deepCopy(revert);
		}
	}
}