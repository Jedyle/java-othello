package othello;


public class StabilityHeuristic implements OthelloEvaluator{

	private char[][] board;
	private boolean[][] whitestable;
	private boolean[][] blackstable;
	private int board_size;
	
	private boolean[] filledrow;
	private boolean[] filledcol;
	private boolean[] filleddiag1;
	private boolean[] filleddiag2;
	
	
	@Override
	public int evaluate(OthelloPosition position) {
		// TODO Auto-generated method stub
		return stability(position);
	}
	
	
	private int stability(OthelloPosition position){
		String pos = position.toString();
		board_size = 8;
		board = new char[board_size + 2][board_size + 2];
		for (int i = 1; i <= board_size * board_size; i++) {
			char c;
			if (pos.charAt(i) == 'E') {
				c = 'E';
			} else if (pos.charAt(i) == 'O') {
				c = 'W';
			} else {
				c = 'B';
			}
			int column = ((i - 1) % 8) + 1;
			int row = (i - 1) / 8 + 1;
			board[row][column] = c;
		}			
		whitestable = new boolean[board_size + 2][board_size + 2];
		blackstable = new boolean[board_size + 2][board_size + 2];
		
		for (int i = 0; i < board_size + 2; i++){
			for (int j = 0; j < board_size + 2; j++){
				whitestable[i][j] = false;
				blackstable[i][j] = false;
			}
		}
		
		filldiags();
		
		check_stable(whitestable, 'W');
		check_stable(whitestable, 'W'); //do it twice to find stable coins next to other stable coins

		check_stable(blackstable, 'B');
		check_stable(blackstable, 'B');
		
		int black = count_stable(blackstable);
		int white = count_stable(whitestable);
				
		if (white + black > 0){
			return 100*(white - black)/(white + black);
		}
		return 0;
	}
	
	private void filldiags(){
		filledrow = new boolean[board_size+2];
		
		for (int i = 1; i <= board_size; i++){
			filledrow[i] = diagisfull(i,1,0,1);
		}
	
		filledcol = new boolean[board_size+2];
		
		for (int i = 1; i<= board_size; i++){
			filledcol[i] = diagisfull(1,i, 1,0);
		}
		
		filleddiag1 = new boolean[2*board_size + 1];
		
		for (int i = 1; i<= 2*board_size - 1; i++){
			if (i <= board_size){
				filleddiag1[i] = diagisfull(board_size+1-i, 1, 1,1);
			}
			else{
				filleddiag1[i] = diagisfull(1,i-board_size+1,1,1);
			}
		}
		
		filleddiag2 = new boolean[2*board_size + 1];		
		
		for (int i = 1; i<= 2*board_size - 1; i++){
			if (i <= board_size){
				filleddiag2[i] = diagisfull(1, i, 1,-1);
			}
			else{
				filleddiag2[i] = diagisfull(i-board_size+1,board_size,1,-1);
			}
		}
		
	}
	
	private boolean diagisfull(int startrow, int startcol, int dirrow, int dircol){
		int tmpr = startrow;
		int tmpc = startcol;

		while(tmpr <= board_size && tmpr >= 1 && tmpc <= board_size && tmpc >= 1){
			if(board[tmpr][tmpc] == 'E'){
				return false;
			}
			tmpr+=dirrow;
			tmpc+=dircol;
		}
		return true;
	}
	
	private boolean directionisfull(int row, int col, int dir){
		switch (dir){
		case 1: //col
			return filledcol[col];
		case 2: //row
			return filledrow[row];
		case 3: //diag (1,1)
			return filleddiag1[board_size - row + col];
		case 4: //diag (1,-1)
			return filleddiag2[row+col -1];
		default:
			return false;
		}
	}
	
	private boolean isStableNext(int row, int col, int dir, boolean[][] stable){
		switch (dir){
		case 1: //col
			if (row == 1 || row == 8){
				return true;
			}
			else{
				return (stable[row+1][col] || stable[row-1][col]);
			}
		case 2: //row
			if (col == 1 || col == 8){
				return true;
			}
			else{
				return (stable[row][col+1] || stable[row][col-1]);
			}
		case 3: //diag1
			if (row == 1 || row == 8 || col == 1 || col == 8){
				return true;
			}
			else{
				return (stable[row+1][col+1] || stable[row-1][col-1]);
			}
		case 4: //diag2
			if (row == 1 || row == 8 || col == 1 || col == 8){
				return true;
			}
			else{
				return (stable[row+1][col-1] || stable[row-1][col+1]);
			}
		default:
			return false;	
		}
		
	}
	
	private void check_stable(boolean[][] stable, char player){
		for (int i = 1; i <= board_size; i++){
			for (int j=1; j<= board_size; j++){
				
				if (board[i][j] == player){			
					
					boolean stab = (isStableNext(i,j,1, stable) || directionisfull(i,j,1))
							&& (isStableNext(i,j,2, stable) || directionisfull(i,j,2))
							&& (isStableNext(i,j,3, stable) || directionisfull(i,j,3))
							&& (isStableNext(i,j,4, stable) || directionisfull(i,j,4));
						
					stable[i][j] = stab;
				}
			}			
		}
	}
	
	public int count_stable(boolean[][] stable){
		int count = 0;
		for (int i = 1; i <= board_size; i++){
			for (int j=1; j<= board_size; j++){
				
				if(stable[i][j] == true){
					count++;
				}
				
			}
		}
		return count;		
	}
	

}
