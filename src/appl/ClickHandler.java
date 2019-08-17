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

    /**
     * Create a click handler for a button
     * @param console the display
     * @param calculator the calculator
     * @param buttons all buttons on the calculator
     */
    public ClickHandler(TextArea console, Calculator calculator, ArrayList<CalcularButton> buttons)
    {
        this.console = console;
        this.calculator = calculator;
        this.buttons = buttons;
    }

    /**
     * Handle any type of button click across all modes
     * @param event button click
     */
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
                            case "1":
                            case "2":
                            case "3":
                            case "4":
                            case "5":
                            case "6":
                            case "7":
                            case "8":
                            case "9":
                            case ".":
                                this.calculator.addEquation(cmd);
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "SPC":
                                this.calculator.addEquation(" ");
                                this.calculator.addDisplayMsg(" ");
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
                            case "cos":
                            case "tan":
                            case "arcsin":
                            case "arccos":
                            case "arctan":
                            case "+":
                            case "-":
                            case "*":
                            case "/":
                            case "^":
                            case "+/-":
                            case "X":
                            case "ln":
                            case "log":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd);
                                break;
                            case "Roll":
                                this.calculator.addEquation(cmd + " ");
                                this.calculator.addDisplayMsg(cmd + " ");
                                break;
                            case "Clear":
                                //clear
                                this.calculator.clearEquation();
                                this.calculator.clearDisplayMsg();
                                break;
                            //MENUS-------------------------------------
                        }
                        break;

                    case SECOND:
                        switch (cmd) {
                            //OPERATIONS--------------------------------
                            case "sec":
                            case "csc":
                            case "cot":
                            case "arcsec":
                            case "arccsc":
                            case "arccot":
                            case "(":
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
                                this.calculator.addDisplayMsg(" " + cmd + " ");
                                break;
                        }
                        break;

                    case ALPHA:
                        switch (cmd) {
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
                        }
                        break;
                }
                console.setText(calculator.getDisplay()); //set the text
                console.appendText(""); //notify the listener so the automatic scrolling happens
                if (cmd.equals("Enter")) {this.calculator.clearDisplayMsg();}
            }
        }
    }
}
