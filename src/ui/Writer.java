package ui;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Writer {
    private int w = 240, h = 160; //uniform scaling
    private int scale_factor_x; //scale x - number of pixels for x box direction
    private int scale_factor_y; //scale y - number of pixels for y box direction
    private int scale_factor; //for uniform scaling maybe?
    private int scale_factor_spacing_x; //scale x spacing - number of pixels between characters printed
    private int scale_factor_spacing_y; //definitely need this

    private int curr_x; //current x pixel position
    private int curr_y; //current y pixel position

    //private WritableImage img;
    private PixelWriter writer;

    public Writer(PixelWriter writer, int w, int h) {
        //w = width of screen, h = height of screen
        //assumes writer has already been formatted
        this.writer = writer;

        this.w = w;
        this.h = h;

        scale_factor_x = w / 40;
        scale_factor_y = (int) Math.round(h / 26.6666666);
        scale_factor_spacing_x = w / 24;
        scale_factor_spacing_y = h / 16;
        scale_factor = (scale_factor_x + scale_factor_y) / 2;

        this.curr_x = 25;
        this.curr_y = 25;
    }

    public void displayText(String text) {
        // Check that we aren't writing off the screen
        if (h <= curr_y + scale_factor_spacing_y + (2 * scale_factor_y)) {
            clearDisplay();
        }
        if (w <= curr_x + (scale_factor_spacing_x * text.length()) + (scale_factor_x * 2 * text.length())) {
            text = text.substring(0, (w)/(scale_factor_spacing_x + scale_factor_x));
        }

        for (int i = 0; i < text.length(); i ++) {
            char s = text.charAt(i);
            printStuff(s, curr_x, curr_y);
            curr_x += scale_factor_spacing_x;
        }
    }

    public void clearDisplay() {
        //Set every pixel color to be white
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++)
            this.writer.setColor(i, j, Color.WHITE);
        }
        curr_x = 25;
        curr_y = 25;
    }

    public void newLine() {
        curr_x = 25;
        curr_y += scale_factor_y * 3;
    }

    private void printStuff(char num, int left_x_coor, int top_y_coor) {
        switch (num) {
            case '0':
                printHexTop(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                printHexLeftTop(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexLeftBot(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                break;
            case '1':
                printHexRightTop(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                break;
            case '2':
                printHexTop(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                printHexLeftBot(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                break;
            case '3':
                printHexTop(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                break;
            case '4':
                printHexLeftTop(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                break;
            case '5':
                printHexTop(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                printHexLeftTop(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                break;
            case '6':
                printHexTop(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                printHexLeftBot(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                printHexLeftTop(left_x_coor, top_y_coor);
                break;
            case '7':
                printHexTop(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                break;
            case '8':
                printHexTop(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                printHexLeftTop(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexLeftBot(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                break;
            case '9':
                printHexTop(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                printHexLeftTop(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                break;
            case '+':
                printPlusSign(left_x_coor, top_y_coor);
                break;
            case '-':
                printMinusSign(left_x_coor, top_y_coor);
                break;
            case '=':
                printEqualSign(left_x_coor, top_y_coor);
                break;
            case '/':
                printDivisionSign(left_x_coor, top_y_coor);
                break;
            case '*':
                printMultiplicationSign(left_x_coor, top_y_coor);
                break;
            case '.':
                printDecimalSign(left_x_coor, top_y_coor);
                break;
        }
    }

    private void printHexTop(int left_x_coor, int top_y_coor) {
        /*
        this.writer.setColor(left_x_coor+1, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+2, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+3, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+4, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+5, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+6, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+7, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+8, top_y_coor, Color.BLUE); */
        for (int i = 1; i < scale_factor_x; i++) {
            this.writer.setColor(left_x_coor + i, top_y_coor, Color.BLUE);
        }
    }

    private void printHexLeftTop(int left_x_coor, int top_y_coor) {
        /*
        this.writer.setColor(left_x_coor, top_y_coor+1, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+2, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+3, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+4, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+5, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+6, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+7, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+8, Color.BLUE);
        */
        for (int i = 1; i < scale_factor_y; i++) {
            this.writer.setColor(left_x_coor, top_y_coor + i, Color.BLUE);
        }
    }

    private void printHexLeftBot(int left_x_coor, int top_y_coor) {
        /*
        this.writer.setColor(left_x_coor, top_y_coor+9, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+10, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+11, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+12, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+13, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+14, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+15, Color.BLUE);
        this.writer.setColor(left_x_coor, top_y_coor+16, Color.BLUE);
        */
        for (int i = scale_factor_y; i < (2 * scale_factor_y); i++) {
            this.writer.setColor(left_x_coor, top_y_coor + i, Color.BLUE);
        }
    }

    private void printHexMid(int left_x_coor, int top_y_coor) {
        /*
        this.writer.setColor(left_x_coor+1, top_y_coor+8, Color.BLUE);
        this.writer.setColor(left_x_coor+2, top_y_coor+8, Color.BLUE);
        this.writer.setColor(left_x_coor+3, top_y_coor+8, Color.BLUE);
        this.writer.setColor(left_x_coor+4, top_y_coor+8, Color.BLUE);
        this.writer.setColor(left_x_coor+5, top_y_coor+8, Color.BLUE);
        this.writer.setColor(left_x_coor+6, top_y_coor+8, Color.BLUE);
        this.writer.setColor(left_x_coor+7, top_y_coor+8, Color.BLUE);
        this.writer.setColor(left_x_coor+8, top_y_coor+8, Color.BLUE);
         */
        for (int i = 1; i < scale_factor_x; i++) {
            this.writer.setColor(left_x_coor + i, top_y_coor + scale_factor_y, Color.BLUE);
        }
    }

    private void printHexBot(int left_x_coor, int top_y_coor) {
        /*
        this.writer.setColor(left_x_coor+1, top_y_coor+17, Color.BLUE);
        this.writer.setColor(left_x_coor+2, top_y_coor+17, Color.BLUE);
        this.writer.setColor(left_x_coor+3, top_y_coor+17, Color.BLUE);
        this.writer.setColor(left_x_coor+4, top_y_coor+17, Color.BLUE);
        this.writer.setColor(left_x_coor+5, top_y_coor+17, Color.BLUE);
        this.writer.setColor(left_x_coor+6, top_y_coor+17, Color.BLUE);
        this.writer.setColor(left_x_coor+7, top_y_coor+17, Color.BLUE);
        this.writer.setColor(left_x_coor+8, top_y_coor+17, Color.BLUE);
         */
        for (int i = 1; i < scale_factor_x; i++) {
            this.writer.setColor(left_x_coor + i, top_y_coor + 2 * scale_factor_y, Color.BLUE);
        }
    }

    private void printHexRightTop(int left_x_coor, int top_y_coor) {
        /*
        this.writer.setColor(left_x_coor+9, top_y_coor+1, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+2, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+3, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+4, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+5, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+6, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+7, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+8, Color.BLUE);
        */
        for (int i = 1; i < scale_factor_y; i++) {
            this.writer.setColor(left_x_coor + scale_factor_x, top_y_coor + i, Color.BLUE); //add 1 to the x coordinate to get italics
        }
    }

    private void printHexRightBot(int left_x_coor, int top_y_coor) {
        /*
        this.writer.setColor(left_x_coor+9, top_y_coor+9, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+10, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+11, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+12, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+13, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+14, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+15, Color.BLUE);
        this.writer.setColor(left_x_coor+9, top_y_coor+16, Color.BLUE);
        */
        for (int i = scale_factor_y; i < (2 * scale_factor_y); i++) {
            this.writer.setColor(left_x_coor + scale_factor_x, top_y_coor + i, Color.BLUE);
        }
    }

    private void printPlusSign(int left_x_coor, int top_y_coor) {
        //draw a horizontal line
        for (int i = 1; i < scale_factor_x; i++) {
            this.writer.setColor(left_x_coor + i, top_y_coor + scale_factor_y, Color.BLUE);
        }

        //draw a vertical line
        for (int i = 1 + (scale_factor_y / 4); i < (scale_factor_y * 2) - (scale_factor_y / 4); i++) {
            this.writer.setColor(left_x_coor + scale_factor_x / 2, top_y_coor + i, Color.BLUE);
        }
    }

    private void printMinusSign(int left_x_coor, int top_y_coor) {
        //draw a horizontal line
        for (int i = 1; i < scale_factor_x; i++) {
            this.writer.setColor(left_x_coor + i, top_y_coor + scale_factor_y, Color.BLUE);
        }
    }

    private void printEqualSign(int left_x_coor, int top_y_coor) {
        //draw top horizontal line
        for (int i = 1; i < scale_factor_x; i++) {
            this.writer.setColor(left_x_coor + i, top_y_coor + scale_factor_y - (scale_factor_y / 3), Color.BLUE);
        }

        //draw bottom horizontal line
        for (int i = 1; i < scale_factor_x; i++) {
            this.writer.setColor(left_x_coor + i, top_y_coor + scale_factor_y + (scale_factor_y / 3), Color.BLUE);
        }
    }

    private void printDivisionSign(int left_x_coor, int top_y_coor) {
        //draw diagonal line
        for (int i = 1; i < scale_factor; i++) {
            this.writer.setColor(left_x_coor + scale_factor_x - i, top_y_coor + 2 * i, Color.BLUE);
        }
    }

    private void printMultiplicationSign(int left_x_coor, int top_y_coor) {
        //draw right to left diagonal line
        for (int i = 4; i < scale_factor - 3; i++) {
            this.writer.setColor(left_x_coor + scale_factor_x - i, top_y_coor + 2 * i, Color.BLUE);
        }

        //draw left to right diagonal line
        for (int i = 4; i < scale_factor - 3; i++) {
            this.writer.setColor(left_x_coor + i, top_y_coor + 2 * i, Color.BLUE);
        }

        //draw a horizontal line
        for (int i = 1; i < scale_factor_x; i += 2) {
            this.writer.setColor(left_x_coor + i, top_y_coor + scale_factor_y, Color.BLUE);
        }

        //draw a vertical line
        for (int i = 1 + (scale_factor_y / 4); i < (scale_factor_y * 2) - (scale_factor_y / 4); i += 2) {
            this.writer.setColor(left_x_coor + scale_factor_x / 2, top_y_coor + i, Color.BLUE);
        }
    }

    private void printDecimalSign(int left_x_coor, int top_y_coor) {
        for (int loop = 0; loop < scale_factor_x/5; loop++) { //loop counter
            for (int i = scale_factor_x / 2; i < 2 * scale_factor_x / 3; i++) { //x position
                for (int j = scale_factor_y - loop; j <= scale_factor_y; j++) { //y position
                    this.writer.setColor(left_x_coor + i, top_y_coor + j + scale_factor_y, Color.BLUE);
                }
            }
        }
    }

    /*private void printMultiplicationSign(int left_x_coor, int top_y_coor) {
        //draw right to left diagonal line
        for (int i = 1; i < scale_factor; i++) {
            this.writer.setColor(left_x_coor + scale_factor_x - i, top_y_coor + 2 * i, Color.BLUE);
        }

        //draw left to right diagonal line
        for (int i = 1; i < scale_factor; i++) {
            this.writer.setColor(left_x_coor + i, top_y_coor + 2 * i, Color.BLUE);
        }
    } */ //use this to print the letter X
}