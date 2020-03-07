package cmsc250.mazerunnerclient;

import static cmsc250.mazerunnerclient.Constants.HEIGHT;
import static cmsc250.mazerunnerclient.Constants.WIDTH;
import static cmsc250.mazerunnerclient.Constants.MARGIN;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 *
 * @author Joe Gregg
 */
public class GamePane extends Pane  {

    private GameGateway gateway;
    private Rectangle goal;
    private Rectangle start1;
    private Rectangle start2;

    public GamePane(GameGateway gateway) {
        // Player 1 start area
        start1 = new Rectangle();
        start1.setFill(Color.web("#339933")); //GREEN
        start1.setStroke(Color.BLACK);
        start1.setX(0);
        start1.setY(0);
        start1.setWidth(MARGIN*3);
        start1.setHeight(MARGIN*3);
        this.getChildren().add(start1);

        // Player 2 start area
        start1 = new Rectangle();
        start1.setFill(Color.web("#339933")); //GREEN
        start1.setStroke(Color.BLACK);
        start1.setX(580.0);
        start1.setY(0);
        start1.setWidth(MARGIN*3);
        start1.setHeight(MARGIN*3);
        this.getChildren().add(start1);
        
        // Goal area rectangle
        goal = new Rectangle();
        goal.setFill(Color.web("#339933")); //GREEN
        goal.setStroke(Color.BLACK);
        goal.setX(0);
        goal.setY(HEIGHT - 50.0);
        goal.setWidth(WIDTH);
        goal.setHeight(HEIGHT);
        this.getChildren().add(goal);
        
        this.gateway = gateway;
        this.getChildren().addAll(gateway.getShapes());
        this.setOnKeyPressed(e -> handleKey(e));
        this.setMinWidth(USE_PREF_SIZE);
        this.setMaxWidth(USE_PREF_SIZE);
        this.setPrefWidth(Constants.WIDTH);
        this.setMinHeight(USE_PREF_SIZE);
        this.setMaxHeight(USE_PREF_SIZE);
        this.setPrefHeight(Constants.HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Color.web("#424242"), CornerRadii.EMPTY, Insets.EMPTY)));
        
        new Thread(new UpdateGameState(gateway)).start();
    }

    private void handleKey(KeyEvent evt) {
        KeyCode code = evt.getCode();
        if (code == KeyCode.UP) {
            gateway.movePaddle(1, 0);
        } else if (code == KeyCode.DOWN) {
            gateway.movePaddle(-1, 0);
        } else if (code == KeyCode.LEFT) {
            gateway.movePaddle(0, 1);
        } else if (code == KeyCode.RIGHT) {
            gateway.movePaddle(0, -1);
        }

    }

    @Override
    public boolean isResizable() {
        return false;
    }

}

class UpdateGameState implements Runnable {

    private GameGateway gateway;

    public UpdateGameState(GameGateway gateway) {
        this.gateway = gateway;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1);
                if (gateway.open()) {
                    gateway.refresh();
                } else {
                    break;
                }
            } catch (Exception ex) {

            }
        }
    }
}
