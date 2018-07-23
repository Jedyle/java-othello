package othello;


public class CornerHeuristic implements OthelloEvaluator{
	
	@Override
	public int evaluate(OthelloPosition position) {
		String pos = position.toString();		
		int whiteCorners = 0;
		int blackCorners = 0;		
		switch (pos.charAt(1)){
		case 'O':
			whiteCorners++;
			break;
		case 'X':
			blackCorners++;
			break;
		default:
			break;
		}
		switch (pos.charAt(8)){
		case 'O':
			whiteCorners++;
			break;
		case 'X':
			blackCorners++;
			break;
		default:
			break;
		}		
		switch (pos.charAt(57)){
		case 'O':
			whiteCorners++;
			break;
		case 'X':
			blackCorners++;
			break;
		default:
			break;
		}
		switch (pos.charAt(64)){
		case 'O':
			whiteCorners++;
			break;
		case 'X':
			blackCorners++;
			break;
		default:
			break;
		}
		if(whiteCorners + blackCorners > 0){
			return ((100*(whiteCorners - blackCorners))/(whiteCorners+blackCorners));
		}
		else{
			return 0;
		}
		
	}
	
}
