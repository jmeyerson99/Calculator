import appl.ClickHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Calculator;
import ui.*;

import java.util.ArrayList;

/**
 * Main calculator simulation class, uses a PixelWriter instead of a TextArea
 */
public class CalcularMainPixelWriter extends Application
{
    private String paneMsg = "Calcular v0.2";

    private String[][] regTopNames;
    private String[][] regBottomNames;

    private String[][] secTopNames;
    private String[][] secBottomNames;

    private String[][] alphaTopNames;
    private String[][] alphaBottomNames;

    private int w = 480, h = 320; //screen size (default is 240 x 160)

    /*
    Bottom grid info - 5 columns, 8 rows
     */
    private final int bottomRows = 8;
    private final int bottomCols = 5;

    /*
    Top grid info - 3 rows, 7 columns
     */
    private final int topRows = 3;
    private final int topCols = 7;

    //uses text console
    //private TextArea console;

    //uses pixel console
    private ImageView view;
    private Writer writer;

    private ArrayList<CalcularButton> topButtons;
    private ArrayList<CalcularButton> botButtons;

    private ArrayList<CalcularButton> allButtons;

    private Calculator calculator;

    @Override
    public void start(Stage stage) {
        this.topButtons = new ArrayList<>();
        this.botButtons = new ArrayList<>();
        this.allButtons = new ArrayList<>();
        this.calculator = new Calculator();

        RegButtonMatrix bx = new RegButtonMatrix();
        this.regTopNames = bx.top;
        this.regBottomNames = bx.bottom;

        SecondButtonMatrix b = new SecondButtonMatrix();
        this.secTopNames = b.top;
        this.secBottomNames = b.bottom;

        AlphaButtonMatrix x = new AlphaButtonMatrix();
        this.alphaTopNames = x.top;
        this.alphaBottomNames = x.bottom;

        Scene sc = new Scene(makeView());
        stage.setScene(sc);
        stage.setTitle(this.paneMsg);
        stage.show();
    }

    private BorderPane makeView()
    {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(makeScreen());
        borderPane.setBottom(makeBottomGrid());
        borderPane.setCenter(makeCenterGrid());
        return borderPane;
    }

    private GridPane makeBottomGrid() {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < bottomRows; i++) {
            for (int j = 0; j < bottomCols; j++) {
                CalcularButton butt = new CalcularButton(regBottomNames[i][j], secBottomNames[i][j], alphaBottomNames[i][j], i, j);
                butt.setMinSize(60, 60);
                butt.setMaxSize(60, 60);
                butt.setText(regBottomNames[i][j]);
                gridPane.add(butt, j, i);
                this.botButtons.add(butt);
                this.allButtons.add(butt);
                //ClickHandler handler = new ClickHandler(console, calculator, allButtons); //uses text console
                ClickHandler handler = new ClickHandler(null, writer, calculator, allButtons); //uses pixel console
                butt.setOnAction(handler);
            }
        }
        gridPane.setHgap(30);
        gridPane.setVgap(2);
        return gridPane;
    }

    private GridPane makeCenterGrid()
    {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < topRows; i++) {
            for (int j = 0; j < topCols; j++) {
                if (!this.regTopNames[i][j].equals("")) {
                    CalcularButton butt = new CalcularButton(regTopNames[i][j], secTopNames[i][j], alphaTopNames[i][j], i, j);
                    butt.setMinSize(60, 60);
                    butt.setMaxSize(60, 60);
                    butt.setText(regTopNames[i][j]);
                    gridPane.add(butt, j, i);
                    this.topButtons.add(butt);
                    this.allButtons.add(butt);
                    //ClickHandler handler = new ClickHandler(console, calculator, allButtons); //uses text console
                    ClickHandler handler = new ClickHandler(null, writer, calculator, allButtons); //uses pixel console
                    butt.setOnAction(handler);
                }
            }
        }
        gridPane.setHgap(11);
        gridPane.setVgap(2);
        return gridPane;
    }

    //uses text console
    /* private TextArea makeScreen()
    {
        TextArea area = new TextArea();
        area.setMaxSize(500, 100);
        area.setEditable(false);
        this.console = area;
        return area;
    } */

    //uses pixel console
    private ImageView makeScreen()
    {
        WritableImage img = new WritableImage(w, h);
        PixelWriter pixelWriter = img.getPixelWriter();
        this.writer = new Writer(pixelWriter, w, h);
        this.view = new ImageView(img);
        return this.view;
    }
}
