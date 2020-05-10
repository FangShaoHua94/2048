package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Random;

import static game.Game.CEll_DIMENSION;
import static game.Game.MOTION_DISTANCE;


public class Cell extends Rectangle {

    private int originalRow;
    private int originalCol;
    private int row;
    private int col;
    private Text text;
    private int value;

    private static int[] bound=new int[]{10,120,230,340};

    public Cell(int row, int col, int startingX, int startingY) {
        super(startingX, startingY, CEll_DIMENSION, CEll_DIMENSION);
        originalRow = row;
        originalCol = col;
        this.row = row;
        this.col = col;
        if(new Random(System.currentTimeMillis()+row+col*row).nextInt(10)==0){
            value =4;
        }else{
            value =2;
        }
        text = new Text();

        text.setX(startingX+25);
        text.setY(startingY+70);
        update();
        text.setText("" + value);
    }

    public Text getText() {
        return text;
    }

    public void merge() {
        value *= 2;
        text.setText("" + value);
    }

    public int getValue() {
        return value;
    }

    public boolean sameValue(Cell cell) {
        return value == cell.value;
    }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int distance(Game.Direction direction) {
        int distance = 0;
        switch (direction) {
        case LEFT:
        case RIGHT:
            distance = (col - originalCol) * MOTION_DISTANCE;
            break;
        case UP:
        case DOWN:
            distance = (row - originalRow) * MOTION_DISTANCE;
            break;
        }
        originalRow = row;
        originalCol = col;
        update();
        return distance;
    }

    private void update(){
        switch (value){
        case 2:
            setFill(Color.RED);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
            break;
        case 4:
            setFill(Color.ORANGERED);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
            break;
        case 8:
            setFill(Color.DARKORANGE);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
            break;
        case 16:
            setFill(Color.ORANGE);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
            break;
        case 32:
            setFill(Color.GOLD);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
            break;
        case 64:
            setFill(Color.YELLOW);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
            break;
        case 128:
            setFill(Color.YELLOWGREEN);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
            break;
        case 256:
            setFill(Color.LIGHTGREEN);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
            break;
        case 512:
            setFill(Color.LIME);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
            break;
        case 1024:
            setFill(Color.LIMEGREEN);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            break;
        case 2048:
            setFill(Color.MEDIUMSEAGREEN);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            break;
        case 4096:
            setFill(Color.MEDIUMSPRINGGREEN);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            break;
        case 8192:
            setFill(Color.MEDIUMTURQUOISE);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            break;
        case 16384:
            setFill(Color.SLATEBLUE);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            break;
        case 32768:
            setFill(Color.ROYALBLUE);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            break;
        case 65536:
            setFill(Color.NAVY);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            break;
        case 131072:
            setFill(Color.MIDNIGHTBLUE);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            break;
        case 262144:
            setFill(Color.DARKBLUE);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            break;
        }
    }
}

