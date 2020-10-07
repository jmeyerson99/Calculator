import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class RPNParser {

    final double a = .00000001; //limit and derivative
    final double pi = Math.PI; //pi

    private Stack<Double> stack;
    private Queue<String> rpn_queue; //rpn equation

    public RPNParser() {
        this.stack = new Stack<>();
        this.rpn_queue = new LinkedList<>();
    }

    /**
     * Get the top element on the stack of doubles
     * @return the top of the stack
     */
    public double getTop() {
        return stack.peek();
    }

    /**
     * Swap two objects from the stack
     */
    private void Swap()
    {
        double f = stack.pop();
        double k = stack.pop();
        stack.push(f);
        stack.push(k);
    }

    /**
     * Copy the top element of the stack and duplcate it
     */
    private void Copy()
    {
        double t = stack.pop();
        stack.push(t);
        stack.push(t);
    }

    /**
     * Remove the top element of the stack
     */
    private void Drop()
    {
        stack.pop();
    }

    /**
     *
     */
    private void Roll()
    {
        double u = stack.pop();
        double v = stack.pop();
        double z = stack.pop();
        stack.push(v);
        stack.push(u);
        stack.push(z);
    }

    /**
     * Add top two numbers of stack
     */
    private void Add()
    {
        double a = stack.pop();
        double b = stack.pop();
        double g = b + a;
        stack.push(g);
    }

    /**
     * Subtract second number on stack by first number on stack
     */
    private void Subtract()
    {
        double a = stack.pop();
        double b = stack.pop();
        double g = b - a;
        stack.push(g);
    }

    /**
     * Multiply top two numbers of stack
     */
    private void Multiply()
    {
        double a = stack.pop();
        double b = stack.pop();
        double g = b * a;
        stack.push(g);
    }

    /**
     * Divide second number on stack by first number on stack
     */
    private void Divide()
    {
        double a = stack.pop();
        double b = stack.pop();
        double g = b / a;
        stack.push(g);
    }

    /**
     * Raise second number on stack to power of first number on stack
     */
    private void Power()
    {
        double a = stack.pop();
        double b = stack.pop();
        double g = Math.pow(b, a);
        stack.push(g);
    }

    /**
     * Take the ln of the top number in the stack
     */
    private void Ln()
    {
        double a = stack.pop();
        double g = Math.log(a);
        stack.push(g);
    }

    /**
     * Take the sin of the top number in the stack
     */
    private void Sin()
    {
        double a = stack.pop();
        double g = Math.sin(a);
        stack.push(g);
    }

    /**
     * Take the cos of the top number in the stack
     */
    private void Cos()
    {
        double a = stack.pop();
        double g = Math.cos(a);
        stack.push(g);
    }

    /**
     * Take the tan of the top number in the stack
     */
    private void Tan()
    {
        double a = stack.pop();
        double g = Math.tan(a);
        stack.push(g);
    }

    /**
     * Take the arcsin of the top number in the stack
     */
    private void Arcsin()
    {
        double a = stack.pop();
        double g = Math.asin(a);
        stack.push(g);
    }

    /**
     * Take the arccos of the top number in the stack
     */
    private void Arccos()
    {
        double a = stack.pop();
        double g = Math.acos(a);
        stack.push(g);
    }

    /**
     * Take the arctan of the top number in the stack
     */
    private void Arctan()
    {
        double a = stack.pop();
        double g = Math.atan(a);
        stack.push(g);
    }

    public void parseEquation(Queue<String> equation)
    {
        this.rpn_queue = equation;
        parse();
    }

    public void parse()
    {
        for (String s : this.rpn_queue) {
            switch (s) {
                case "+": Add();
                    break;
                case "-": Subtract();
                    break;
                case "*": Multiply();
                    break;
                case "/": Divide();
                    break;
                case "^": Power();
                    break;
                case "ln": Ln();
                    break;
                case "Drop": Drop();
                    break;
                case "Swap": Swap();
                    break;
                case "Copy": Copy();
                    break;
                case "Roll": Roll();
                    break;
                case "sin": Sin();
                    break;
                case "cos": Cos();
                    break;
                case "tan": Tan();
                    break;
                case "arcsin": Arcsin();
                    break;
                case "arccos": Arccos();
                    break;
                case "arctan": Arctan();
                    break;
                default: //is a number
                    stack.push(Double.parseDouble(s));
                    break;
            }
        }
    }
}
