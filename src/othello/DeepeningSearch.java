package othello;

import java.util.concurrent.Callable;


public class DeepeningSearch implements Callable<Object>{
	private OthelloPosition pos;
	private OthelloAlgorithm algo;
	private OthelloAction action;
	private int depth;
	
	public DeepeningSearch(OthelloPosition position, OthelloAlgorithm algorithm){
		pos = position;
		algo = algorithm;
		depth = 4;
	}
		
	public OthelloAction getAction(){
		return action;
	}

	@Override
	public Object call() throws Exception {
		while(!Thread.currentThread().isInterrupted()){
			algo.setSearchDepth(depth++);
			action = algo.evaluate(pos);
		}
		return action;
	}


}
