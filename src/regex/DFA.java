package regex;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/*
 * Due to the simplicity of the regex to NFA converter, if the 
 * NFA does not contain epsilon or kleene star, it is a DFA.
 * NFA only converted to DFA if it contains epsilon transitions or
 * kleene star.
 * 
 */
public class DFA extends NFA {

	public DFA(NFA nfa) {
		super();
	}

	public DFA() {
		super();
	}
	
	public NFA reduce(NFA nfa) {
		if (!containsEpsilon(nfa)) {
			nfa.findSelfReferentialTransitions();
		}

		return nfa;
	}

	// Used for handling epsilon-NFA's, for conversion to DFA
	public static boolean containsEpsilon(NFA nfa) {
		Set<State> visited = new HashSet<>();
		Queue<State> queue = new LinkedList<>();

		State startState = nfa.getStartState();
		queue.add(startState);

		while (!queue.isEmpty()) {
			State currentState = queue.poll();
			if (visited.contains(currentState)) {
				continue;
			}
			visited.add(currentState);

			for (Transition t : currentState.getOutTransitions()) {
				if (t.getTransitionSymbol() == 'E') {
					// If an 'E' transition is found, return true
					return true;
				}
				if (!visited.contains(t.getNextState())) {
					queue.add(t.getNextState());
				}
			}
		}
		// No 'E' transition found in the entire NFA
		return false;
	}
}
