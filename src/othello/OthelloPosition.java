package othello;
import java.util.*;
import java.lang.*;


/**
 * This class is used to represent game positions. It uses a 2-dimensional char
 * array for the board and a Boolean to keep track of which player has the move.
 * 
 * @author Henrik Bj&ouml;rklund
 */

public class OthelloPosition {

	/** For a normal Othello game, BOARD_SIZE is 8. */
	protected static final int BOARD_SIZE = 8;

	/** True if the first player (white) has the move. */
	protected boolean playerToMove;
	
	private int toBePlayed;

	/**
	 * The representation of the board. For convenience, the array actually has
	 * two columns and two rows more that the actual game board. The 'middle' is
	 * used for the board. The first index is for rows, and the second for
	 * columns. This means that for a standard 8x8 game board,
	 * <code>board[1][1]</code> represents the upper left corner,
	 * <code>board[1][8]</code> the upper right corner, <code>board[8][1]</code>
	 * the lower left corner, and <code>board[8][8]</code> the lower left
	 * corner. In the array, the charachters 'E', 'W', and 'B' are used to
	 * represent empty, white, and black board squares, respectively.
	 */
	protected char[][] board;

	public int getTurnsLeft(){
		return toBePlayed;
	}
	
	private void setToBePlayed(){
		String pos = toString();
		toBePlayed = BOARD_SIZE*BOARD_SIZE;
		for (int i = 0; i < pos.length(); i++){
			if (pos.charAt(i) == 'O' || pos.charAt(i) == 'X'){
				toBePlayed--;
			}
		}
	}
	
	public void nextTurn(){
		toBePlayed--;
	}
	
	/** Creates a new position and sets all squares to empty. */
	public OthelloPosition() {
		board = new char[BOARD_SIZE + 2][BOARD_SIZE + 2];
		for (int i = 0; i < BOARD_SIZE + 2; i++)
			for (int j = 0; j < BOARD_SIZE + 2; j++)
				board[i][j] = 'E';
		setToBePlayed();
	}

	public OthelloPosition(String s) {
		if (s.length() != 65) {
			board = new char[BOARD_SIZE + 2][BOARD_SIZE + 2];
			for (int i = 0; i < BOARD_SIZE + 2; i++)
				for (int j = 0; j < BOARD_SIZE + 2; j++)
					board[i][j] = 'E';
		} else {
			board = new char[BOARD_SIZE + 2][BOARD_SIZE + 2];
			if (s.charAt(0) == 'W') {
				playerToMove = true;
			} else {
				playerToMove = false;
			}
			for (int i = 1; i <= 64; i++) {
				char c;
				if (s.charAt(i) == 'E') {
					c = 'E';
				} else if (s.charAt(i) == 'O') {
					c = 'W';
				} else {
					c = 'B';
				}
				int column = ((i - 1) % 8) + 1;
				int row = (i - 1) / 8 + 1;
				board[row][column] = c;
			}
		}
		setToBePlayed();
		
	}

	/**
	 * Initializes the position by placing four markers in the middle of the
	 * board.
	 */
	public void initialize() {
		board[BOARD_SIZE / 2][BOARD_SIZE / 2] = board[BOARD_SIZE / 2 + 1][BOARD_SIZE / 2 + 1] = 'W';
		board[BOARD_SIZE / 2][BOARD_SIZE / 2 + 1] = board[BOARD_SIZE / 2 + 1][BOARD_SIZE / 2] = 'B';
		playerToMove = true;
		setToBePlayed();
	}

	/* getMoves and helper functions */

	/**
	 * Returns a linked list of <code>OthelloAction</code> representing all
	 * possible moves in the position. If the list is empty, there are no legal
	 * moves for the player who has the move.
	 */

	
	public LinkedList<OthelloAction> getMoves() {
		LinkedList<OthelloAction> list = new LinkedList<OthelloAction>();
		for (int i = 1; i <= BOARD_SIZE; i++){
			for (int j = 1; j <= BOARD_SIZE; j++){
				
			OthelloAction a = new OthelloAction(i, j);
			if (canMove(a)){
				list.addLast(a);
				}
			}
		}
		return list;
	}
	

	
	public boolean canMove(OthelloAction a){
		int row = a.getRow();
		int col = a.getColumn();
		return (
				directionPossible(row, col, 0, 1) ||
				directionPossible(row, col, 1, 1) ||
				directionPossible(row, col, 1, 0) ||
				directionPossible(row, col, 1, -1) ||
				directionPossible(row, col, 0, -1) ||
				directionPossible(row, col, -1, -1) ||
				directionPossible(row, col, -1, 0) ||
				directionPossible(row, col, -1, 1)	
				);
	}
	
	
	private boolean directionPossible(int row, int col, int dirrow, int dircol){
		if (board[row][col] != 'E'){
			return false;
		}
		int tmprow = row + dirrow;
		int tmpcol = col + dircol;
		char player;
		char opponent;
		if (playerToMove){
		    player = 'W';
		    opponent = 'B';
		}
		else{
			player = 'B';
		    opponent = 'W';
		}
		boolean found1 = false;
		boolean found2 = false;
		while((tmprow <= BOARD_SIZE + 1) && (tmpcol <= BOARD_SIZE + 1) 
				&& (tmprow >= 0) && (tmpcol >= 0) 
				&&  (   (!found1) || (!found2)   )        ){
			if (!found1){
				if(board[tmprow][tmpcol] == opponent){
					found1 = true;
				}
				else{
					return false;
				}
			}
			else if (!found2){
				if(board[tmprow][tmpcol] == player){
					found2 = true;
				}
				else if (board[tmprow][tmpcol] != opponent)
				{
					return false;
				}
			}
			
			tmprow+= dirrow;
			tmpcol+= dircol;
		}
		return (found1 && found2);
	}
	
	/* toMove */

	/** Returns true if the first player (white) has the move, otherwise false. */
	public boolean toMove() {
		return playerToMove;
	}

	/* makeMove and helper functions */

	/**
	 * Returns the position resulting from making the move <code>action</code>
	 * in the current position. Observe that this also changes the player to
	 * move next.
	 */
	
	
	public OthelloPosition makeMove(OthelloAction action)
			throws IllegalMoveException {

		LinkedList<OthelloAction> moves = getMoves();
		OthelloPosition clone = this.clone();
		if(moves.isEmpty() && action.isPassMove()){
			clone.playerToMove = !playerToMove;
			return clone;}
		if (moves.isEmpty() && (!action.isPassMove())){
			throw new IllegalMoveException(action);}
		if (!Helper.isInList(action, moves) || action.isPassMove()){
			throw new IllegalMoveException(action);
		}
		//update lines
		
		int row = action.getRow();
		int col = action.getColumn();
		if(this.directionPossible(row, col, 0,1)){
			clone.updateLine(row, col, 0,1);
		}
		if(this.directionPossible(row, col, 1,1)){
			clone.updateLine(row, col, 1,1);
		}		
		if(this.directionPossible(row, col, 1,0)){
			clone.updateLine(row, col, 1,0);
		}		
		if(this.directionPossible(row, col, 1,-1)){
			clone.updateLine(row, col, 1,-1);
		}		
		if(this.directionPossible(row, col, 0,-1)){
			clone.updateLine(row, col, 0,-1);
		}		
		if(this.directionPossible(row, col, -1,-1)){
			clone.updateLine(row, col, -1,-1);
		}		
		if(this.directionPossible(row, col, -1,0)){
			clone.updateLine(row, col, -1,0);
		}		
		if(this.directionPossible(row, col, -1,1)){
			clone.updateLine(row, col, -1,1);
		}

		// add disk on the requested position
		char player;
		if (playerToMove){
		    player = 'W';
		}
		else{
			player = 'B';
		}
		clone.board[row][col] = player;
		clone.playerToMove = !playerToMove;
		clone.nextTurn();
		return clone;
	}

	private void updateLine(int row, int col, int dirrow, int dircol){
		int tmprow = row + dirrow;
		int tmpcol = col + dircol;
		
		char player;
		if (playerToMove){
		    player = 'W';
		}
		else{
			player = 'B';
		}
		
		while(board[tmprow][tmpcol] != player){
			board[tmprow][tmpcol] = player;
			tmprow+=dirrow;
			tmpcol+=dircol;
		}
	}
	
	/**
	 * Returns a new <code>OthelloPosition</code>, identical to the current one.
	 */
	protected OthelloPosition clone() {
		OthelloPosition newPosition = new OthelloPosition();
		newPosition.playerToMove = playerToMove;
		for (int i = 0; i < BOARD_SIZE + 2; i++)
			for (int j = 0; j < BOARD_SIZE + 2; j++)
				newPosition.board[i][j] = board[i][j];
		newPosition.setToBePlayed();
		return newPosition;
	}

	/* illustrate and other output functions */

	/**
	 * Draws an ASCII representation of the position. White squares are marked
	 * by '0' while black squares are marked by 'X'.
	 */
	public void illustrate() {
		System.out.print("   ");
		for (int i = 1; i <= BOARD_SIZE; i++)
			System.out.print("| " + i + " ");
		System.out.println("|");
		printHorizontalBorder();
		for (int i = 1; i <= BOARD_SIZE; i++) {
			System.out.print(" " + i + " ");
			for (int j = 1; j <= BOARD_SIZE; j++) {
				if (board[i][j] == 'W') {
					System.out.print("| 0 ");
				} else if (board[i][j] == 'B') {
					System.out.print("| X ");
				} else {
					System.out.print("|   ");
				}
			}
			System.out.println("| " + i + " ");
			printHorizontalBorder();
		}
		System.out.print("   ");
		for (int i = 1; i <= BOARD_SIZE; i++)
			System.out.print("| " + i + " ");
		System.out.println("|\n");
	}

	private void printHorizontalBorder() {
		System.out.print("---");
		for (int i = 1; i <= BOARD_SIZE; i++) {
			System.out.print("|---");
		}
		System.out.println("|---");
	}

	public String toString() {
		String s = "";
		char c, d;
		if (playerToMove) {
			s += "W";
		} else {
			s += "B";
		}
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				d = board[i][j];
				if (d == 'W') {
					c = 'O';
				} else if (d == 'B') {
					c = 'X';
				} else {
					c = 'E';
				}
				s += c;
			}
		}
		return s;
	}

}
