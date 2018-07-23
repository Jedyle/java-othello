package othello;

/**
 * This exception is supposed to be thrown when an OthelloPosition is asked to
 * make a move that is not legal in the position.
 */

public class IllegalMoveException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OthelloAction action;

	public IllegalMoveException(OthelloAction a) {
		action = a;
	}

	public OthelloAction getAction() {
		return action;
	}
}