
package regex;

/**
 * Creates the transition(s) (to and from each state) in a nondeterministic
 * finite automaton (NFA).
 *
 * @author hannah.a.wang@vanderbilt.edu
 */
class Transition {

	private char transitionSymbol;
	private State prev;
	private State next;

	/**
	 * Transition Constructor
	 *
	 * @param prev
	 * @param next
	 *
	 */
	Transition(State prev, State next, char transitionSymbol) {
		this.prev = prev;
		this.next = next;
		this.transitionSymbol = transitionSymbol;
	}

	/**
	 * Return the transition symbol, the char that initiates the transition.
	 */
	public char getTransitionSymbol() {
		return transitionSymbol;
	}

	/**
	 * Return the previous state of this transitions.
	 */
	public State getPreviousState() {
		return prev;
	}

	/**
	 * Return the next state of this transition.
	 */
	public State getNextState() {
		return next;
	}

}
