//
//  ShuntYard.cpp
//  
//
//  Created by Jacob Meyerson on 11/28/17.
//

//#include <stdio.h>
//#include <stdlib.h>
//#include <math.h>
//#include <cstdlib>
//#include <string.h>
//#include <string>

#include <iostream>
#include <map>

using namespace std;

//Global Variables
string alg_eq; //algebraic equation

string op_stack[64]; //operator stack
int op_cnt = 0; //operator stack index

string rpn_queue[128]; //rpn (ouput) equation
int rpn_cnt = 0; //rpn (output) index

//Operator stuct definition
//Name of operator, precedence, left/right associativty
struct operator_type
{
    string operation;
    int precedence;
    string associativity;
};

//Operator Map
map<string, operator_type> OperatorMap;

void MakeMap()
{
    /*
	operator_type plus, minus, times, divide, power;
	plus.operation = "+";
	plus.precedence = 4;
	plus.associativity = "l";
	OperatorMap.insert (std::pair<string, operator_type>("+", plus));
    */
    int map_length = 5;
    string oper[8] = {"+", "-", "*", "/", "^"};
    int prec[8] = {4, 4, 3, 3, 2};
    string assoc[8] = {"l", "l", "l", "l", "l"};
    for (int q = 0; q < 5; q++)
    {
        operator_type name;
        name.operation = oper[q];
        name.precedence = prec[q];
        name.associativity = assoc[q];
        OperatorMap.insert (std::pair<string, operator_type>(oper[q], name));
    }
}

void push_rpn (string b)
{
    //cout << "pushing (output) " << b << endl;
    rpn_queue[rpn_cnt] = b;
    rpn_cnt++;
}

void push_op (string c)
{
    //cout << "pushing (op) " << c << endl;
    op_stack[op_cnt] = c;
    op_cnt++;
}

string pop_op ()
{
    string op = op_stack[op_cnt - 1];
    op_stack[op_cnt - 1] = "";
    op_cnt--;
    //cout << "top op of stack = " << op << endl;
    return op;
}

void GetEquation ()
{
    cout << "Enter equation: " << endl;
    getline(cin, alg_eq);
    alg_eq += " ";
}

string get_top_op ()
{
    if (op_cnt > 0)
    {
        return op_stack[op_cnt - 1];
    }
    else
    {
        return "";
    }
}

void Parse ()
{
    string key = "";
    for (int i = 0; i < alg_eq.length(); i++)
    {
        char tok = alg_eq[i];
        //cout << "tok = " << tok << endl;
        if (tok != ' ')
        {
            key += tok;
        }
        if (tok == ' ')
        {
            //cout << "key = " << key << endl;
            if ((OperatorMap.count(key) == 0) && (key != "(") && (key != ")")) //key is a number
            {
                //push number to rpn_queue
                push_rpn(key);
                key.clear();
            }
            else if (key.compare("(") == 0)
            {
                //push open bracket to operator stack
                push_op(key);
                key.clear();
            }
            else if (key.compare(")") == 0)
            {
                //while the operator at the top of the operator stack is not a left bracket:
                        //pop operators from the operator stack onto the output queue.
                //pop the left bracket from the stack.
                while (get_top_op() != "(") 
                {
                    string Operator = pop_op();
                    push_rpn(Operator);
                }
                pop_op();
                key.clear();
            }
            else //(key is an operator)
            {
                // while (there is an operator at the top of the operator stack with greater precedence) or
                //       (the operator at the top of the operator stack has equal precedence and the operator is left associative) and
                //       (the operator at the top of the stack is not a left bracket)
                    //pop operators from the operator stack, onto the output queue.
                //push current operator to op stack
                string curr_key_assoc = OperatorMap.find(key)->second.associativity;
                int curr_key_prec = OperatorMap.find(key)->second.precedence;
                string top_op = get_top_op();
                if (top_op != "")
                {
                    while (((curr_key_prec > OperatorMap.find(top_op)->second.precedence) || (curr_key_prec == OperatorMap.find(top_op)->second.precedence && OperatorMap.find(top_op)->second.associativity == "l")) && (top_op != "("))
                    {
                        string Operator = pop_op();
                        push_rpn(Operator);
                        top_op = get_top_op();
                        if (top_op == "")
                        {
                            break;
                        }
                    }
                }
                push_op(key);
                key.clear();
            }
            key.clear();
        }
    }
    //pop all operators
    while (op_cnt > 0)
    {
        string Operator = pop_op();
        push_rpn(Operator);
    }
}

void TestPopandPush()
{
    //test push_rpn
    cout << rpn_cnt << endl;
    push_rpn("hi");
    push_rpn("yellow");
    cout << rpn_queue[0] << endl;
    cout << rpn_queue[1] << endl << endl;
    
    //test push_op
    cout << op_cnt << endl;
    push_op("+");
    push_op("*");
    cout << op_stack[0] << endl;
    cout << op_stack[1] << endl << endl;
    
    //test pop_op
    cout << op_cnt << endl;
    pop_op();
    cout << op_stack[op_cnt] << endl;
    pop_op();
    cout << op_stack[op_cnt] << endl;
}

void TestMap()
{
    cout << OperatorMap.find("(")->second.precedence << endl;
    cout << OperatorMap.count("5") << endl;
}

void PrintRPN()
{
    for (int e = 0; e < 10; e++)
    {
        cout << rpn_queue[e] << " ";
    }
    cout << endl;
}

int main (void)
{
    GetEquation();
    MakeMap();
    Parse();
    //TestPopandPush();
    //TestMap();
    PrintRPN();
}
