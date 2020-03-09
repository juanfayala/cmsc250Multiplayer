package cmsc250.mazerunnerclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 *
 * @author Joe Gregg
 */
public class GameGateway implements Constants {

    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    private List<Shape> shapes;
    private GamePane pane;
    Rectangle leftPaddle;
    Rectangle rightPaddle;
    Rectangle leftStart;
    Rectangle rightStart;
    Rectangle win1;
    Rectangle win2;
    Label lives1;
    Label lives2;
    Circle ball;
    boolean isOpen = true;

    public GameGateway() {
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an output stream to send data to the server
            outputToServer = new PrintWriter(socket.getOutputStream());

            // Create an input stream to read data from the server
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            System.out.println("Exception in GameGateway.");
            ex.printStackTrace();
        }

        // Make the shapes
        shapes = new ArrayList<Shape>();

        // Player 1
        leftPaddle = new Rectangle(MARGIN, MARGIN, THICKNESS, LENGTH);
        leftPaddle.setFill(Color.web("#cc3300")); //RED
        leftPaddle.setStroke(Color.BLACK);
        shapes.add(leftPaddle);

        // Player 2
        rightPaddle = new Rectangle(WIDTH - MARGIN - THICKNESS, MARGIN, THICKNESS, LENGTH);
        rightPaddle.setFill(Color.web("#3366cc")); //BLUE
        rightPaddle.setStroke(Color.BLACK);
        shapes.add(rightPaddle);

        // Ball
        ball = new Circle(WIDTH / 2, HEIGHT / 4, MARGIN / 4);
        ball.setFill(Color.BLACK);
        shapes.add(ball);

    }

    public List<Shape> getShapes() {
        return shapes;
    }

    /* Move the player's paddle
    *  1 -> increases x or y
    *  -1 -> decreases x or y
     */
    public synchronized void movePaddle(int y, int x) {
        if (y == 1) {
            outputToServer.println(MOVE_UP);
        } else if (y == -1) {
            outputToServer.println(MOVE_DOWN);
        } else if (x == 1) {
            outputToServer.println(MOVE_LEFT);
        } else if (x == -1) {
            outputToServer.println(MOVE_RIGHT);
        }
        outputToServer.flush();
    }

    // Refresh the game state
    public synchronized void refresh() {
        outputToServer.println(GET_GAME_STATE);
        outputToServer.flush();
        String state = "";
        try {
            state = inputFromServer.readLine();
        } catch (IOException ex) {
            System.out.println("Exception in GameGateway.");
            ex.printStackTrace();
        }
        // TODO: Add paddle setX
        String parts[] = state.split(" ");
        ball.setCenterX(Double.parseDouble(parts[0]));
        ball.setCenterY(Double.parseDouble(parts[1]));

        // Check if player 1(RED) has reached goal
        if (Double.parseDouble(parts[2]) > HEIGHT - 25) {
            // Give player win
            win(1);
        } else {
            leftPaddle.setY(Double.parseDouble(parts[2]));
        }

        // Check if player 2(BLUE) has reached goal
        if (Double.parseDouble(parts[3]) > HEIGHT - 25) {
            // Give player win
            win(2);
        } else {
            rightPaddle.setY(Double.parseDouble(parts[3]));
        }

        // Added these lines for client to get the x coords from server
        leftPaddle.setX(Double.parseDouble(parts[4]));
        rightPaddle.setX(Double.parseDouble(parts[5]));

    }

    // Winner screen
    public void win(int player) {
        if (player == 1) {
            win1 = new Rectangle();
            win1.setFill(Color.web("#cc3300")); //RED
            win1.setX(0);
            win1.setY(0);
            win1.setWidth(WIDTH);
            win1.setHeight(HEIGHT);
            pane.getChildren().add(win1);
        } else {
            win2 = new Rectangle();
            win2.setFill(Color.web("#3366cc")); //BLUE
            win2.setX(0);
            win2.setY(0);
            win2.setWidth(WIDTH);
            win2.setHeight(HEIGHT);
            pane.getChildren().add(win2);

        }
    }

    public void close() {
        try {
            outputToServer.close();
            inputFromServer.close();
        } catch (Exception ex) {

        }
        isOpen = false;
    }

    public boolean open() {
        return isOpen;
    }
}
