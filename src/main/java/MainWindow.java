import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainWindow extends AnchorPane {

    @FXML
    private GridPane boardPane;

    @FXML
    private Pane highScorePane;

    @FXML
    private Pane scorePane;

    @FXML
    private Pane iconPane;

    private Board board;

    @FXML
    public void initialize(){
        board=new Board(boardPane);
        setupIcon();
        setupScore();
        setupHighScore();
        setupControl();
    }


    private void setupIcon(){
        Label icon=(Label) iconPane.getChildren().get(0);
        icon.setText("2048");
    }

    private void setupScore(){
        Label score=(Label) scorePane.getChildren().get(0);
        score.setText("Score");
    }

    private void setupHighScore(){
        Label highScore=(Label) highScorePane.getChildren().get(0);
        highScore.setText("Best");
    }

    private void setupControl(){
        setOnKeyPressed(new KeyHandler(board));
    }

}
