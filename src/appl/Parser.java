package appl;

import model.RPNParser;
import model.ShuntYardParser;

import java.util.Queue;

public class Parser {

    private ShuntYardParser algebraic;
    private RPNParser rpn;

    public Parser() {
        this.algebraic = new ShuntYardParser();
        this.rpn = new RPNParser();
    }

    /**
     * Parse an agebraic expression entered as a string
     * @param expression string expression
     */
    public void parseExpression(String expression) {
        parseAlgebra(expression);
    }

    /**
     * Parse an equation using the shuntYard algorithm and convert it to RPN
     * @param equation string expression
     */
    private void parseAlgebra(String equation) {
        this.algebraic.parseEquation(equation);
        this.algebraic.printRPN(); //DEBUGGING
        parseRPN(this.algebraic.getRPN());
    }

    /**
     * Parse RPN operators and numbers
     * @param tokens queue of string instructions
     */
    private void parseRPN (Queue<String> tokens) {
        rpn.parseEquation(tokens);
    }

    /**
     * Get the RPN parser being used (only needed for testing)
     * @return the RPN parser
     */
    public RPNParser getRpn() {
        return rpn;
    }

    /**
     * Get the algebraic parser being used (only needed for testing)
     * @return the algebraic parser
     */
    public ShuntYardParser getAlgebraic() {
        return algebraic;
    }
}
