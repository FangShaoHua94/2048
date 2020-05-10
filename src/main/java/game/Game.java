package game;

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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Game extends Application implements EventHandler<KeyEvent> {

    public static final int SIZE = 4;
    public static final int HEIGHT = 450;
    public static final int WIDTH = 450;
    public static final int CEll_DIMENSION = 100;
    public static final int BORDER_WIDTH = 10;
    public static final int MOTION_DISTANCE = CEll_DIMENSION + BORDER_WIDTH;
    public static final Random random = new Random(System.currentTimeMillis());
    public static final long TRANSITION_DELAY = 50;

    Group root;
    ArrayList<TranslateTransition> motion = new ArrayList<>();
    ArrayList<Cell> toBeRemoved = new ArrayList<>();
    boolean controlOn = true;

    Cell[][] board = new Cell[SIZE][SIZE];

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("2048");
        primaryStage.setResizable(false);
        root = new Group();
        setBoard();
        setControl();
        generateCell(2);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setControl(){
        final Pane invisible = new Pane();
        invisible.setFocusTraversable(true);
        invisible.requestFocus();
        invisible.setOnKeyPressed(this);
        root.getChildren().add(invisible);
    }

    @Override
    public void handle(KeyEvent event) {
        if (controlOn) {
            if (event.getCode() == KeyCode.LEFT) {
                System.out.println("move left");
                shiftLeft();
                mergeLeft();
                shiftLeft();
                move(Direction.LEFT);
            } else if (event.getCode() == KeyCode.RIGHT) {
                System.out.println("move right");
                shiftRight();
                mergeRight();
                shiftRight();
                move(Direction.RIGHT);
            } else if (event.getCode() == KeyCode.UP) {
                System.out.println("move up");
                shiftUp();
                mergeUp();
                shiftUp();
                move(Direction.UP);
            } else if (event.getCode() == KeyCode.DOWN) {
                System.out.println("move down");
                shiftDown();
                mergeDown();
                shiftDown();
                move(Direction.DOWN);
            }
            controlOn = false;
            clearCell();
            playMotion();
            generateCell(1);
            print();
        }
    }

    private void clearCell() {
        for (Cell cell : toBeRemoved) {
            root.getChildren().remove(cell.getText());
            root.getChildren().remove(cell);
        }
        toBeRemoved.clear();
    }

    private void print() {
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

    private void move(Direction direction, Cell cell, int distance) {
        motion.add(translate(direction, cell, distance));
        motion.add(translate(direction, cell.getText(), distance));
    }

    private void playMotion() {
        for (TranslateTransition translateTransition : motion) {
            translateTransition.play();
        }
        motion.clear();
    }

    private void move(Direction direction) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != null) {
                    move(direction, board[i][j], board[i][j].distance(direction));
                }
            }
        }
    }

    private TranslateTransition translate(Direction direction, Node node, int distance) {
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

    private void setBoard() {
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

    private void generateCell(int num) {
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

    private int numCell() {
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

    private void mergeLeft() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE - 1; j++) {
                if (board[i][j] != null && board[i][j + 1] != null && board[i][j].sameValue(board[i][j + 1])) {
                    toBeRemoved.add(board[i][j]);
                    board[i][j] = board[i][j + 1];
                    board[i][j].setPos(i, j);
                    board[i][j].merge();
                    board[i][j + 1] = null;
                }
            }
        }
    }

    private void mergeRight() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE - 1; j > 0; j--) {
                if (board[i][j] != null && board[i][j - 1] != null && board[i][j].sameValue(board[i][j - 1])) {
                    toBeRemoved.add(board[i][j]);
                    board[i][j] = board[i][j - 1];
                    board[i][j].setPos(i, j);
                    board[i][j].merge();
                    board[i][j - 1] = null;
                }
            }
        }
    }

    private void mergeUp() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE - 1; j++) {
                if (board[j][i] != null && board[j + 1][i] != null && board[j][i].sameValue(board[j + 1][i])) {
                    toBeRemoved.add(board[j][i]);
                    board[j][i] = board[j + 1][i];
                    board[j][i].setPos(j, i);
                    board[j][i].merge();
                    board[j + 1][i] = null;
                }
            }
        }
    }

    private void mergeDown() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE - 1; j > 0; j--) {
                if (board[j][i] != null && board[j - 1][i] != null && board[j][i].sameValue(board[j - 1][i])) {
                    toBeRemoved.add(board[j][i]);
                    board[j][i] = board[j - 1][i];
                    board[j][i].setPos(j, i);
                    board[j][i].merge();
                    board[j - 1][i] = null;
                }
            }
        }
    }

    private void shiftLeft() {
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

    private void shiftRight() {
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

    private void shiftUp() {
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

    private void shiftDown() {
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


}
