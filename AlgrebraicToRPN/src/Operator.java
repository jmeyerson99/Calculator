public class Operator {

    /*
    Public and immutable
     */
    final String operation;     //name of operation
    final int precedence;       //operation's precedence
    final String associativity; //operation's associativity ("l" or "r")

    public Operator (String operation, int precedence, String associativity)
    {
        this.operation = operation;
        this.precedence = precedence;
        this.associativity = associativity;
    }
}
