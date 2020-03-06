package cmsc250.mazerunnerclient;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Joe Gregg
 */
public class GamePane extends Pane {
    private GameGateway gateway;
    
    public GamePane(GameGateway gateway) {
        this.gateway = gateway;
        this.getChildren().addAll(gateway.getShapes());
        this.setOnKeyPressed(e->handleKey(e));
        this.setMinWidth(USE_PREF_SIZE);
        this.setMaxWidth(USE_PREF_SIZE);
        this.setPrefWidth(Constants.WIDTH);
        this.setMinHeight(USE_PREF_SIZE);
        this.setMaxHeight(USE_PREF_SIZE);
        this.setPrefHeight(Constants.HEIGHT);
        new Thread(new UpdateGameState(gateway)).start();
    }
    
    private void handleKey(KeyEvent evt) {
        KeyCode code = evt.getCode();
        if(code == KeyCode.UP)
            gateway.movePaddle(1,0);
        else if(code == KeyCode.DOWN)
            gateway.movePaddle(-1,0);
        else if(code == KeyCode.LEFT)
            gateway.movePaddle(0,1);
        else if(code == KeyCode.RIGHT)
            gateway.movePaddle(0,-1);


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
        while(true) {
            try {
                Thread.sleep(1);
                if(gateway.open())
                    gateway.refresh();
                else
                    break;
            } catch(Exception ex) {
                
            }
        }
    }
}