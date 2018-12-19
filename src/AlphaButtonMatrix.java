public class AlphaButtonMatrix implements ButtonMatrix {

    private String[][] top;
    private String[][] bottom;

    public AlphaButtonMatrix ()
    {
        topConfig();
        bottomConfig();

    }

    @Override
    public void topConfig() {

        this.top = new String[3][7];
        this.top[0][0] = "";
        this.top[0][1] = "";
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
    public void bottomConfig() {

        this.bottom = new String[8][5];
        this.bottom[0][0] = "";
        this.bottom[0][1] = "";
        this.bottom[0][2] = "";
        this.bottom[0][3] = "";
        this.bottom[0][4] = "";

        this.bottom[1][0] = "A";
        this.bottom[1][1] = "B";
        this.bottom[1][2] = "C";
        this.bottom[1][3] = "D";
        this.bottom[1][4] = "E";

        this.bottom[2][0] = "F";
        this.bottom[2][1] = "G";
        this.bottom[2][2] = "H";
        this.bottom[2][3] = "I";
        this.bottom[2][4] = "J";

        this.bottom[3][0] = "K";
        this.bottom[3][1] = "L";
        this.bottom[3][2] = "M";
        this.bottom[3][3] = "N";
        this.bottom[3][4] = "O";

        this.bottom[4][0] = "P";
        this.bottom[4][1] = "Q";
        this.bottom[4][2] = "R";
        this.bottom[4][3] = "S";
        this.bottom[4][4] = "T";

        this.bottom[5][0] = "U";
        this.bottom[5][1] = "V";
        this.bottom[5][2] = "W";
        this.bottom[5][3] = "X";
        this.bottom[5][4] = "Y";

        this.bottom[6][0] = "Z";
        this.bottom[6][1] = "'";
        this.bottom[6][2] = "\"";
        this.bottom[6][3] = ",";
        this.bottom[6][4] = "";

        this.bottom[7][0] = "";
        this.bottom[7][1] = "";
        this.bottom[7][2] = ".";
        this.bottom[7][3] = "";
        this.bottom[7][4] = "Enter";
    }

    @Override
    public String[][] getTop() {
        return this.top;
    }

    @Override
    public String[][] getBottom() {
        return this.bottom;
    }
}

