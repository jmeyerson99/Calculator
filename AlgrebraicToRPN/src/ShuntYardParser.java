import java.util.*;

public class ShuntYardParser {

    //Constants
    private final String[] oper = {"+", "-", "*", "/", "^", "ln", "sin", "cos", "tan", "arcsin", "arccos", "arctan"};
    private final int[] prec = {4, 4, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1};
    private final String[] assoc = {"l", "l", "l", "l", "l", "l", "l", "l", "l", "l", "l", "l"};

    //Global Variables
    private String alg_eq; //algebraic equation
    private Stack<String> op_stack; //operator stack
    private Queue<String> rpn_queue; //rpn (ouput) equation
    private Map<String, Operator> operators; //operator map

    //Constructor
    public ShuntYardParser() {
        this.op_stack = new Stack<>();
        this.rpn_queue = new LinkedList<>();
        MakeMap(); //Create operator lookup table
    }

    /**
     * Create the lookup map of operators
     */
    private void MakeMap()
    {
        this.operators = new HashMap<>();
        /* Example:
        plus.operation = "+";
        plus.precedence = 4;
        plus.associativity = "l";
        operators.put(operator.operator, operator)
        */
        int numberofOperators = oper.length;
        for (int q = 0; q < numberofOperators; q++)
        {
            Operator operator = new Operator(oper[q], prec[q], assoc[q]);
            operators.put(operator.operation, operator);
        }
    }

    /**
     * Push a token to the RPN output queue
     * @param b token to push
     */
    private void pushRPN(String b)
    {
        rpn_queue.add(b);
    }

    /**
     * Push an operator to the operator stack
     * @param b operator to push
     */
    private void pushOp(String b)
    {
        op_stack.push(b);
    }

    /**
     * Pop an operator from the operator stack
     * @return next string operator
     */
    private String popOp()
    {
        return op_stack.pop();
    }

    /**
     * View operator on top of operator stack (if it exists)
     * @return operator on top of operator stack (null if empty)
     */
    private String topOp()
    {
        if (op_stack.empty()) { return null; }
        return op_stack.peek();
    }

    /**
     * Print the RPN equation
     */
    public void printRPN()
    {
        for (String s : this.rpn_queue)
        {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    /**
     * Get an equation from the user
     */
    private void getEquation()
    {
        Scanner scanner = new Scanner(System.in);
        this.alg_eq = scanner.nextLine();
        this.alg_eq += " ";
    }

    /**
     * Get the RPN queue of instructions
     * @return Queue of String instructions
     */
    public Queue<String> getRPN()
    {
        return this.rpn_queue;
    }

    /**
     * Parse a given algebraic equation
     * @param equation string equation
     */
    public void parseEquation(String equation)
    {
        this.alg_eq = equation;
        parse();
    }

    /**
     * Parse the equation (stored as the field)
     */
    public void parse()
    {
        String key = "";
        for (int i = 0; i < alg_eq.length(); i++)
        {
            char tok = alg_eq.charAt(i);

            if (tok != ' ') { key += tok; }

            if (tok == ' ') {
                if (!(operators.containsKey(key)) &&  (!(key.equals("(")) && !(key.equals(")")))) { //if key is a number
                    pushRPN(key);
                    key = "";
                }
                else if (key.equals("(")) { //else if key is open parentheses
                    pushOp(key);
                    key = "";
                }
                else if (key.equals(")")) { //else if key is closed parentheses
                    /* Algorithm
                    while the operator at the top of the operator stack is not a left bracket:
                      pop operators from the operator stack onto the output queue.
                    pop the left bracket from the stack.
                    */
                    while (!(topOp().equals("(")))
                    {
                        String Operator = popOp();
                        pushRPN(Operator);
                    }
                    popOp();
                    key = "";
                }
                else { //else (key is an operator)

                    /* Algorithm
                     while (there is an operator at the top of the operator stack with greater precedence) or
                           (the operator at the top of the operator stack has equal precedence and the operator is left associative) and
                           (the operator at the top of the stack is not a left bracket)
                    pop operators from the operator stack, onto the output queue.
                    push current operator to op stack
                    */
                    String currentKeyAssoc = operators.get(key).associativity;
                    int currentKeyPrec = operators.get(key).precedence;
                    String topOp = topOp();
                    if (topOp != null) {
                        while (currentKeyAssoc.equals("l") && !(topOp.equals("(")) && currentKeyPrec >= operators.get(topOp).precedence)
                        {
                            String operator = popOp();
                            pushRPN(operator);
                            topOp = topOp();
                            if (topOp == null)
                            {
                                break;
                            }
                        }
                    }
                    pushOp(key);
                    key = "";
                }
            }
        }
        //pop all operators
        while (topOp() != null)
        {
            pushRPN(popOp());
        }
    }
}
