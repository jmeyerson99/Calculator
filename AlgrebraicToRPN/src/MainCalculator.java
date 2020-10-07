import java.util.Queue;
import java.util.Scanner;

public class MainCalculator {

    private ShuntYardParser shuntyard;
    private RPNParser rpn;

    public MainCalculator() {
        this.shuntyard = new ShuntYardParser();
        this.rpn = new RPNParser();
    }

    public void ParseAlgebra(String equation) {

        this.shuntyard.parseEquation(equation);

        //DEBUGGING PURPOSES
        //System.out.print("The RPN is: ");
        //this.shuntyard.printRPN();

        ParseRPN(this.shuntyard.getRPN());
    }

    public void ParseRPN (Queue<String> tokens) {
        rpn.parseEquation(tokens);
        System.out.println("The answer is " + rpn.getTop());
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Start by entering an expression");
        MainCalculator calculator = new MainCalculator();
        while (true) {
            String input = (scanner.nextLine() + " ");
            if (input.equals("q ")) { System.exit(0); System.out.println("Bye"); }
           calculator.ParseAlgebra(input);
        }
    }
}
