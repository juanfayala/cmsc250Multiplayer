package cmsc250.mazerunnerclient;

import static cmsc250.mazerunnerclient.Constants.HEIGHT;
import static cmsc250.mazerunnerclient.Constants.WIDTH;
import static cmsc250.mazerunnerclient.Constants.MARGIN;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author Joe Gregg
 */
public class GamePane extends Pane {

    private GameGateway gateway;
    private Pane pane;
    private Rectangle goal;
    private Rectangle start1;
    private Rectangle start2;
    private Rectangle win1;
    private Rectangle win2;
    private Label lives1;
    private Label lives2;

    public GamePane(GameGateway gateway) {
        // Player 1 start area
        start1 = new Rectangle();
        start1.setFill(Color.web("#339933")); //GREEN
        start1.setStroke(Color.BLACK);
        start1.setX(0);
        start1.setY(0);
        start1.setWidth(MARGIN * 3);
        start1.setHeight(MARGIN * 3);

        // Player 2 start area
        start2 = new Rectangle();
        start2.setFill(Color.web("#339933")); //GREEN
        start2.setStroke(Color.BLACK);
        start2.setX(580.0);
        start2.setY(0);
        start2.setWidth(MARGIN * 3);
        start2.setHeight(MARGIN * 3);

        // Goal area rectangle
        goal = new Rectangle();
        goal.setFill(Color.web("#339933")); //GREEN
        goal.setStroke(Color.BLACK);
        goal.setX(0);
        goal.setY(HEIGHT - 50.0);
        goal.setWidth(WIDTH);
        goal.setHeight(HEIGHT);

        // Player 1 life label
        lives1 = new Label("Fails: ");
        lives1.setFont(new Font("Helvetica Bold", 16));
        lives1.setTextFill(Color.WHITE);
        lives1.setMinWidth(20);
        lives1.setMinHeight(10);
        lives1.setLayoutX(0);
        lives1.setLayoutY(0);

        // Player 2 life label
        lives2 = new Label("Fails: ");
        lives2.setFont(new Font("Helvetica Bold", 16));
        lives2.setTextFill(Color.WHITE);
        lives2.setMinWidth(20);
        lives2.setMinHeight(10);
        lives2.setLayoutX(580.0);
        lives2.setLayoutY(0);

        // Add all to pane
        this.getChildren().addAll(start1, start2, goal, lives1, lives2);

        this.gateway = gateway;
        this.getChildren().addAll(gateway.getShapes());
        this.getChildren().addAll(gateway.getLabels());
        this.setOnKeyPressed(e -> handleKey(e));
        this.setMinWidth(USE_PREF_SIZE);
        this.setMaxWidth(USE_PREF_SIZE);
        this.setPrefWidth(Constants.WIDTH);
        this.setMinHeight(USE_PREF_SIZE);
        this.setMaxHeight(USE_PREF_SIZE);
        this.setPrefHeight(Constants.HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Color.web("#424242"), CornerRadii.EMPTY, Insets.EMPTY))); //DARK GREY

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
