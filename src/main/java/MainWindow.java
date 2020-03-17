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
        setupIcon();
        board=new Board(boardPane);
    }


    private void setupIcon(){
        Label icon=(Label) iconPane.getChildren().get(0);
        icon.setText("2048");
    }

}
