import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Tile extends Pane {

    private Label label;
    private int value;
    private boolean isFilled;

    public Tile(){
        label=new Label();
        setupLabel();
        setId("tilePane");
        this.getStylesheets().add("view/tile.css");
        isFilled=false;
        value=0;
    }

    private void setupLabel(){
        label.setId("tileLabel");
        getChildren().add(label);
    }

    public boolean isFilled(){
        return isFilled;
    }

    public void multiply(){
        setValue(value*2);
    }

    public void clearValue(){
        setValue(0);
        isFilled=false;
    }

    private void setValue(int value){
        this.value=value;
        if(value==0){
            label.setText("");
        }else {
            label.setText(String.valueOf(value));
        }
    }

    public int getValue(){
        return value;
    }

    public void initialize(int value){
        setValue(value);
        isFilled=true;
    }



}
