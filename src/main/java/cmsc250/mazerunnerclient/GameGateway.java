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
    private int playerWin = 0;
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
        
        Circle circle = new Circle(WIDTH/2,HEIGHT/4,MARGIN/2);
        circle.setFill(Color.WHITE);
        shapes.add(circle);
        
        Circle circle1 = new Circle(WIDTH/4,HEIGHT/4,MARGIN/2);
        circle1.setFill(Color.WHITE);
        shapes.add(circle1);
        
        Circle circle2 = new Circle(WIDTH/2 + WIDTH/4,HEIGHT/4,MARGIN/2);
        circle2.setFill(Color.WHITE);
        shapes.add(circle2);
        
        Circle circle0 = new Circle(WIDTH/2,HEIGHT/1.25,MARGIN);
        circle0.setFill(Color.RED);
        shapes.add(circle0);

         Circle circle01 = new Circle(WIDTH/1.1,HEIGHT/1.25,MARGIN);
        circle01.setFill(Color.RED);
        shapes.add(circle01);

          Circle circle02 = new Circle(WIDTH/7,HEIGHT/1.25,MARGIN);
        circle02.setFill(Color.RED);
        shapes.add(circle02);

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
        String parts[] = state.split(" ");

        leftPaddle.setY(Double.parseDouble(parts[0]));
        rightPaddle.setY(Double.parseDouble(parts[1]));

        // Added these lines for client to get the x coords from server
        leftPaddle.setX(Double.parseDouble(parts[2]));
        rightPaddle.setX(Double.parseDouble(parts[3]));

        // Added this line to set the winning player when goal is met
        playerWin = Integer.parseInt(parts[4]);
        // Check which player won the game
        if (playerWin == 1) {
            // player 1 wins
            System.out.println("Red Player Wins!");
            close();
            //TODO: OPEN WIN OR LOSE PANE
        } else if (playerWin == 2) {
            // player 2 wins
            System.out.println("Blue Player Wins!");
            close();
            //TODO: OPEN WIN OR LOSE PANE
            
        } else {
            //Do nothing, nobody has won yet
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
