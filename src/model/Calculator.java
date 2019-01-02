package model;

public class Calculator {

    private String equation;

    public enum Mode {SECOND, ALPHA, REGULAR}
    private Mode mode;

    private RPNParser rpn;
    private ShuntYardParser shuntyard;

    public enum CharType {NULL, OPERATOR, NUMBER, SPACE}

    public Calculator()
    {
        this.mode = Mode.REGULAR;
        this.equation = "";
        this.rpn = new RPNParser();
        this.shuntyard = new ShuntYardParser();
    }

    public Mode getMode()
    {
        return this.mode;
    }

    public void setMode(Mode mode)
    {
        this.mode = mode;
    }

    public void addEquation(String node) {
        this.equation += node;
    }

    public void clearEquation() {
        this.equation = "";
    }

    public String getEquation() {
        return equation;
    }

    public RPNParser getRpn() {
        return rpn;
    }

    public void interpret() {
    }

    public void parse() {
        formatEquation();
        this.shuntyard.parseEquation(this.equation);
        this.rpn.parseEquation(this.shuntyard.getRPN());
        System.out.println("Answer is: " + this.rpn.getTop());
    }

    /**
     * Convert the string equation created from the buttons on the calculator to a properly formatted (with spaces) algebraic equation to be parsed
     */
    private void formatEquation() {
        CharType prevChar = CharType.NULL;
        CharType currChar;
        String newEquation = "";
        for (int i = 0; i < equation.length(); i++)
        {
            if ((equation.charAt(i) >= '0' && equation.charAt(i) <= '9') || equation.charAt(i) == '.') { //i is a digit or .
                currChar = CharType.NUMBER;
            }
            else if (equation.charAt(i) == ' ') {
                currChar = CharType.SPACE;
            }
            else {
                currChar = CharType.OPERATOR;
            }

            if (currChar == CharType.OPERATOR && prevChar == CharType.NUMBER) { newEquation += " " + equation.charAt(i); }
            else { newEquation += equation.charAt(i); }
            prevChar = currChar;
        }
        System.out.println(newEquation);
        this.equation = newEquation;
    }
}
