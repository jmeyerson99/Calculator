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

    public void parseExpression(String expression) {
        parseAlgebra(expression);
    }

    private void parseAlgebra(String equation) {
        this.algebraic.parseEquation(equation);

        //DEBUGGING PURPOSES
        //System.out.print("The RPN is: ");
        //this.shuntyard.printRPN();

        parseRPN(this.algebraic.getRPN());
    }

    private void parseRPN (Queue<String> tokens) {
        rpn.parseEquation(tokens);
        //System.out.println("The answer is " + rpn.getTop());
    }

}
