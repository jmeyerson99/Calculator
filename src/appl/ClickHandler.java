package appl;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import model.Calculator;

import model.Calculator.Mode;
import ui.CalcularButton;

import java.util.ArrayList;

/**
 * Handles a click on a button and communicates between brains (model) and view (ui)
 */
public class ClickHandler implements EventHandler {

    //Console (Screen)
    private TextArea console;

    //All the buttons so they all change (cause I forget how to do observer patterns)
    private ArrayList<CalcularButton> buttons;

    //The calculator itself
    private Calculator calculator;

    public ClickHandler(TextArea console, Calculator calculator, ArrayList<CalcularButton> buttons)
    {
        this.console = console;
        this.calculator = calculator;
        this.buttons = buttons;
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() instanceof Button)
        {
            Button button = (Button) event.getSource();
            if (!button.getText().equals("")) { //don't handle buttons that aren't used for anything
                Mode currMode = calculator.getMode();
                String cmd = button.getText();

                //This switch handles mode changes between 2nd & Alpha
                switch (cmd) {
                    case "2nd":
                        if (!currMode.equals(Mode.SECOND)) {
                            for (CalcularButton b : buttons) { b.setText(b.getSecondName()); }
                            calculator.setMode(Mode.SECOND);
                        } else {
                            for (CalcularButton b : buttons) { b.setText(b.getRegName()); }
                            calculator.setMode(Mode.REGULAR);
                        }
                        break;
                    case "Alpha":
                        if (!currMode.equals(Mode.ALPHA)) {
                            for (CalcularButton b : buttons) { b.setText(b.getAlphaName()); }
                            calculator.setMode(Mode.ALPHA);
                        } else {
                            for (CalcularButton b : buttons) { b.setText(b.getRegName()); }
                            calculator.setMode(Mode.REGULAR);
                        }
                        break;
                }

                //This switch handles all other buttons based on the calculator's mode
                switch (calculator.getMode()) {
                    case REGULAR:
                        switch (cmd) {
                            //NUMBERS--------------------------------
                            case "0":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "1":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "2":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "3":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "4":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "5":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "6":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "7":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "8":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "9":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case ".":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "SPC":
                                this.calculator.addEquation(" ");
                                this.calculator.addDisplayMsg(" ");
                                break;
                            case "X":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "Enter":
                                this.calculator.parse();
                                this.calculator.clearEquation();
                                this.calculator.clearDisplayMsg();
                                try { //use a try catch in case there is no number at the top of the stack
                                    this.calculator.addDisplayMsg(Double.toString(calculator.getRpn().getTop())); } catch (Exception e) {}
                                break;
                            //OPERATIONS-----------------------------------
                            case "sin":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "cos":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "tan":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "arcsin":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "arccos":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "arctan":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "Clear":
                                //clear
                                this.calculator.clearEquation();
                                this.calculator.clearDisplayMsg();
                                break;
                            case "+":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "-":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "*":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "/":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "^":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "+/-":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            //MENUS-------------------------------------
                        }
                        break;

                    case SECOND:
                        switch (cmd) {
                            //OPERATIONS--------------------------------
                            case "sec":
                                this.calculator.addEquation(cmd+ " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "csc":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "cot":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "arcsec":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "arccsc":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "arccot":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "(":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case ")":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "Enter":
                                this.calculator.parse();
                                this.calculator.clearEquation();
                                this.calculator.clearDisplayMsg();
                                try { //use a try catch in case there is no number at the top of the stack
                                this.calculator.addDisplayMsg(Double.toString(calculator.getRpn().getTop())); } catch (Exception e) {}
                                break;
                            case "Store":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(" " + cmd);
                                break;
                        }
                        break;

                    case ALPHA:
                        break;
                }
                console.setText(calculator.getDisplay()); //set the text
                console.appendText(""); //notify the listener
                if (cmd.equals("Enter")) {this.calculator.clearDisplayMsg();}
            }
        }
    }
}
