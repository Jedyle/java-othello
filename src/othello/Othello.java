package othello;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Othello {
	
	public static void main(String[] args) {
		
		if (args.length == 2){
			String position = args[0];
			long time = Integer.parseInt(args[1]);
		
			OthelloPosition start = new OthelloPosition(position);
			OthelloAlgorithm alphabeta = new AlphaBetaAlgorithm();
			alphabeta.setEvaluator(new WeightedHeuristic());
			
			if (time <= 2){
				alphabeta.setSearchDepth(4);
				OthelloAction action = alphabeta.evaluate(start);
				action.print();
			}
			
			else{
				DeepeningSearch res = new DeepeningSearch(start, alphabeta);
				
				ExecutorService executor = Executors.newSingleThreadExecutor();
				try {
					executor.invokeAll(Arrays.asList(res), time-1, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				executor.shutdownNow();
				
				OthelloAction action = res.getAction();
				action.print();
					
			if (!executor.isTerminated()){
				System.exit(0); //exit if the thread doesn't interrupt in spite of the signal
			}
				
			}
			
		}
		

	}

}
