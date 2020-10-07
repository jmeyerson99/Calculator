package ui;

import javafx.scene.control.Button;

public class CalcularButton extends Button {

    private String regName;
    private String secondName;
    private String alphaName;
    private int row;
    private int col;

    public CalcularButton (String regName, String secondName, String alphaName, int row, int col)
    {
        this.regName = regName;
        this.secondName = secondName;
        this.alphaName = alphaName;
        this.row = row;
        this.col = col;
    }

    public int getRow()
    {
        return this.row;
    }

    public int getCol()
    {
        return this.col;
    }

    public String getRegName() {
        return regName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getAlphaName() {
        return alphaName;
    }
}
