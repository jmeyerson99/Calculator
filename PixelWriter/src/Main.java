import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Main calculator simulation class
 */
public class Main extends Application {

    private int w = 240, h = 160; //uniform scaling
    private int scale_factor_x; //scale x - number of pixels for x box direction
    private int scale_factor_y; //scale y - number of pixels for y box direction
    private int scale_factor; //for uniform scaling maybe?
    private int scale_factor_spacing_x; //scale x spacing - number of pixels between characters printed
    private int scale_factor_spacing_y; //definitely need this

    private WritableImage img;
    private PixelWriter writer;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        //Set scale factor for uniform scaling here, otherwise use x and y values as needed
        w = w*4;
        h = h*4;

        scale_factor_x = w / 40;
        scale_factor_y = (int) Math.round(h / 26.6666666);
        scale_factor_spacing_x = w / 24;
        scale_factor_spacing_y = h / 16;
        scale_factor = (scale_factor_x + scale_factor_y) / 2;

        this.img = new WritableImage(w, h);
        this.writer = img.getPixelWriter();

        int x = 25;
        int y = 25;
        ArrayList<String> testArray = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "=", "/", "*", "."));
        for (String s : testArray) {
            printStuff(s, x, y);
            x += scale_factor_spacing_x;
        }

        String s = "9+0=9";
        x = 25;
        y = y + scale_factor_spacing_y * 2;
        for (int c = 0; c < s.length(); c++) {
            printStuff(Character.toString(s.charAt(c)), x, y);
            x += scale_factor_spacing_x;
        }
        ImageView view = new ImageView(img);
        HBox root = new HBox(view);
        Scene scene = new Scene(root, w, h);
        stage.setScene(scene);
        stage.setTitle("yeet");
        stage.show();

    }

    private void printStuff(String num, int left_x_coor, int top_y_coor)
    {
        switch (num) {
            case "0":
                printHexTop(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                printHexLeftTop(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexLeftBot(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                break;
            case "1":
                printHexRightTop(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                break;
            case "2":
                printHexTop(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                printHexLeftBot(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                break;
            case "3":
                printHexTop(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                break;
            case "4":
                printHexLeftTop(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                break;
            case "5":
                printHexTop(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                printHexLeftTop(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                break;
            case "6":
                printHexTop(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                printHexLeftBot(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                printHexLeftTop(left_x_coor, top_y_coor);
                break;
            case "7":
                printHexTop(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                break;
            case "8":
                printHexTop(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                printHexLeftTop(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexLeftBot(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                break;
            case "9":
                printHexTop(left_x_coor, top_y_coor);
                printHexBot(left_x_coor, top_y_coor);
                printHexLeftTop(left_x_coor, top_y_coor);
                printHexRightTop(left_x_coor, top_y_coor);
                printHexMid(left_x_coor, top_y_coor);
                printHexRightBot(left_x_coor, top_y_coor);
                break;
            case "+":
                printPlusSign(left_x_coor, top_y_coor);
                break;
            case "-":
                printMinusSign(left_x_coor, top_y_coor);
                break;
            case "=":
                printEqualSign(left_x_coor, top_y_coor);
                break;
            case "/":
                printDivisionSign(left_x_coor, top_y_coor);
                break;
            case "*":
                printMultiplicationSign(left_x_coor, top_y_coor);
                break;
            case ".":
                printDecimalSign(left_x_coor, top_y_coor);
                break;
        }
    }

    private void printHexTop(int left_x_coor, int top_y_coor)
    {
        /*
        this.writer.setColor(left_x_coor+1, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+2, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+3, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+4, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+5, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+6, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+7, top_y_coor, Color.BLUE);
        this.writer.setColor(left_x_coor+8, top_y_coor, Color.BLUE); */
        for (int i = 1; i < scale_factor_x; i++)
        {
            this.writer.setColor(left_x_coor + i, top_y_coor, Color.BLUE);
        }
    }

    private void printHexLeftTop(int left_x_coor, int top_y_coor)
    {
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
        for (int i = 1; i < scale_factor_y; i++)
        {
            this.writer.setColor(left_x_coor, top_y_coor + i, Color.BLUE);
        }
    }

    private void printHexLeftBot(int left_x_coor, int top_y_coor)
    {
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
        for (int i = scale_factor_y; i < (2 * scale_factor_y); i++)
        {
            this.writer.setColor(left_x_coor, top_y_coor + i, Color.BLUE);
        }
    }

    private void printHexMid(int left_x_coor, int top_y_coor)
    {
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
        for (int i = 1; i < scale_factor_x; i++)
        {
            this.writer.setColor(left_x_coor + i, top_y_coor + scale_factor_y, Color.BLUE);
        }
    }

    private void printHexBot(int left_x_coor, int top_y_coor)
    {
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
        for (int i = 1; i < scale_factor_x; i++)
        {
            this.writer.setColor(left_x_coor + i, top_y_coor + 2 * scale_factor_y, Color.BLUE);
        }
    }

    private void printHexRightTop(int left_x_coor, int top_y_coor)
    {
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
        for (int i = 1; i < scale_factor_y; i++)
        {
            this.writer.setColor(left_x_coor + scale_factor_x, top_y_coor + i, Color.BLUE); //add 1 to the x coordinate to get italics
        }
    }

    private void printHexRightBot(int left_x_coor, int top_y_coor)
    {
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
        for (int i = scale_factor_y; i < (2 * scale_factor_y); i++)
        {
            this.writer.setColor(left_x_coor + scale_factor_x, top_y_coor + i, Color.BLUE);
        }
    }

    private void printPlusSign(int left_x_coor, int top_y_coor)
    {
        //draw a horizontal line
        for (int i = 1; i < scale_factor_x; i++)
        {
            this.writer.setColor(left_x_coor + i, top_y_coor + scale_factor_y, Color.BLUE);
        }

        //draw a vertical line
        for (int i = 1 + (scale_factor_y / 4); i < (scale_factor_y * 2) - (scale_factor_y / 4); i++)
        {
            this.writer.setColor(left_x_coor + scale_factor_x / 2, top_y_coor + i, Color.BLUE);
        }
    }

    private void printMinusSign(int left_x_coor, int top_y_coor)
    {
        //draw a horizontal line
        for (int i = 1; i < scale_factor_x; i++)
        {
            this.writer.setColor(left_x_coor + i, top_y_coor + scale_factor_y, Color.BLUE);
        }
    }

    private void printEqualSign(int left_x_coor, int top_y_coor)
    {
        //draw top horizontal line
        for (int i = 1; i < scale_factor_x; i++)
        {
            this.writer.setColor(left_x_coor + i, top_y_coor + scale_factor_y - (scale_factor_y / 3), Color.BLUE);
        }

        //draw bottom horizontal line
        for (int i = 1; i < scale_factor_x; i++)
        {
            this.writer.setColor(left_x_coor + i, top_y_coor + scale_factor_y + (scale_factor_y / 3), Color.BLUE);
        }
    }

    private void printDivisionSign(int left_x_coor, int top_y_coor)
    {
        //draw diagonal line
        for (int i = 1; i < scale_factor; i++)
        {
            this.writer.setColor(left_x_coor + scale_factor_x - i, top_y_coor + 2*i, Color.BLUE);
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
                    System.out.println(j);
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

