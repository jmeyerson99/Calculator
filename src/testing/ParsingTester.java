package testing;

import appl.Parser;
import model.RPNParser;
import model.ShuntYardParser;

import java.util.Queue;
import java.util.Scanner;

public class ParsingTester {

    private ShuntYardParser shuntyard;
    private RPNParser rpn;
    private Parser parser;

    public ParsingTester() {
        this.shuntyard = new ShuntYardParser();
        this.rpn = new RPNParser();
        this.parser = new Parser();
    }

    public void parse(String equation)
    {
        
    }

    public void parseAlgebra(String equation) {
        this.shuntyard.parseEquation(equation);

        System.out.print("The RPN is: ");
        this.shuntyard.printRPN();

        parseRPN(this.shuntyard.getRPN());
    }

    public void parseRPN (Queue<String> tokens) {
        rpn.parseEquation(tokens);
        System.out.println("The answer is " + rpn.getTop());
    }

    public static void main(String[] args) {
        ParsingTester tester = new ParsingTester();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an expression: ");
        String equation = scanner.nextLine();// + " ";

        tester.parse(equation);
    }
}
