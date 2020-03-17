import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {

    public Board board;

    public KeyHandler(Board board){
        this.board=board;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case LEFT:
            board.squash(Direction.LEFT);
            break;
        case UP:
            board.squash(Direction.UP);
            break;
        case RIGHT:
            board.squash(Direction.RIGHT);
            break;
        case DOWN:
            board.squash(Direction.DOWN);
            break;
        default:
            break;
        }
    }
}
