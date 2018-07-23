package othello;

import java.util.LinkedList;

public class AlphaBetaAlgorithm implements OthelloAlgorithm {

	private static final int INFINITY = 1000;
	private OthelloEvaluator _evaluator;
	private int _searchDepth;
	
	@Override
	public void setEvaluator(OthelloEvaluator evaluator) {
		_evaluator = evaluator;
	}

	@Override
	public OthelloAction evaluate(OthelloPosition position) {		
		_searchDepth = Math.min(_searchDepth, position.getTurnsLeft()+1);
		
		if (position.toMove()) //white
		{
			OthelloAction eval = maxValue(_searchDepth, position, -INFINITY, INFINITY);
			return eval;
		}
		else{
			OthelloAction eval = minValue(_searchDepth, position,-INFINITY, INFINITY);
			return eval;
		}
	}

	@Override
	public void setSearchDepth(int depth) {
		_searchDepth = depth;
	}
	
	private OthelloAction maxValue(int depth, OthelloPosition pos, int alpha, int beta){
		if (depth <= 0){
			OthelloAction act = new OthelloAction(0,0,true);
			act.setValue(_evaluator.evaluate(pos));
			return act;}
		OthelloAction bestaction = new OthelloAction(0,0,true);
		bestaction.setValue(-INFINITY);
		LinkedList<OthelloAction> moves = pos.getMoves();
		if(moves.size() == 0){
			try {
				OthelloPosition posbi = pos.makeMove(bestaction);
				bestaction.setValue(minValue(depth-1,posbi, alpha, beta).getValue());
				return bestaction;
			} catch (IllegalMoveException e) {
				e.printStackTrace();
			}
		}
		for (OthelloAction move : moves){
			try {
				OthelloPosition posbis = pos.makeMove(move);
				OthelloAction tmpact = minValue(depth-1, posbis, alpha, beta);
				if (tmpact.getValue() > bestaction.getValue()){
					bestaction = move;
					bestaction.setValue(tmpact.getValue());
				}
				if (bestaction.getValue() >= beta){
					return bestaction;
				}
				alpha = Math.max(alpha, bestaction.getValue());
				if (alpha == bestaction.getValue()){
				}
			} catch (IllegalMoveException e) {
				System.out.print("Illegal Move : ");
				e.getAction().print();	System.out.print("\n");
			}			
		}
		return bestaction;
	}
	
	private OthelloAction minValue(int depth, OthelloPosition pos, int alpha, int beta){
		if (depth <= 0){
			OthelloAction act = new OthelloAction(0,0,true);
			act.setValue(_evaluator.evaluate(pos));
			return act;
		}

		OthelloAction bestaction = new OthelloAction(0,0,true);
		bestaction.setValue(INFINITY);
		LinkedList<OthelloAction> moves = pos.getMoves();
		
		if(moves.size() == 0){
			try {
				OthelloPosition posbi = pos.makeMove(bestaction);
				bestaction.setValue(maxValue(depth-1,posbi, alpha, beta).getValue());
				return bestaction;
			} catch (IllegalMoveException e) {
				e.printStackTrace();
			}
		}

		for (OthelloAction move : moves){
			try {
				OthelloPosition posbis = pos.makeMove(move);
				OthelloAction tmpact = maxValue(depth-1, posbis, alpha, beta);
				if (tmpact.getValue() < bestaction.getValue()){
					bestaction = move;
					bestaction.setValue(tmpact.getValue());
				}
				if (bestaction.getValue() <= alpha){
					return bestaction;
				}
				beta = Math.min(beta, bestaction.getValue());
			} catch (IllegalMoveException e) {
				System.out.print("Illegal Move : ");
				e.getAction().print();
				System.out.print("\n");
			} 
		}
		return bestaction;	
	}

}
