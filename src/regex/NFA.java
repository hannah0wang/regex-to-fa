
package regex;

import java.util.*;

/**
 * This program implements a Non-deterministic Finite Automaton (NFA). It allows
 * the construction of an NFA based on a given regular expression, and provides
 * functionalities to simulate the NFA on input strings, perform operations like
 * concatenation, branch and grouping.
 *
 * @author hannah.a.wang@vanderbilt.edu
 * 
 */
class NFA {

	private State startState;
	private State currentState;

	/**
	 * NFA Constructor.
	 *
	 * Creates an empty NFA by instantiating the start state as current state.
	 */
	public NFA() {

		startState = new State();
		currentState = startState;
	}

	/**
	 * Returns the starting state startState.
	 * 
	 * @return startState
	 */
	public State getStartState() {
		return startState;
	}

	/**
	 * Gets the current state currentState.
	 * 
	 * @return currentState
	 */
	public State getCurrentState() {
		return currentState;
	}

	/**
	 * Add the character c to NFA with concatenation.
	 *
	 * @param c must be a lowercase alphabet char.
	 * 
	 */
	public void concatenation(char c) {

		State nextState = new State();
		Transition t = new Transition(currentState, nextState, c);

		currentState.addOutTransition(t);
		nextState.addInTransition(t);

		nextState.setAccept();
		currentState.nonAccept();

		currentState = nextState;

	}

	/**
	 * Create an alternation branch to deal with choice opperator.
	 *
	 * This method is used when the input is |, in order to create an alternation
	 * branch on the NFA state.
	 *
	 * @param currentBranch is the State at the beginning of the current branch
	 * @return s the beginning of the new branch.
	 *
	 */
	public State alternation(State currentBranch) {
		State s = new State();
		currentState = s;
		Transition epsilon = new Transition(currentBranch, s, 'E');
		currentBranch.addOutTransition(epsilon);
		s.addInTransition(epsilon);
		return s;
	}

	/**
	 * Creates a sub-NFA for Kleene star, to be connected to the overall NFA
	 *
	 * Unlike concatenation and |, this method leaves the currentState as is. It
	 * only adds new transitions to the current state
	 *
	 * @param c the character that has Kleene star (*) applied.
	 */
	public void kleeneStar(char c) {

		// move backward one state (through x). Must accept case of zero c's
		currentState = currentState.sourceOf(c);
		currentState.setAccept();

		// The new currentState has already been set as
		// an accept state in concatenation(). Add new self-referring transition
		currentState = currentState.transition(c);

		Transition t = new Transition(currentState, currentState, c);
		currentState.addInTransition(t);
		currentState.addOutTransition(t);

	}

	/**
	 * Removes the start state by setting it to null. Used when connecting an
	 * epsilon transitioning state to the rest of the automaton.
	 */
	public void removeInitialState() {
		startState = null;
	}

	/**
	 * toString method returns the NFA state transition table
	 * 
	 * @return table.toString(), the state transition table.
	 *
	 */
	public String toString() {
		Map<State, String> stateMap = new HashMap<>();
		Queue<State> queue = new LinkedList<>();
		Set<State> visited = new HashSet<>();
		StringBuilder table = new StringBuilder();

		int stateCount = 0;
		stateMap.put(startState, "q" + stateCount + " (Start)");
		stateCount++;
		queue.add(startState);

		while (!queue.isEmpty()) {
			State currentState = queue.poll();
			if (!visited.contains(currentState)) {
				stateMap.put(currentState, "q" + stateCount);
				stateCount++;
				visited.add(currentState);
			}

			String stateTransitionSymb = stateMap.get(currentState);
			table.append("\n" + "State: " + stateTransitionSymb + (currentState.isAccept() ? " (Accepting)" : "")
					+ (currentState == startState ? " (Start)" : "") + "\n");
			/*
			 * System.out.println("State: " + stateTransitionSymb + (currentState.isAccept()
			 * ? " (Accepting)" : "") + (currentState == startState ? " (Start)" : ""));
			 */

			for (Transition transition : currentState.getOutTransitions()) {
				State nextState = transition.getNextState();
				String nextStateTranSymb = stateMap.get(nextState);
				if (nextStateTranSymb == null) {
					stateMap.put(nextState, "q" + stateCount);
					stateCount++;
					nextStateTranSymb = stateMap.get(nextState);
				}
				char transitionSymbol = transition.getTransitionSymbol() == 'E' ? 'ε'
						: transition.getTransitionSymbol();
				table.append("  Transition: " + transitionSymbol + " -> " + nextStateTranSymb + "\n");
//				System.out.println("  Transition: " + transitionSymbol + " -> " + nextStateTranSymb);
				if (!visited.contains(nextState)) {
					queue.add(nextState);
					visited.add(nextState);
				}
			}
		}
		return table.toString();
	}

	/**
	 * Iterates through all states and transitions to find self-referential
	 * transitions.
	 */
	public void findSelfReferentialTransitions() {
		Set<State> visited = new HashSet<>();
		Queue<State> queue = new LinkedList<>();

		queue.add(startState);
		visited.add(startState);

		while (!queue.isEmpty()) {
			State currentState = queue.poll();
			List<Transition> outTransitions = currentState.getOutTransitions();

			for (Transition transition : outTransitions) {
				if (transition.getNextState() == currentState) {
					// Self reference found
					System.out.println("State q" + currentState.hashCode() + " has a self-referential transition.");
				}

				State nextState = transition.getNextState();
				if (!visited.contains(nextState)) {
					queue.add(nextState);
					visited.add(nextState);
				}
			}
		}
	}
}