package othello;


public class WeightedHeuristic implements OthelloEvaluator{
	
	StabilityHeuristic stab;
	MobilityHeuristic mob;
	CornerHeuristic corner;
	OthelloHeuristic coins;
	
	@Override
	public int evaluate(OthelloPosition position) {
		
		stab = new StabilityHeuristic();
		mob = new MobilityHeuristic();
		corner = new CornerHeuristic();
		coins = new OthelloHeuristic();
		
		return coins.evaluate(position) + corner.evaluate(position) + mob.evaluate(position) + 2*stab.evaluate(position);
	}
	
}
