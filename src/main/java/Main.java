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

    static final int SIZE = 4;
    static final int HEIGHT = 450;
    static final int WIDTH = 450;
    static final int CEll_DIMENSION = 100;
    static final int BORDER_WIDTH = 10;
    static final int MOTION_DISTANCE = CEll_DIMENSION + BORDER_WIDTH;
    static final Random random = new Random(System.currentTimeMillis());
    static final long TRANSITION_DELAY = 50;

    Group root;
    ArrayList<TranslateTransition> motion = new ArrayList<>();
    boolean controlOn = true;

    Cell[][] board = new Cell[SIZE][SIZE];

    int[] border = new int[]{10, 110, 210, 310};

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("2048");
        primaryStage.setResizable(false);
        root = new Group();
        setBoard();

        final Pane invisible = new Pane();
        invisible.setFocusTraversable(true);
        invisible.requestFocus();
        invisible.setOnKeyPressed(this);
        root.getChildren().add(invisible);

        generateCell(2);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(KeyEvent event) {
        if (controlOn) {
            if (event.getCode() == KeyCode.LEFT) {
                System.out.println("move left");
                shiftLeft();
                move(Direction.LEFT);
            } else if (event.getCode() == KeyCode.RIGHT) {
                System.out.println("move right");
                shiftRight();
                move(Direction.RIGHT);
            } else if (event.getCode() == KeyCode.UP) {
                System.out.println("move up");
                shiftUp();
                move(Direction.UP);
            } else if (event.getCode() == KeyCode.DOWN) {
                System.out.println("move down");
                shiftDown();
                move(Direction.DOWN);
            }
            controlOn = false;
            playMotion();
            generateCell(1);
            print();
        }
    }

    void print() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == null) {
                    System.out.print("0 ");
                } else {
                    System.out.print(board[i][j].getValue() + " ");
                }
            }
            System.out.println();
        }
    }

    void move(Direction direction, Cell cell, int distance) {
        motion.add(translate(direction, cell, distance));
        motion.add(translate(direction, cell.getText(), distance));
    }

    void playMotion() {
        for (TranslateTransition translateTransition : motion) {
            translateTransition.play();
        }
        motion.clear();
    }

    void move(Direction direction) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != null) {
                    move(direction, board[i][j], board[i][j].distance(direction));
                }
            }
        }
    }

    TranslateTransition translate(Direction direction, Node node, int distance) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(TRANSITION_DELAY));
        translateTransition.setNode(node);
        switch (direction) {
        case UP:
        case DOWN:
            translateTransition.setByY(distance);
            break;
        case LEFT:
        case RIGHT:
            translateTransition.setByX(distance);
            break;
        default:
        }
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.onFinishedProperty().set(actionEvent -> controlOn = true);
        return translateTransition;
    }

    void setBoard() {
        Rectangle square = new Rectangle(450, 450);
        square.setFill(Color.ROSYBROWN);
        square.addEventHandler(KeyEvent.KEY_TYPED, this);
        root.getChildren().add(square);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Rectangle cell = new Rectangle(10 + i * 110, 10 + j * 110, CEll_DIMENSION, CEll_DIMENSION);
                cell.setFill(Color.WHEAT);
                root.getChildren().add(cell);
            }
        }
    }

    void generateCell(int num) {
        if (numCell() == SIZE * SIZE) {
            System.out.println("game over");
            return;
        }
        int count = 0;
        while (count != num) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (board[row][col] == null) {
                board[row][col] = new Cell(row, col, BORDER_WIDTH + col * MOTION_DISTANCE, BORDER_WIDTH + row * MOTION_DISTANCE);
                root.getChildren().addAll(board[row][col], board[row][col].getText());
                count++;
            }
        }
    }

    int numCell() {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != null) {
                    count++;
                }
            }
        }
        return count;
    }

    void shiftLeft() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == null) {
                    for (int k = j + 1; k < SIZE; k++) {
                        if (board[i][k] != null) {
                            board[i][j] = board[i][k];
                            board[i][k] = null;
                            board[i][j].setPos(i, j);
                            break;
                        }
                    }
                }
            }
        }
    }

    void shiftRight() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE - 1; j >= 0; j--) {
                if (board[i][j] == null) {
                    for (int k = j - 1; k >= 0; k--) {
                        if (board[i][k] != null) {
                            board[i][j] = board[i][k];
                            board[i][k] = null;
                            board[i][j].setPos(i, j);
                            break;
                        }
                    }
                }
            }
        }
    }

    void shiftUp() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[j][i] == null) {
                    for (int k = j + 1; k < SIZE; k++) {
                        if (board[k][i] != null) {
                            board[j][i] = board[k][i];
                            board[k][i] = null;
                            board[j][i].setPos(j, i);
                            break;
                        }
                    }
                }
            }
        }
    }

    void shiftDown() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE - 1; j >= 0; j--) {
                if (board[j][i] == null) {
                    for (int k = j - 1; k >= 0; k--) {
                        if (board[k][i] != null) {
                            board[j][i] = board[k][i];
                            board[k][i] = null;
                            board[j][i].setPos(j, i);
                            break;
                        }
                    }
                }
            }
        }
    }

    enum Direction {

        UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

        int x, y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }
    }

    class Cell extends Rectangle {

        int originalRow;
        int originalCol;
        int row;
        int col;
        Text text;
        int value;

        public Cell(int row, int col, int startingX, int startingY) {
            super(startingX, startingY, CEll_DIMENSION, CEll_DIMENSION);
            originalRow = row;
            originalCol = col;
            this.row = row;
            this.col = col;
            setFill(Color.GRAY);
            value = 2;
            text = new Text();
            text.setX(startingX + 30);
            text.setY(startingY + 70);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 60));
            text.setFill(Color.BLACK);
            text.setText("" + value);
        }

        public Text getText() {
            return text;
        }

        void addValue(Cell cell) {
            value += cell.value;
        }

        void addValue(int value) {
            this.value += value;
        }

        int getValue() {
            return value;
        }

        boolean sameValue(Cell cell) {
            return value == cell.value;
        }

        void setPos(int row, int col) {
            this.row = row;
            this.col = col;
        }

        int distance(Direction direction) {
            int distance = 0;
            switch (direction) {
            case LEFT:
            case RIGHT:
                System.out.println(originalCol + " --" + col);
                distance = (col-originalCol) * MOTION_DISTANCE;
                break;
            case UP:
            case DOWN:
                System.out.println(originalRow + " --" + row);
                distance = (row-originalRow ) * MOTION_DISTANCE;
                break;
            }
            originalRow = row;
            originalCol = col;
            return distance;
        }


    }

}
