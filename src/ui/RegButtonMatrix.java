package ui;

import ui.ButtonMatrix;

public class RegButtonMatrix implements ButtonMatrix {

    private String[][] top;
    private String[][] bottom;

    public RegButtonMatrix ()
    {
        topConfig();
        bottomConfig();
    }

    @Override
    public void topConfig()
    {
        this.top = new String[3][7];

        this.top[0][0] = "Menu";
        this.top[0][1] = "Mode";
        this.top[0][2] = "";
        this.top[0][3] = "";
        this.top[0][4] = "";
        this.top[0][5] = "Up";
        this.top[0][6] = "";

        this.top[1][0] = "";
        this.top[1][1] = "2nd";
        this.top[1][2] = "Alpha";
        this.top[1][3] = "";
        this.top[1][4] = "Left";
        this.top[1][5] = "";
        this.top[1][6] = "Right";

        this.top[2][0] = "";
        this.top[2][1] = "";
        this.top[2][2] = "";
        this.top[2][3] = "";
        this.top[2][4] = "";
        this.top[2][5] = "Down";
        this.top[2][6] = "";
    }

    @Override
    public void bottomConfig()
    {
        this.bottom = new String[8][5];

        this.bottom[0][0] = "y=";
        this.bottom[0][1] = "Calculate";
        this.bottom[0][2] = "Zoom";
        this.bottom[0][3] = "Table";
        this.bottom[0][4] = "Graph";

        this.bottom[1][0] = "Swap";
        this.bottom[1][1] = "Copy";
        this.bottom[1][2] = "Drop";
        this.bottom[1][3] = "Roll";
        this.bottom[1][4] = "Clear";

        this.bottom[2][0] = "Wrt Disk";
        this.bottom[2][1] = "Sin";
        this.bottom[2][2] = "Cos";
        this.bottom[2][3] = "Tan";
        this.bottom[2][4] = "^";

        this.bottom[3][0] = "Root";
        this.bottom[3][1] = "Arcsin";
        this.bottom[3][2] = "Arccos";
        this.bottom[3][3] = "Arctan";
        this.bottom[3][4] = "+";

        this.bottom[4][0] = "Log";
        this.bottom[4][1] = "7";
        this.bottom[4][2] = "8";
        this.bottom[4][3] = "9";
        this.bottom[4][4] = "-";

        this.bottom[5][0] = "Ln";
        this.bottom[5][1] = "4";
        this.bottom[5][2] = "5";
        this.bottom[5][3] = "6";
        this.bottom[5][4] = "*";

        this.bottom[6][0] = "Math";
        this.bottom[6][1] = "1";
        this.bottom[6][2] = "2";
        this.bottom[6][3] = "3";
        this.bottom[6][4] = "/";

        this.bottom[7][0] = "X";
        this.bottom[7][1] = "0";
        this.bottom[7][2] = ".";
        this.bottom[7][3] = "SPC";
        this.bottom[7][4] = "Enter";
    }

    @Override
    public String[][] getTop()
    {
        return this.top;
    }

    @Override
    public String[][] getBottom()
    {
        return this.bottom;
    }
}
