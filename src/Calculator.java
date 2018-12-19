import java.util.ArrayList;

public class Calculator {

    private ArrayList<String> commandQueue;

    public Calculator()
    {
        this.commandQueue = new ArrayList<>();
    }

    public void handleButton(String cmd, String mode)
    {
        if (mode.equals("Regular")) {
            switch (cmd) {
                case ("7"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("4"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("1"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("0"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("8"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("5"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("2"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("."):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("9"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("6"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("3"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case (" "):
                    this.commandQueue.add(" ");
                    DisplayText(5, 133, 8, " ");
                    break;
                case ("Enter"):
                    Interpret(commandQueue);
                    for(String c : commandQueue)
                    {
                        commandQueue.remove(c);
                    }
                    break;
                //OPERATIONS-----------------------------------
                case ("Y="):
                    //go into graphing mode
                    break;
                case ("Swap"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Sqrt"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Log"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Ln"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("X"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Copy"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Sin"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Arcsin"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Drop"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Cos"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Arccos"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Roll"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Tan"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Arctan"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("Clear"):
                    //clear
                    break;
                case ("^"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("+"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("-"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("*"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                case ("/"):
                    this.commandQueue.add(cmd);
                    DisplayText(5, 133, 8, cmd);
                    break;
                //MENUS-------------------------------------
               //case (7):
                //    MathMenu();
                 //   break;
            }
        }
        else if (mode.equals("2nd"))
        {

        }
        else if (mode.equals("Alpha"))
        {

        }
    }

    private void DisplayText(int i, int i1, int i2, String s) {
    }

    private void Interpret(ArrayList<String> commandQueue) {
    }
}
