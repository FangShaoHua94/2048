import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Tile extends Pane {

    private Label label;

    public Tile(){
        setStyle("-fx-background-color: grey ");
        label=new Label();
    }



}
