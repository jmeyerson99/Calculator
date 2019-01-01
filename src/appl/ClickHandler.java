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
                System.out.println(button.getText());
                console.setText(button.getText() + " was clicked...");

                Mode currMode = calculator.getMode();

                if (button.getText().equals("2nd"))
                {
                    if (!currMode.equals(Mode.SECOND)) {
                        for (CalcularButton b : buttons) {
                            b.setText(b.getSecondName());
                        }
                        calculator.setMode(Mode.SECOND);
                    }
                    else {
                        for (CalcularButton b : buttons)
                        {
                            b.setText(b.getRegName());
                        }
                        calculator.setMode(Mode.REGULAR);
                    }
                }
                else if (button.getText().equals("Alpha"))
                {
                    if (!currMode.equals("Alpha")) {
                        for (CalcularButton b : buttons) {
                            b.setText(b.getAlphaName());
                        }
                        calculator.setMode(Mode.ALPHA);
                    }
                    else {
                        for (CalcularButton b : buttons)
                        {
                            b.setText(b.getRegName());
                        }
                        calculator.setMode(Mode.REGULAR);
                    }
                }
                else if (button.getText().equals("Regular"))
                {
                    for (CalcularButton b : buttons) {
                        b.setText(b.getRegName());
                    }
                    calculator.setMode(Mode.REGULAR);
                }
            }
        }
    }
}
