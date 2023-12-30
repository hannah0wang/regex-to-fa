
package regex;

import java.util.*;

/**
 * This class handles the parsing of regular expressions and user input for
 * constructing and simulating a Non-deterministic Finite Automaton (NFA).
 *
 * Lowercase alphabet (a, b, c, ...) Or: "|" for choice between expressions
 * Kleene Star: "*" for zero or more repetitions Parentheses: "()" for grouping
 * expressions together
 *
 * @author hannah.a.wang@vanderbilt.edu
 * 
 */
public class RegexToFA {

	public static void main(String[] args) {

		try (Scanner input = new Scanner(System.in)) {
			System.out.print("Enter a regular expression: ");

			while (true) {
				String regexInput = input.nextLine().trim();

				if (regexInput.isEmpty()) {
					break;
				}

				if (!regexInput.matches("^[a-z()*|]+$")) {
					throw new IllegalArgumentException("Input contains invalid characters.");
				}

				NFA nfa = parseRegex(regexInput);
				System.out.println("\n---NFA State Transition Table---");
				System.out.println(nfa.toString());

				// nfa.findSelfReferentialTransitions();

				/*
				 * DFA dfa = new DFA(nfa); dfa.reduce(nfa);
				 * System.out.println("\n---DFA State Transition Table---");
				 * System.out.println(dfa.toString());
				 */

				System.out.print("\nEnter another regular expression (or press Enter to exit): ");
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Reads a regular expression as input and converts to an NFA. Uses recursive
	 * descent parsing algorithm (top-down) to recursively break down the input into
	 * smaller components according to the regex production rules
	 *
	 * @param regex regular expression
	 * @return nondeterministic finite automaton (NFA)
	 */
	public static NFA parseRegex(String regex) {

		NFA nfa = new NFA();
		/*
		 * currentBranch is used to signify the start of a new branch and for handling
		 * possible future choice (alternation) branches.
		 */
		State currentBranch = nfa.getStartState();
		char repeat = 0; // record the previous character for Kleene star

		int i = 0;
		Stack<Character> parenthStack = new Stack<Character>();

		while (i < regex.length()) {

			/*
			 * If left parenthesis '(' reached, then push it onto parenthStack. Read
			 * characters until the corresponding right parenthesis is reached. Inner
			 * parentheses are ignored.
			 */
			if (regex.charAt(i) == '(') {
				parenthStack.push('(');
				StringBuilder groupSubExp = new StringBuilder();
				int j = i + 1;
				while (true) {
					if (regex.charAt(j) == ')') {
						parenthStack.pop();
						if (parenthStack.empty()) {
							break;
						}
					} else if (regex.charAt(j) == '(') {
						parenthStack.push('(');
					}
					if (!parenthStack.empty()) {
						groupSubExp.append(regex.charAt(j));
					}
					j++;
				}

				// recursive call to create subNFA for chars inside parentheses
				NFA subNFA = parseRegex(groupSubExp.toString());

				// connect currentState to the beginning of groupSubExp
				for (Transition t : subNFA.getStartState().getOutTransitions()) {
					nfa.getCurrentState().addOutTransition(t);
				}
				subNFA.removeInitialState();
				i = j;
			}

			/*
			 * If a grouping has not been identified, recursive call is not required. make
			 * sure all operations are within the scope
			 */
			if (Character.isAlphabetic(regex.charAt(i)) && Character.isLowerCase(regex.charAt(i))) {

				repeat = regex.charAt(i);
				nfa.concatenation(regex.charAt(i));

			} else if (regex.charAt(i) == '*') {
				// recursive call to make previous character/group repeating
				nfa.kleeneStar(repeat);
				// reached alternation operator
			} else if (regex.charAt(i) == '|') {
				currentBranch = nfa.alternation(currentBranch);

			}
			i++;
		}
		return nfa;
	}
}