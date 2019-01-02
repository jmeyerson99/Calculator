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
                            case "0":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "1":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "2":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "3":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "4":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "5":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "6":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "7":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "8":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "9":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case ".":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "SPC":
                                this.calculator.addEquation(" ");
                                console.setText(calculator.getEquation());
                                break;
                            case "Enter":
                                this.calculator.parse();
                                this.calculator.clearEquation();
                                console.setText(Double.toString(calculator.getRpn().getTop()));
                                break;
                            //OPERATIONS-----------------------------------
                            case "sin":
                                this.calculator.addEquation(cmd + " ");
                                console.setText(calculator.getEquation());
                                break;
                            case "cos":
                                this.calculator.addEquation(cmd + " ");
                                console.setText(calculator.getEquation());
                                break;
                            case "tan":
                                this.calculator.addEquation(cmd + " ");
                                console.setText(calculator.getEquation());
                                break;
                            case "arcsin":
                                this.calculator.addEquation(cmd + " ");
                                console.setText(calculator.getEquation());
                                break;
                            case "arccos":
                                this.calculator.addEquation(cmd + " ");
                                console.setText(calculator.getEquation());
                                break;
                            case "arctan":
                                this.calculator.addEquation(cmd + " ");
                                console.setText(calculator.getEquation());
                                break;
                            case "Clear":
                                //clear
                                this.calculator.clearEquation();
                                console.setText(calculator.getEquation());
                                break;
                            case "+":
                                this.calculator.addEquation(cmd + " ");
                                console.setText(calculator.getEquation());
                                break;
                            case "-":
                                this.calculator.addEquation(cmd + " ");
                                console.setText(calculator.getEquation());
                                break;
                            case "*":
                                this.calculator.addEquation(cmd + " ");
                                console.setText(calculator.getEquation());
                                break;
                            case "/":
                                this.calculator.addEquation(cmd + " ");
                                console.setText(calculator.getEquation());
                                break;
                            case "^":
                                this.calculator.addEquation(cmd + " ");
                                console.setText(calculator.getEquation());
                                break;
                            case "+/-":
                                this.calculator.addEquation(cmd + " ");
                                console.setText(calculator.getEquation());
                                break;
                            //MENUS-------------------------------------
                        }
                        break;

                    case SECOND:
                        switch (cmd) {
                            case "sec":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "csc":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "cot":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "arcsec":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "arccsc":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                            case "arccot":
                                this.calculator.addEquation(cmd);
                                console.setText(calculator.getEquation());
                                break;
                        }
                        break;

                    case ALPHA:
                        break;
                }


            }
        }
    }
}
