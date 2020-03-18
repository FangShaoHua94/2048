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
            System.out.println("left");
            break;
        case UP:
            board.squash(Direction.UP);
            System.out.println("up");
            break;
        case RIGHT:
            board.squash(Direction.RIGHT);
            System.out.println("right");
            break;
        case DOWN:
            board.squash(Direction.DOWN);
            System.out.println("down");
            break;
        default:
            break;
        }
    }
}
