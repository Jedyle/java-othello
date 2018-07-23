package othello;


public class OthelloHeuristic implements OthelloEvaluator {

	@Override
	public int evaluate(OthelloPosition position) {
		
		String pos = position.toString();
		
		int nbWhite = 0;
		int nbBlack = 0;
		
		for (int i = 0; i < pos.length(); i++){
			if (pos.charAt(i) == 'X')
				nbBlack++;
			else if (pos.charAt(i) == 'O')
				nbWhite++;			
		}
		
		return 100*(nbWhite-nbBlack)/(nbWhite + nbBlack);
	}
	
	

}
