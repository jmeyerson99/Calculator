import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ui.AlphaButtonMatrix;
import ui.CalcularButton;
import ui.RegButtonMatrix;
import ui.SecondButtonMatrix;

import java.util.ArrayList;

/**
 * Main calculator simulation class
 */

public class CalcularMain extends Application
{
    private String paneMsg = "Calcular v0.1";

    private String[][] regTopNames;
    private String[][] regBottomNames;

    private String[][] secTopNames;
    private String[][] secBottomNames;

    private String[][] alphaTopNames;
    private String[][] alphaBottomNames;

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

    private TextArea console;

    private ArrayList<CalcularButton> topButtons;
    private ArrayList<CalcularButton> botButtons;

    private String currMode;

    @Override
    public void start(Stage stage) {
        this.currMode = "Regular";

        this.topButtons = new ArrayList<>();
        this.botButtons = new ArrayList<>();

        RegButtonMatrix bx = new RegButtonMatrix();
        this.regTopNames = bx.getTop();
        this.regBottomNames = bx.getBottom();

        SecondButtonMatrix b = new SecondButtonMatrix();
        this.secTopNames = b.getTop();
        this.secBottomNames = b.getBottom();

        AlphaButtonMatrix x = new AlphaButtonMatrix();
        this.alphaTopNames = x.getTop();
        this.alphaBottomNames = x.getBottom();

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
        for (int i = 0; i < bottomRows; i++)
        {
            for (int j = 0; j < bottomCols; j++)
            {
                CalcularButton butt = new CalcularButton(regBottomNames[i][j], secBottomNames[i][j], alphaBottomNames[i][j], i, j);
                butt.setMinSize(60, 60);
                butt.setMaxSize(60, 60);
                butt.setText(regBottomNames[i][j]);
                gridPane.add(butt, j, i);
                this.botButtons.add(butt);
                butt.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        console.setText("button " + butt.getText() + " was clicked");
                    }
                });
            }
        }
        gridPane.setHgap(30);
        gridPane.setVgap(2);
        return gridPane;
    }

    private GridPane makeCenterGrid()
    {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < topRows; i++)
        {
            for (int j = 0; j < topCols; j++)
            {
                if (!this.regTopNames[i][j].equals("")) {
                    CalcularButton butt = new CalcularButton(regTopNames[i][j], secTopNames[i][j], alphaTopNames[i][j], i, j);
                    butt.setMinSize(60, 60);
                    butt.setMaxSize(60, 60);
                    butt.setText(regTopNames[i][j]);
                    gridPane.add(butt, j, i);
                    this.topButtons.add(butt);
                    butt.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent e) {
                            console.setText("button " + butt.getText() + " was clicked");
                            if (butt.getText().equals("2nd"))
                            {
                                if (!currMode.equals("2nd")) {
                                    for (CalcularButton b : topButtons) {
                                        b.setText(secTopNames[b.getRow()][b.getCol()]);
                                    }
                                    for (CalcularButton b : botButtons) {
                                        b.setText(secBottomNames[b.getRow()][b.getCol()]);
                                    }
                                    currMode = "2nd";
                                }
                                else {
                                    for (CalcularButton b : topButtons)
                                    {
                                        b.setText(regTopNames[b.getRow()][b.getCol()]);
                                    }
                                    for (CalcularButton b : botButtons)
                                    {
                                        b.setText(regBottomNames[b.getRow()][b.getCol()]);
                                    }
                                    currMode = "Regular";
                                }
                            }
                            else if (butt.getText().equals("Alpha"))
                            {
                                if (!currMode.equals("Alpha")) {
                                    for (CalcularButton b : topButtons) {
                                        b.setText(alphaTopNames[b.getRow()][b.getCol()]);
                                    }
                                    for (CalcularButton b : botButtons) {
                                        b.setText(alphaBottomNames[b.getRow()][b.getCol()]);
                                    }
                                    currMode = "Alpha";
                                }
                                else {
                                    for (CalcularButton b : topButtons)
                                    {
                                        b.setText(regTopNames[b.getRow()][b.getCol()]);
                                    }
                                    for (CalcularButton b : botButtons)
                                    {
                                        b.setText(regBottomNames[b.getRow()][b.getCol()]);
                                    }
                                    currMode = "Regular";
                                }
                            }
                            else if (butt.getText().equals("Regular"))
                            {
                                for (CalcularButton b : topButtons)
                                {
                                    b.setText(regTopNames[b.getRow()][b.getCol()]);
                                }
                                for (CalcularButton b : botButtons)
                                {
                                    b.setText(regBottomNames[b.getRow()][b.getCol()]);
                                }
                                currMode = "Regular";
                            }
                            else
                            {
                                currMode = currMode;
                            }
                        }
                    });
                }
            }
        }
        gridPane.setHgap(11);
        gridPane.setVgap(2);
        return gridPane;
    }

    private TextArea makeScreen()
    {
        TextArea area = new TextArea();
        area.setMaxSize(500, 100);
        this.console = area;
        return area;
    }

}
