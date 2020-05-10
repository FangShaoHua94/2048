import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application implements EventHandler<KeyEvent> {

    static final int SIZE=4;
    static final int HEIGHT=450;
    static final int WIDTH=450;
    static final int CEll_DIMENSION=100;
    static final int BORDER_WIDTH=10;
    static final int MOTION_DISTANCE=CEll_DIMENSION+BORDER_WIDTH;
    static final Random random = new Random(System.currentTimeMillis());

    Group root;
    ArrayList<TranslateTransition> motion= new ArrayList<>();

    Cell[][] board=new Cell[SIZE][SIZE];

    int[] border= new int[]{10,110,210,310};

    enum Direction{

        UP(0,-1),DOWN(0,1),LEFT(-1,0),RIGHT(1,0);

        int x, y;
        Direction(int x,int y){
            this.x=x;
            this.y=y;
        }

        int getX(){
            return x;
        }

        int getY(){
            return y;
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("2048");
        primaryStage.setResizable(false);
        root = new Group();
        setBoard();

        final Pane invisible = new Pane();
        invisible.setFocusTraversable(true);
        invisible.requestFocus();
        invisible.setOnKeyPressed(this);
        root.getChildren().add(invisible);

        setStartingCell();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(KeyEvent event) {
        if(event.getCode()==KeyCode.LEFT){
            System.out.println("move left");
            move(Direction.LEFT);
        } else if(event.getCode()==KeyCode.RIGHT){
            System.out.println("move right");
            move(Direction.RIGHT);
        } else if(event.getCode()==KeyCode.UP){
            System.out.println("move up");
            move(Direction.UP);
        } else if(event.getCode()==KeyCode.DOWN){
            System.out.println("move down");
            move(Direction.DOWN);
        }
        playMotion();
    }

    void move(Direction direction){
        for(int i=0;i<SIZE;i++){
            for (int j=0;j<SIZE;j++){
                if(board[i][j]!=null){
                    motion.add(translate(direction,board[i][j]));
                    motion.add(translate(direction,board[i][j].getText()));
                }
            }
        }
    }

    void playMotion(){
        for(TranslateTransition translateTransition:motion){
            translateTransition.play();
        }
        motion.clear();
    }

    TranslateTransition translate(Direction direction, Node node){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(50));
        translateTransition.setNode(node);
        switch (direction){
        case UP:
            translateTransition.setByY(-MOTION_DISTANCE);
            break;
        case DOWN:
            translateTransition.setByY(MOTION_DISTANCE);
            break;
        case LEFT:
            translateTransition.setByX(-MOTION_DISTANCE);
            break;
        case RIGHT:
            translateTransition.setByX(MOTION_DISTANCE);
            break;
        default:
        }
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        return translateTransition;
    }

    void setBoard(){
        Rectangle square=new Rectangle(450,450);
        square.setFill(Color.ROSYBROWN);
        square.addEventHandler(KeyEvent.KEY_TYPED,this);
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
