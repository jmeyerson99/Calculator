import java.util.Queue;
import java.util.Scanner;

public class ParsingTester {

    private ShuntYardParser shuntyard;
    private RPNParser rpn;

    public ParsingTester() {
        this.shuntyard = new ShuntYardParser();
        this.rpn = new RPNParser();
    }

    public void ParseAlgebra(String equation) {
        this.shuntyard.parseEquation(equation);

        System.out.print("The RPN is: ");
        this.shuntyard.printRPN();

        ParseRPN(this.shuntyard.getRPN());
    }

    public void ParseRPN (Queue<String> tokens) {
        rpn.parseEquation(tokens);
        System.out.println("The answer is " + rpn.getTop());
    }

    public static void main(String[] args) {
        ParsingTester tester = new ParsingTester();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an expression: ");
        String equation = scanner.nextLine() + " ";

        tester.ParseAlgebra(equation);
    }
}
