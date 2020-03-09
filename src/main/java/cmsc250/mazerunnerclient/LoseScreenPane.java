/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsc250.mazerunnerclient;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Juan Felipe Ayala
 */
public class LoseScreenPane extends Pane{
      private GameGateway gateway;
     private Label loseCondition; 
     private Label exitMessage;
    public LoseScreenPane(){
         loseCondition = new Label("You Win.");
        loseCondition.setFont(new Font("Helvetica Bold", 16));
        loseCondition.setTextFill(Color.WHITE);
        loseCondition.setMinWidth(50);
        loseCondition.setMinHeight(50);
        loseCondition.setLayoutX(300);
        loseCondition.setLayoutY(0);
        
        exitMessage = new Label ("Please Exit Game");
        exitMessage.setFont(new Font("Helvetica Bold", 16));      
        exitMessage.setTextFill(Color.WHITE);
        exitMessage.setMinWidth(50);
        exitMessage.setMinHeight(50);
        exitMessage.setLayoutX(300);
        exitMessage.setLayoutY(20);
       
        this.gateway = gateway;
         this.getChildren().addAll(loseCondition, exitMessage);

        
        this.getChildren().addAll(gateway.getShapes());
        this.setMinWidth(USE_PREF_SIZE);
        this.setMaxWidth(USE_PREF_SIZE);
        this.setPrefWidth(Constants.WIDTH);
        this.setMinHeight(USE_PREF_SIZE);
        this.setMaxHeight(USE_PREF_SIZE);
        this.setPrefHeight(Constants.HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Color.web("#424242"), CornerRadii.EMPTY, Insets.EMPTY))); //DARK GREY

        new Thread(new UpdateGameState(gateway)).start();
     }
      @Override
    public boolean isResizable() {
        return false;
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
}