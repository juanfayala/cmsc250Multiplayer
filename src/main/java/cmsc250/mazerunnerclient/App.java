package cmsc250.mazerunnerclient;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        GameGateway gateway = new GameGateway();
        GamePane pane = new GamePane(gateway);
        var scene = new Scene(pane, 640, 480);
        pane.requestFocus();
        stage.setScene(scene);
        stage.setOnCloseRequest(e->gateway.close());
        stage.setTitle("Maze Runner");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}