package othello;


public class MobilityHeuristic implements OthelloEvaluator {
	
	@Override
	public int evaluate(OthelloPosition position) {
			String pos = position.toString();
			int blackPlayerMoves = 0;
			int whitePlayerMoves = 0;
			
			String pos2 = pos;
			if (pos.charAt(0) == 'W'){
				pos2 = pos.replace('W', 'B');
			}
			else if (pos.charAt(0) == 'B'){
				pos2 = pos.replace('B','W');
			}
			
			OthelloPosition position2 = new OthelloPosition(pos2);
			
			if(position.toMove()){
				whitePlayerMoves = position.getMoves().size();
				blackPlayerMoves = position2.getMoves().size();
			}
			else{
				blackPlayerMoves = position.getMoves().size();
				whitePlayerMoves = position2.getMoves().size();
			}
					
			if (whitePlayerMoves + blackPlayerMoves > 0){
				return ((100*(whitePlayerMoves - blackPlayerMoves))/(whitePlayerMoves + blackPlayerMoves));
			}
			else{
				return 0;
			}
		}
		
	
}
