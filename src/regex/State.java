
package regex;

import java.util.*;

/**
 * The State class represents an individual state in the Non-deterministic
 * Finite Automaton (NFA). It adds transitions for each state within the NFA and
 * determines if it is a start or accepting state.
 *
 *
 * @author hannah.a.wang@vanderbilt.edu
 */
class State {

	// List of transitions coming into the current state
	private List<Transition> inTransitions = new ArrayList<>();

	// List of transitions going out of the current state
	private List<Transition> outTransitions = new ArrayList<>();

	private boolean acceptState = false;

	/**
	 * Returns whether this state is an accepting state.
	 *
	 * @return the isAccept
	 */
	public boolean isAccept() {
		return acceptState;
	}

	/**
	 * Set this state as an accepting state.
	 */
	public void setAccept() {
		this.acceptState = true;
	}

	/**
	 * Set this state as a non-accepting state.
	 */
	public void nonAccept() {
		this.acceptState = false;
	}

	/**
	 * Returns the list of this state's outgoing transitions (edges).
	 *
	 * @return List outTransitions
	 */
	public List<Transition> getOutTransitions() {
		return outTransitions;
	}

	/**
	 * Add transition to the list of transitions from this state to another.
	 *
	 * @param t the outgoing edge to be added.
	 */
	public void addOutTransition(Transition t) {
		this.outTransitions.add(t);
	}

	/**
	 * Returns the list of this state's incoming transitions (edges).
	 *
	 * @return the inTransitions
	 */
	public List<Transition> getInTransitions() {
		return inTransitions;
	}

	/**
	 * Add a transition to the list of incoming links.
	 *
	 * @param t the incoming transition to be added.
	 */
	public void addInTransition(Transition t) {
		this.inTransitions.add(t);
	}

	/**
	 * Checks whether this state contains an outgoing transition with symbol 'c'.
	 *
	 * @param c edge to be checked.
	 * @return
	 */
	public boolean containsOutTransition(char c) {
		for (Transition t : outTransitions) {
			if (t.getTransitionSymbol() == c) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Makes a new transition from the current state to the next (new) state through
	 * input char c (transition symbol), and returns the new state.
	 *
	 * @param x the edge label through the transition being made
	 * @return next the new state
	 */
	public State transition(char c) {

		State next = new State();

		for (Transition t : outTransitions) {
			if (t.getTransitionSymbol() == c) {
				next = t.getNextState();
			}
		}
		return next;
	}

	/**
	 * Returns the previous state of the transition whose symbol is passed as an
	 * argument.
	 *
	 * @param c transition symbol
	 * @return source of the transition symbol x that is an inTransition for the
	 *         currentState
	 */
	public State sourceOf(char c) {

		State ret = new State();

		for (Transition t : inTransitions) {
			if (t.getTransitionSymbol() == c) {
				ret = t.getPreviousState();
			}
		}
		return ret;
	}

	/**
	 * Removes an incoming transition.
	 *
	 * @param transition the incoming transition to be removed.
	 */
	public void removeInTransition(Transition transition) {
		inTransitions.remove(transition);
	}

	/**
	 * Removes an outgoing transition.
	 *
	 * @param transition the outgoing transition to be removed.
	 */
	public void removeOutTransition(Transition transition) {
		outTransitions.remove(transition);
	}

	/**
	 * Deletes this state from the NFA by removing in and out transitions. Used for
	 * converting NFA to DFA.
	 * 
	 */
	public void deleteState() {
		// Remove outgoing transitions from this state
		for (Transition transition : outTransitions) {
			State nextState = transition.getNextState();
			nextState.removeInTransition(transition);
		}
		outTransitions.clear(); // Clear outgoing transitions

		// Remove incoming transitions to this state
		for (Transition transition : inTransitions) {
			State previousState = transition.getPreviousState();
			previousState.removeOutTransition(transition);
		}
		inTransitions.clear(); // Clear incoming transitions
	}
}
