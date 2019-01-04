package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class RPNParser {

    final double a = .00000001; //limit and derivative
    final double pi = Math.PI; //pi

    private String var; //variable letter
    private double vars[]; //variable array (stored using hashcodes)

    private Stack<Double> stack;
    private Queue<String> rpn_queue; //rpn equation

    public RPNParser() {
        this.stack = new Stack<>();
        this.rpn_queue = new LinkedList<>();
        this.vars = new double[26];
        var = "";
    }

    /**
     * Clear the RPN queue
     */
    private void clearRPNQueue() {
        LinkedList<String> removalList = new LinkedList<>();
        removalList.addAll(this.rpn_queue);
        this.rpn_queue.removeAll(removalList);
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
     * Take the sec of the top number in the stack
     */
    private void Sec()
    {
        double a = stack.pop();
        double g = Math.cos(1/a);
        stack.push(g);
    }

    /**
     * Take the csc of the top number in the stack
     */
    private void Csc()
    {
        double a = stack.pop();
        double g = Math.sin(1/a);
        stack.push(g);
    }

    /**
     * Take the cot of the top number in the stack
     */
    private void Cot()
    {
        double a = stack.pop();
        double g = Math.tan(1/a);
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

    /**
     * Take the arcsec of the top number in the stack
     */
    private void Arcsec()
    {
        double a = stack.pop();
        double g = Math.acos(1/a);
        stack.push(g);
    }

    /**
     * Take the arccsc of the top number in the stack
     */
    private void Arccsc()
    {
        double a = stack.pop();
        double g = Math.asin(1/a);
        stack.push(g);
    }

    /**
     * Take the arcsot of the top number in the stack
     */
    private void Arccot()
    {
        double a = stack.pop();
        double g = Math.atan(1/a);
        stack.push(g);
    }

    /**
     * Toggle the sign of the number on the top of the stack
     */
    private void SignToggle()
    {
        double a = stack.pop();
        double g = a * (-1);
        stack.push(g);
    }

    /**
     * Store x with the number on the top of the stack
     */
    private void Store()
    {
        double a = stack.pop();
        vars[code(var)] = a;
        var = "";
    }


    private int code(String s)
    {
        return s.charAt(0) - 'A';
    }

    /**
     * Parse a given set of instructions
     * @param equation a queue (list) of instructions to execute
     */
    public void parseEquation(Queue<String> equation)
    {
        this.rpn_queue = equation;
        parse();
    }

    /**
     * Perform the parsing
     */
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
                case "sec": Sec();
                    break;
                case "csc": Csc();
                    break;
                case "cot": Cot();
                    break;
                case "arcsin": Arcsin();
                    break;
                case "arccos": Arccos();
                    break;
                case "arctan": Arctan();
                    break;
                case "arcsec": Arcsec();
                    break;
                case "arccsc": Arccsc();
                    break;
                case "arccot": Arccot();
                    break;
                case "+/-": SignToggle();
                    break;
                case "Store": Store();
                    break;
                //is a letter to store
                case "A":
                case "B":
                case "C":
                case "D":
                case "E":
                case "F":
                case "G":
                case "H":
                case "I":
                case "J":
                case "K":
                case "L":
                case "M":
                case "N":
                case "O":
                case "P":
                case "Q":
                case "R":
                case "S":
                case "T":
                case "U":
                case "V":
                case "W":
                case "X":
                case "Y":
                case "Z":
                    if (rpn_queue.contains("Store")) { var = s;
                        System.out.println("here"); }
                    else {
                        System.out.println("bleh: " + vars[code(s)]);
                        stack.push(vars[code(s)]); }
                    break;

                //is a number
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9": stack.push(Double.parseDouble(s));
                    break;
                default:
                    System.out.println("Unknown identifier: " + s);
            }
        }
        clearRPNQueue();
    }
}
