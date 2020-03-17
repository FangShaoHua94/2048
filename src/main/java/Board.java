import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Board {

    private GridPane boardPane;
    private static final int DIMENSION=4;
    private Label[][] board=new Label[DIMENSION][DIMENSION];

    public Board(GridPane boardPane){
        setupBoard(boardPane);
    }

    private void setupBoard(GridPane boardPane){
        this.boardPane=boardPane;
        for(int i=0;i<DIMENSION;i++){
            for(int j=0;j<DIMENSION;j++){
                boardPane.add(new Tile(),i,j);
            }
        }
    }




}
