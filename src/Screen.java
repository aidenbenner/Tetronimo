import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Screen extends JPanel { 
	final int SCREEN_WIDTH = 520;
	final int SCREEN_HEIGHT = 500;

	final static int GAME_ROWS = 20;
	final static int GAME_COLS = 10;

	boolean[][] gameGrid = new boolean[GAME_ROWS][GAME_COLS];
	Color[][] colorMap = new Color[GAME_ROWS][GAME_COLS];
	Piece currPiece = new Piece(this, Piece.BlockType.L_BLOCK, PIECE_SPAWN_X,PIECE_SPAWN_Y);
	PieceListener pieceListener = new PieceListener(currPiece, this);

	String score = "0"; 
	public void setScore(String s){
		score = s;
	}
	
	public String getScore(){
		return score;
	}

	LinkedList<Piece> pieceQueue = new LinkedList<Piece>();
	
	
	public Screen(){
		this.setFocusable(true);
		this.addKeyListener(pieceListener);
		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setOpaque(true);
		this.setBackground(Color.BLACK);
			
		for(int i = 0; i<3; i++){
			pieceQueue.add(new Piece(this,Piece.getRandomPiece(), PIECE_SPAWN_X, PIECE_SPAWN_Y));
		}
		
		
		for(int i = 0; i < gameGrid.length; i++){
			gameGrid[i] = new boolean[GAME_COLS];
			Arrays.fill(gameGrid[i], false);
		}
	}
	
	final int GRID_WIDTH  = 20;
	final int GRID_HEIGHT = 20;
	final int BOX_WIDTH = GRID_WIDTH * GAME_ROWS;
	final int BOX_HEIGHT  = GRID_HEIGHT *  GAME_COLS;	

	final int BOX_MARGIN  = 20; 
	final int BOX_X = (SCREEN_WIDTH - ( BOX_HEIGHT / 2)) / 2;
	final int BOX_Y = BOX_MARGIN;

	
	public void paintToScreen(Graphics g, int row, int col){
		int CurrY = BOX_Y + GRID_HEIGHT * row; 
		int CurrX = BOX_X + GRID_WIDTH * col; 
		g.fillRect(CurrX, CurrY , GRID_WIDTH, GRID_HEIGHT);
	}
	

	public void update(){
		repaint();
	}

	public boolean checkLoss(){
		if(isBlocked(currPiece)){
			for(Block b : currPiece.blocks){
				if(b.getY() <=  0){
					return true;
				}
			}
		}
		return false;
	}
	
	
	final static Color BG_COLOUR = Color.BLACK;
	public void paint(Graphics g){
		super.paintComponent(g);
		g.setColor(currPiece.getColor());
		for(Block b : currPiece.blocks){
			int currX = b.getX();
			int currY = b.getY();
			paintToScreen(g,currY,currX);
		}
		g.setColor(Color.BLACK);
		
		//for each row 
		for(int i = 0; i<gameGrid.length; i++){
			//for each column
			for(int k = 0; k<gameGrid[i].length; k++){
				int CurrX = BOX_X + GRID_WIDTH * k; 
				int CurrY = BOX_Y + GRID_HEIGHT * i; 
				if(gameGrid[i][k] == true){
					g.setColor(colorMap[i][k]);
					g.fillRect(CurrX , CurrY , GRID_WIDTH, GRID_HEIGHT );
				}
			}
		}
	    g.setColor(Color.WHITE);
		for(int i = 0; i<gameGrid.length; i++){
			//for each column
			for(int k = 0; k<gameGrid[i].length; k++){
				int CurrX = BOX_X + GRID_WIDTH * k; 
				int CurrY = BOX_Y + GRID_HEIGHT * i; 
				g.drawRect(CurrX , CurrY , GRID_WIDTH, GRID_HEIGHT );
			}
		}
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString("Score: " + score, BOX_X - 190, BOX_Y + 50);
		
		//draw preview
		Piece p = Piece.deepClone(currPiece);
		instantDrop(p);
		g.setColor(new Color(100,100,100));
		for(Block b : p.blocks){
			int currX = b.getX();
			int currY = b.getY();
			paintToScreen(g,currY,currX);
		}
		
		//draw piece queue
		int i = 0;
		for(Piece nextP : pieceQueue){
			g.setColor(nextP.color);
			for(Block b : nextP.blocks){
				paintToScreen(g, 4 * i + 1 + b.getY(), GAME_COLS + -2 + b.getX());
			}
			i++;
		}
		
		
		
	}

	public boolean checkValid(Piece p){
		//check each block and see if it overlaps with the map
		for(Block b : p.blocks){
			if(b.getX() >= GAME_COLS || b.getX() < 0){
				return false;
			}
			if(b.getY() >= GAME_ROWS || b.getY() < 0){
				return false;
			}
			
			if(gameGrid[b.getY()][b.getX()]){
				return false;
			}

		}
		return true;
	}

	public void instantDrop(){
		instantDrop(currPiece);
	}
	
	
	public void instantDrop(Piece p){	
		//lower currpiece till bot
		while(!isBlocked(p)){
			p.incDown();
		}
	}
	
	static final int PIECE_SPAWN_X = GAME_COLS / 2 - 2;
	static final int PIECE_SPAWN_Y = 0;
	public void tick(){
		if(isBlocked(currPiece)){
			if( checkLoss() ){
				clearBoard();
				setScore("0");
			}
			else{
				addPieceToGrid(currPiece);
				int lines = removeFilledLines();
				lines = lines * lines * 10;
				lines += Integer.parseInt(score) ;
				setScore(lines + "");
				pieceQueue.add(new Piece(this, Piece.getRandomPiece(), PIECE_SPAWN_X , PIECE_SPAWN_Y));
				currPiece = pieceQueue.pop();
				pieceListener.setPiece(currPiece);
			}
		}
		else{
			//lower current piece
			currPiece.incDown();

		}
		update();
	}

	public void clearBoard(){
		for(int i = 0; i<gameGrid.length; i++ ){
			for(int k = 0; k<gameGrid[i].length; k++){
				gameGrid[i][k] = false;
			}
		}
	}
	
	public boolean getGridLoco(int row, int col){
		return gameGrid[row][col];
	}
	
	
	public boolean isBlocked(Piece p){
		for(Block b : p.blocks){
			if(b.getY() >= GAME_ROWS - 1){
				return true;
			}
			if(getGridLoco(b.getY() + 1, b.getX())){
				return true;
			}
		}
		return false;
	}
	
	public int removeFilledLines(){
		int removedLines = 0;

		for(int i = 0; i<gameGrid.length; i++){
			boolean full = true;
			for(int k = 0; k<gameGrid[i].length; k++){
				if(!gameGrid[i][k]){
					full = false;
					continue;
				}
			}
			if(full){
				for(int k = 0; k<gameGrid[i].length; k++){
					gameGrid[i][k] = false;
				}
				removedLines++;
				shiftLines(i);
			}
		}
		return removedLines; 
	}
	

	public void shiftLines(int start ){
		for(int i = start; i>0; i--){
			for(int k = 0; k<gameGrid[i].length; k++ ){
				if(!gameGrid[i][k] && gameGrid[i - 1][k]){
					gameGrid[i][k] = true;
					gameGrid[i - 1][k] = false;
					colorMap[i][k] = colorMap[i - 1][k];
				}
			}
		}
	}
	
	
	public void addPieceToGrid(Piece p ){
		for(Block b : p.blocks){
			gameGrid[b.getY()][b.getX()] = true;
			colorMap[b.getY()][b.getX()] = p.getColor();
		}
	}
	
}
