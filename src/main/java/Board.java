import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private static final int DIMENSION=4;
    private static final int START_VALUE=2;

    private GridPane boardPane;
    private Tile[][] board=new Tile[DIMENSION][DIMENSION];

    public Board(GridPane boardPane){
        this.boardPane=boardPane;
        setupBoard();
        setupGame();
        display();
    }

    private void setupBoard(){
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                Tile tile=new Tile();
                board[i][j]=tile;
                boardPane.add(tile,j,i);
            }
        }
    }

    private void setupGame(){
        for(int i=0;i<2;i++) {
            generateValue();
        }
    }

    private void generateValue(){
        Tile tile;
        int count=0;
        do {
            Random random = new Random(System.currentTimeMillis());
            tile=board[random.nextInt(DIMENSION)][random.nextInt(DIMENSION)];
            count++;
        } while (tile.isFilled());
        System.out.println("count:"+ count);
        tile.initialize(START_VALUE);
        refillGridPane();
    }

    public void squash(Direction direction){
        switch(direction){
        case LEFT:
            sort();
            break;
        case UP:
            rotateAntiClockwise();
            sort();
            rotateClockwise();
            break;
        case RIGHT:
            rotateAntiClockwise();
            rotateAntiClockwise();
            sort();
            rotateClockwise();
            rotateClockwise();
            break;
        case DOWN:
            rotateAntiClockwise();
            rotateAntiClockwise();
            rotateAntiClockwise();
            sort();
            rotateClockwise();
            rotateClockwise();
            rotateClockwise();
            break;
        default:
            break;
        }
        refillGridPane();
        generateValue();
        display();
    }

    private void sort(){
        for(int i=0;i<DIMENSION;i++){
            sortRow(board[i]);
            merge(board[i]);
            sortRow(board[i]);
        }
    }

    private void sortRow(Tile[] tiles){
        for(int i=0;i<3;i++){
            if(!tiles[i].isFilled()){
                for(int j=i+1;j<4;j++){
                    if(tiles[j].isFilled()){
                        Tile temp=tiles[i];
                        tiles[i]=tiles[j];
                        tiles[j]=temp;
                    }
                }
            }
        }
    }

    private boolean merge(Tile[] tiles){
        boolean isMerge=false;
        for(int i=0;i<3;i++){
            if(tiles[i].isFilled() && tiles[i].getValue() == tiles[i+1].getValue()){
                tiles[i].multiply();
                tiles[i+1].clearValue();
                isMerge=true;
            }
        }
        return isMerge;
    }

    private void rotateAntiClockwise(){
        for(int i=0;i<2;i++){
            for(int j=i;j<3-i;j++){
                Tile temp=board[i][j];
                board[i][j]=board[j][3-i];
                board[j][3-i]=board[3-i][3-j];
                board[3-i][3-j]=board[3-j][i];
                board[3-j][i]=temp;
            }
        }
    }

    private void rotateClockwise(){
        for(int i=0;i<2;i++){
            for(int j=i;j<3-i;j++){
                Tile temp=board[i][j];
                board[i][j]=board[3-j][i];
                board[3-j][i]=board[3-i][3-j];
                board[3-i][3-j]=board[j][3-i];
                board[j][3-i]=temp;
            }
        }
    }

    private void refillGridPane(){
        List<Node> nodes=new ArrayList<>();
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                nodes.add(board[i][j]);
            }
        }
        boardPane.getChildren().removeAll(nodes);
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                boardPane.add(board[i][j],j,i);
            }
        }
    }

    private void display(){
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                System.out.print(board[i][j].getValue()+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
