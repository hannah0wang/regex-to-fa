# Converting Regular Expressions to Finite Automata

### Project Overview
The primary goal of this project was to develop a program capable of converting a given regular 
expression into an equivalent Non-deterministic Finite Automaton. The NFA represents the 
behavior and patterns defined by the input regular expression. Acceptable input includes any 
lowercase alphabet character, set of parentheses, Kleene star, and alternation.
By leveraging algorithms and parsing techniques, the program endeavors to decipher user-provided regular expressions, character by character. The accepted input encompasses the 
language of lowercase alphabet characters, parentheses for grouping and precedence, the Kleene 
star (*) denoting zero or more occurrences, and the alternation symbol (|) offering choice or 
branching possibilities within the regex pattern.
One of the major challenges of the project involved establishing robustness and adaptability to 
comprehensively handle the inherent elements within regular expressions. The robustness is
largely attributed to the parsing method used to handle the regular expression.

The following steps delineate the process to convert a given regular expression into an NFA.
• The user inputs a regular expression string, that is then parsed to separate the expression 
into characters, parentheses, and operators. Methods explored in this project include 
Shunting Yard algorithm, Thompson’s construction, and recursive descent parsing.
• After parsing, the individual tokens are used to create an NFA, with each character 
corresponding to its respective transition and state depending on the operation 
(concatenation, Kleene star, or alternation)
• Leveraging Java’s object-oriented characteristics, the creation of the NFA becomes
intuitive. The resulting NFA object comprises of states and transitions.

### Parsing Methods Explored
The shunting yard algorithm is a straightforward method for parsing infix expressions that 
contain binary operators with different levels of precedence. Typically, the algorithm accurately 
assigns the correct operands to each operator while considering their precedence order. The 
algorithm is used to convert the infix notation, which includes the conventional notation of 
regular expressions, to reverse polish notation, where all the operators appear after (to the right) 
of all the operands/characters. The postfix notation is also known as the reverse polish notation 
(RPN). As a result, it facilitates immediate evaluation of the expression or construction of the 
corresponding syntax tree. The algorithm is quite simple, as it uses basic stack manipulation to 
push all the characters before the operators. However, while creating the project, I found it 
difficult to correctly interpret the altered regex into its corresponding NFA. After facilitating 
more research, I found it could be due to several reasons. First, the Shunting Yard algorithm is 
tailored for handling mathematic expressions, which involves slightly different grammar rules 
than a regular expression. Additionally, although the algorithm itself is very simple, a solid 
flexible implementation might include thousands of lines of code, which was not entirely feasible 
given the limited scope of the project.

<img width="328" alt="image" src="https://github.com/hannah0wang/regex-to-fa/assets/43276816/933be999-5399-4808-b7b1-146d35c47bba">

The above figure shows an example breakdown of how the recursive descent parsing breaks 
down the regular expression into individual tokens consisting of characters, parentheses, and 
operators.

After consulting ChatGPT, it suggested implementing a top-down recursive descent algorithm, 
as this method works well with the nature of regular expressions. By mapping each grammar rule
in the regex to corresponding recursive functions or methods, this method simplifies the handling 
of different regex elements such as alternation, concatenation, and Kleene star operations. It also 
enables hierarchical parsing, ensuring the proper handling of nested constructs like parentheses 
and maintaining precedence. While the recursive descent parser is easier to implement on regular 
expressions, it does have some limitations. Usually, it is used to recognize simple languages with 
set production rules. However, considering the broad scope of regular languages, this approach 
encounters constraints when dealing with more complex patterns, leading to challenges in 
accurately capturing nested structures or recursive definitions. As a result, the program cannot 
yet handle complex regular expressions, including Kleene star applied to grouping (for example, 
(aba)*).

### Test Cases and Results
The program outputs the correct NFA and state transition table for simple regular expressions 
consisting of concatenations followed by a single character transformed by Kleene star.

<img width="188" alt="image" src="https://github.com/hannah0wang/regex-to-fa/assets/43276816/6f764ad7-b758-4073-bfd6-5ae98b58fbbe">

As shown, the state transition diagram can be verified using the NFA diagram representing the 
regular expression, indicating the correct construction of the NFA for this test case.

The second test case is slightly more complex, including alternation and grouping.

<img width="260" alt="image" src="https://github.com/hannah0wang/regex-to-fa/assets/43276816/49b07fe9-a381-4e3d-8647-b8eeb41a5152">

And again, the NFA can be verified using the diagram and state transition table.

The following shows a test case that does not generate the expected NFA.

<img width="210" alt="image" src="https://github.com/hannah0wang/regex-to-fa/assets/43276816/fbbd5fed-58d5-4a06-99c0-cc99cb92823b">

The characters inside the parentheses are correctly concatenated, and q4 is correctly identified as 
an accepting state, but there is no transition out of q4 back to q1 to represent the Kleene star 
transformation.

Several areas required attention that I intended to address but could not due to the limited scope 
of the project. This includes correctly implementing more complex test cases like shown above, 
as well as converting the NFA or epsilon NFA to its equivalent DFA.

### Reflections on the Use of AI

During the brainstorming phase of the project, AI was pivotal in generating ideas and outlining 
the scope to create the projects it recommended. Moreover, the utilization of AI tools such as 
ChatGPT contributed to expediting the process by assisting in resolving bugs or logic errors 
within the code. It helped explore additional methods to solve various problems encountered 
during the development phase as well as providing summaries of newly researched topics 
essential for the project. While extremely beneficial in certain aspects, AI tools might encounter 
challenges in delivering suitable guidance, especially when dealing with unfamiliar material or 
intricate logic. Furthermore, their responses could potentially be misleading and result in 
erroneous implementations, underscoring the necessity for human judgment.

### References

Kyashif, Denis. “Parsing Regex with Recursive Descent.” · Denis Kyashif’s Blog, 17 Aug. 2020, 
deniskyashif.com/2020/08/17/parsing-regex-with-recursive-descent/. 

“Parse Tree and Syntax Tree.” GeeksforGeeks, GeeksforGeeks, 9 Jan. 2023, 
www.geeksforgeeks.org/parse-tree-and-syntax-tree/. 

“Recursive Descent Parser.” GeeksforGeeks, GeeksforGeeks, 9 June 2023, 
www.geeksforgeeks.org/recursive-descent-parser/. 

“Regular Expression to ∈-NFA.” GeeksforGeeks, GeeksforGeeks, 24 Jan. 2023, 
www.geeksforgeeks.org/regular-expression-to-nfa/. 

“Shunting Yard Algorithm.” Brilliant Math & Science Wiki, brilliant.org/wiki/shunting-yard-algorithm/. Accessed 6 Dec. 2023. 

“Chatgpt.” ChatGPT, openai.com/chatgpt. Accessed 6 Dec. 2023.
