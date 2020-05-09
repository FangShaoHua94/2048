import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application implements EventHandler<KeyEvent> {

    static final int SIZE=4;
    static final int HEIGHT=450;
    static final int WIDTH=450;
    static final int CEll_DIMENSION=100;
    static final int BORDER_WIDTH=10;
    static final Random random = new Random(System.currentTimeMillis());

    Group root;

    Cell[][] board=new Cell[SIZE][SIZE];

    int[] leftTopBound= new int[]{10,110,210,310};
    int[] rightBottomBound=new int[]{110,220,330,440};

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("2048");
        primaryStage.setResizable(false);
        root = new Group();
        setBoard();
        setStartingCell();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(KeyEvent event) {

    }

    void setBoard(){
        Rectangle square=new Rectangle(450,450);
        square.setFill(Color.ROSYBROWN);
        root.getChildren().add(square);
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                Rectangle cell = new Rectangle(10+i*110,10+j*110,CEll_DIMENSION,CEll_DIMENSION);
                cell.setFill(Color.WHEAT);
                root.getChildren().add(cell);
            }
        }
    }

    void setStartingCell(){
        int count=0;
        while(count!=2){
            int row=random.nextInt(SIZE);
            int col=random.nextInt(SIZE);
            if(board[row][col]==null){
                board[row][col]=new Cell(10+col*110,10+row*110);
                root.getChildren().addAll(board[row][col],board[row][col].getText());
                count++;
            }
        }
    }

    class Cell extends Rectangle{
        Text text;
        int value;

        public Cell(int startingX,int startingY){
            super(startingX,startingY,CEll_DIMENSION,CEll_DIMENSION);
            setFill(Color.GRAY);
            value=2;
            text= new Text();
            text.setX(startingX+30);
            text.setY(startingY+70);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 60));
            text.setFill(Color.BLACK);
            text.setText(""+value);
        }

        public Text getText(){
            return text;
        }

        void addValue(Cell cell){
            value+=cell.value;
        }

        void addValue(int value){
            this.value+=value;
        }

        int getValue(){
            return value;
        }
    }

}
