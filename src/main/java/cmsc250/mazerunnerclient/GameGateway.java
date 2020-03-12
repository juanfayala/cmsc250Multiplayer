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
    Label fails1;
    int failCount1 = 0;
    Label fails2;
    int failCount2 = 0;
    private int playerWin = 0;
    boolean isOpen = true;
    private Circle movingBall1;
    private Circle movingBall2;
    private Circle movingBall3;
    private Circle movingBall4;
    private Circle movingBall5;
    private List<Shape> obstacles;
    private boolean collisionDetected;

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
        obstacles = new ArrayList<Shape>();

        // Player 1
        leftPaddle = new Rectangle(MARGIN, MARGIN, THICKNESS, LENGTH);
        leftPaddle.setFill(Color.web("#cc3300")); //RED
        leftPaddle.setStroke(Color.BLACK);
        shapes.add(leftPaddle);

        fails1 = new Label("0");
        fails1.setFont(new Font("Helvetica Bold", 16));
        fails1.setTextFill(Color.WHITE);
        fails1.setMinWidth(20);
        fails1.setMinHeight(10);
        fails1.setLayoutX(45.0);
        fails1.setLayoutY(0);
        //shapes.add(fails1);
        // NEED TO ADD TO PANE

        // Player 2
        rightPaddle = new Rectangle(WIDTH - MARGIN - THICKNESS, MARGIN, THICKNESS, LENGTH);
        rightPaddle.setFill(Color.web("#3366cc")); //BLUE
        rightPaddle.setStroke(Color.BLACK);
        shapes.add(rightPaddle);

        fails2 = new Label("0");
        fails2.setFont(new Font("Helvetica Bold", 16));
        fails2.setTextFill(Color.WHITE);
        fails2.setMinWidth(20);
        fails2.setMinHeight(10);
        fails2.setLayoutX(625.0);
        fails2.setLayoutY(0);
        
        Circle circle = new Circle(WIDTH / 2, HEIGHT / 4, MARGIN / 2);
        circle.setFill(Color.WHITE);
        shapes.add(circle);
        obstacles.add(circle);

        Circle circle1 = new Circle(WIDTH / 4, HEIGHT / 4, MARGIN / 2);
        circle1.setFill(Color.WHITE);
        shapes.add(circle1);
        obstacles.add(circle);
        Circle circle2 = new Circle(WIDTH / 2 + WIDTH / 4, HEIGHT / 4, MARGIN / 2);
        circle2.setFill(Color.WHITE);
        shapes.add(circle2);
        obstacles.add(circle2);
        movingBall1 = new Circle(WIDTH / 2, HEIGHT / 1.25, MARGIN);
        movingBall1.setFill(Color.RED);
        shapes.add(movingBall1);
        obstacles.add(movingBall1);
        movingBall2 = new Circle(WIDTH / 1.1, HEIGHT / 1.25, MARGIN);
        movingBall2.setFill(Color.RED);
        shapes.add(movingBall2);
        obstacles.add(movingBall2);
        movingBall3 = new Circle(WIDTH / 7, HEIGHT / 1.25, MARGIN);
        movingBall3.setFill(Color.RED);
        shapes.add(movingBall3);
        obstacles.add(movingBall3);
        movingBall4 = new Circle(WIDTH / 7, HEIGHT / 1.25, MARGIN);
        movingBall4.setFill(Color.RED);
        shapes.add(movingBall4);
        obstacles.add(movingBall4);
        movingBall5 = new Circle(WIDTH / 7, HEIGHT / 1.25, MARGIN);
        movingBall5.setFill(Color.RED);
        shapes.add(movingBall5);
        obstacles.add(movingBall5);
        Circle circle3 = new Circle(WIDTH / 3 + WIDTH / 5, HEIGHT / 5, MARGIN / 2);
        circle3.setFill(Color.WHITE);
        shapes.add(circle3);
        obstacles.add(circle3);

        Circle circle4 = new Circle(WIDTH / 5 + WIDTH / 3, HEIGHT / 3, MARGIN / 2);
        circle4.setFill(Color.WHITE);
        shapes.add(circle4);
        obstacles.add(circle4);
        Circle circle5 = new Circle(WIDTH / 6 + WIDTH / 4, HEIGHT / 3, MARGIN / 2);
        circle5.setFill(Color.WHITE);
        shapes.add(circle5);
        obstacles.add(circle5);
        Circle circle6 = new Circle(WIDTH / 2 + WIDTH / 1, HEIGHT / 8, MARGIN / 2);
        circle6.setFill(Color.WHITE);
        shapes.add(circle6);
        obstacles.add(circle6);
        Circle circle7 = new Circle(WIDTH / 3 + WIDTH / 4, HEIGHT / 5, MARGIN / 2);
        circle7.setFill(Color.WHITE);
        shapes.add(circle7);
        obstacles.add(circle7);
        Circle circle8 = new Circle(WIDTH / 3 + WIDTH / 4, HEIGHT / 5, MARGIN / 2);
        circle8.setFill(Color.WHITE);
        shapes.add(circle8);
        obstacles.add(circle8);
        Circle circle9 = new Circle(WIDTH / 5 + WIDTH / 2, HEIGHT / 5, MARGIN / 2);
        circle9.setFill(Color.WHITE);
        shapes.add(circle9);
        obstacles.add(circle9);
        Circle circle10 = new Circle(WIDTH / 22 + WIDTH / 43, HEIGHT / 5, MARGIN / 2);
        circle10.setFill(Color.WHITE);
        shapes.add(circle10);
        obstacles.add(circle10);
        Circle circle11 = new Circle(WIDTH / 35 + WIDTH / 65, HEIGHT / 2, MARGIN / 2);
        circle11.setFill(Color.WHITE);
        shapes.add(circle11);
        obstacles.add(circle11);
        Circle circle12 = new Circle(WIDTH / 25 + WIDTH / 5, HEIGHT / 2, MARGIN / 2);
        circle12.setFill(Color.WHITE);
        shapes.add(circle12);
        obstacles.add(circle12);
        Circle circle13 = new Circle(WIDTH / 35 + WIDTH / 15, HEIGHT / 2, MARGIN / 2);
        circle13.setFill(Color.WHITE);
        shapes.add(circle13);
        obstacles.add(circle13);
        Circle circle14 = new Circle(WIDTH / 15 + WIDTH / 3, HEIGHT / 6, MARGIN / 2);
        circle14.setFill(Color.WHITE);
        shapes.add(circle14);
        obstacles.add(circle14);
        Circle circle15 = new Circle(WIDTH / 5 + WIDTH / 3, HEIGHT / 12, MARGIN / 2);
        circle15.setFill(Color.WHITE);
        shapes.add(circle15);
        obstacles.add(circle15);
        Circle circle16 = new Circle(WIDTH / 4 + WIDTH / 9, HEIGHT / 8, MARGIN / 2);
        circle16.setFill(Color.WHITE);
        shapes.add(circle16);
        obstacles.add(circle16);
        Circle circle17 = new Circle(WIDTH / 4 + WIDTH / 9, HEIGHT / 8, MARGIN / 2);
        circle17.setFill(Color.WHITE);
        shapes.add(circle17);
        obstacles.add(circle17);
        Circle circle18 = new Circle(WIDTH / 6 + WIDTH / 8, HEIGHT / 10, MARGIN / 2);
        circle18.setFill(Color.WHITE);
        shapes.add(circle18);
        obstacles.add(circle18);
        Circle circle19 = new Circle(WIDTH / 3 + WIDTH / 12, HEIGHT / 15, MARGIN / 2);
        circle19.setFill(Color.WHITE);
        shapes.add(circle19);
        obstacles.add(circle19);
        Circle circle20 = new Circle(WIDTH / 3 + WIDTH / 2, HEIGHT / 2.5, MARGIN / 2);
        circle20.setFill(Color.WHITE);
        shapes.add(circle20);
        obstacles.add(circle20);
        Circle circle21 = new Circle(WIDTH / 3 + WIDTH / 2.5, HEIGHT / 1.75, MARGIN / 2);
        circle21.setFill(Color.WHITE);
        shapes.add(circle21);
        obstacles.add(circle21);
        Circle circle22 = new Circle(WIDTH / 3 + WIDTH / 2, HEIGHT / 2, MARGIN / 2);
        circle22.setFill(Color.WHITE);
        shapes.add(circle22);
        obstacles.add(circle22);
        Circle circle23 = new Circle(WIDTH / 3.25 + WIDTH / 3, HEIGHT / 2.25, MARGIN / 2);
        circle23.setFill(Color.WHITE);
        shapes.add(circle23);
        obstacles.add(circle23);
        Circle circle24 = new Circle(WIDTH / 3.5 + WIDTH / 3.25, HEIGHT / 2.50, MARGIN / 2);
        circle24.setFill(Color.WHITE);
        shapes.add(circle24);
        obstacles.add(circle24);
        Circle circle25 = new Circle(WIDTH / 3.5 + WIDTH / 3.25, HEIGHT / 1.5, MARGIN / 2);
        circle25.setFill(Color.WHITE);
        shapes.add(circle25);
        obstacles.add(circle25);
        Circle circle26 = new Circle(WIDTH / 4.25 + WIDTH / 7, HEIGHT / 1.5, MARGIN / 2);
        circle26.setFill(Color.WHITE);
        shapes.add(circle26);
        obstacles.add(circle26);
        Circle circle27 = new Circle(WIDTH / 5 + WIDTH / 14, HEIGHT / 2, MARGIN / 2);
        circle27.setFill(Color.WHITE);
        shapes.add(circle27);
        obstacles.add(circle27);
        Circle circle28 = new Circle(WIDTH / 7 + WIDTH / 21, HEIGHT / 4.5, MARGIN / 2);
        circle28.setFill(Color.WHITE);
        shapes.add(circle28);
        obstacles.add(circle28);
        Circle circle29 = new Circle(WIDTH / 7 + WIDTH / 21, HEIGHT / 1.5, MARGIN / 2);
        circle29.setFill(Color.WHITE);
        shapes.add(circle29);
        obstacles.add(circle29);
        Circle circle30 = new Circle(WIDTH / 22 + WIDTH / 21, HEIGHT / 1.5, MARGIN / 2);
        circle30.setFill(Color.WHITE);
        shapes.add(circle30);
        obstacles.add(circle30);
        Circle circle31 = new Circle(WIDTH / 1.1 + WIDTH / 21, HEIGHT / 1.5, MARGIN / 2);
        circle31.setFill(Color.WHITE);
        shapes.add(circle31);
        obstacles.add(circle31);
        Circle circle32 = new Circle(WIDTH / 1.3 + WIDTH / 21, HEIGHT / 1.5, MARGIN / 2);
        circle32.setFill(Color.WHITE);
        shapes.add(circle32);
        obstacles.add(circle32);
        Circle circle33 = new Circle(WIDTH / 1.1 + WIDTH / 21, HEIGHT / 5, MARGIN / 2);
        circle33.setFill(Color.WHITE);
        shapes.add(circle33);
        obstacles.add(circle33);
        Circle circle34 = new Circle(WIDTH / 1.1 + WIDTH / 21, HEIGHT / 3.5, MARGIN / 2);
        circle34.setFill(Color.WHITE);
        shapes.add(circle34);
        obstacles.add(circle34);
        Circle circle35 = new Circle(WIDTH / 1.1 + WIDTH / 21, HEIGHT / 2, MARGIN / 2);
        circle35.setFill(Color.WHITE);
        shapes.add(circle35);
        obstacles.add(circle35);
        Circle circle36 = new Circle(WIDTH / 1.1 + WIDTH / 21, HEIGHT / 2.5, MARGIN / 2);
        circle36.setFill(Color.WHITE);
        shapes.add(circle36);
        obstacles.add(circle36);
        Circle circle37 = new Circle(WIDTH / 1.1 + WIDTH / 21, HEIGHT / 1.15, MARGIN / 2);
        circle37.setFill(Color.WHITE);
        shapes.add(circle37);
        obstacles.add(circle37);
        Circle circle38 = new Circle(WIDTH / 2 + WIDTH / 21, HEIGHT / 1.15, MARGIN / 2);
        circle38.setFill(Color.WHITE);
        shapes.add(circle38);
        obstacles.add(circle38);
        Circle circle39 = new Circle(WIDTH / 7 + WIDTH / 21, HEIGHT / 1.15, MARGIN / 2);
        circle39.setFill(Color.WHITE);
        shapes.add(circle39);
        obstacles.add(circle39);
        Circle circle40 = new Circle(WIDTH / 1.5 + WIDTH / 21, HEIGHT / 1.15, MARGIN / 2);
        circle40.setFill(Color.WHITE);
        shapes.add(circle40);
        obstacles.add(circle40);
        Circle circle41 = new Circle(WIDTH / 2 + WIDTH / 21, HEIGHT / 1.15, MARGIN / 2);
        circle41.setFill(Color.WHITE);
        shapes.add(circle41);
        obstacles.add(circle41);
        Circle circle42 = new Circle(WIDTH / 3 + WIDTH / 21, HEIGHT / 1.15, MARGIN / 2);
        circle42.setFill(Color.WHITE);
        shapes.add(circle42);
        obstacles.add(circle42);
        Circle circle43 = new Circle(WIDTH / 50 + WIDTH / 50, HEIGHT / 1.15, MARGIN / 2);
        circle43.setFill(Color.WHITE);
        shapes.add(circle43);
        obstacles.add(circle43);
        Circle circle44 = new Circle(WIDTH / 1.3 + WIDTH / 21, HEIGHT / 1.25, MARGIN / 2);
        circle44.setFill(Color.WHITE);
        shapes.add(circle44);
        obstacles.add(circle44);
        Circle circle45 = new Circle(WIDTH / 2 + WIDTH / 7, HEIGHT / 1.25, MARGIN / 2);
        circle45.setFill(Color.WHITE);
        shapes.add(circle45);
        obstacles.add(circle45);
        Circle circle46 = new Circle(WIDTH / 12 + WIDTH / 24, HEIGHT / 1.25, MARGIN / 2);
        circle46.setFill(Color.WHITE);
        shapes.add(circle46);
        obstacles.add(circle46);
        Circle circle47 = new Circle(WIDTH / 2.75 + WIDTH / 10, HEIGHT / 1.25, MARGIN / 2);
        circle47.setFill(Color.WHITE);
        shapes.add(circle47);
        obstacles.add(circle47);
        Circle circle48 = new Circle(WIDTH / 4.5 + WIDTH / 14.5, HEIGHT / 1.25, MARGIN / 2);
        circle48.setFill(Color.WHITE);
        shapes.add(circle48);
        obstacles.add(circle48);
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
    public synchronized void refresh() throws InterruptedException {
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
        movingBall1.setCenterX(Double.parseDouble(parts[5]));
        movingBall1.setCenterY(Double.parseDouble(parts[6]));
        movingBall2.setCenterX(Double.parseDouble(parts[7]));
        movingBall2.setCenterY(Double.parseDouble(parts[8]));
        movingBall3.setCenterX(Double.parseDouble(parts[9]));
        movingBall3.setCenterY(Double.parseDouble(parts[10]));
        movingBall4.setCenterX(Double.parseDouble(parts[11]));
        movingBall4.setCenterY(Double.parseDouble(parts[12]));
        movingBall5.setCenterX(Double.parseDouble(parts[13]));
        movingBall5.setCenterY(Double.parseDouble(parts[14]));
        
        failCount1 = Integer.parseInt(parts[15]);
        failCount2 = Integer.parseInt(parts[16]);

        // If collision has occurred, reset positions
        if (checkShapeIntersection(leftPaddle)) {
            leftPaddle.setFill(Color.WHITE);
            outputToServer.println(LEFT_COLLISION);
            outputToServer.flush();
            fails1.setText(Integer.toString(failCount1));
        } else {
            leftPaddle.setFill(Color.web("#cc3300"));
            leftPaddle.setY(Double.parseDouble(parts[0]));
            leftPaddle.setX(Double.parseDouble(parts[2]));
        }
        if (checkShapeIntersection(rightPaddle)) {
            rightPaddle.setFill(Color.WHITE);
            outputToServer.println(RIGHT_COLLISION);
            outputToServer.flush();
            fails2.setText(Integer.toString(failCount2));
        } else {
            rightPaddle.setFill(Color.web("#3366cc"));
            rightPaddle.setY(Double.parseDouble(parts[1]));
            rightPaddle.setX(Double.parseDouble(parts[3]));
        }
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

    private boolean checkShapeIntersection(Shape block) {
        collisionDetected = false;
        for (Shape obstacle : obstacles) {
            if (obstacle != block) {
                obstacle.setFill(Color.WHITE);

                Shape intersect = Shape.intersect(block, obstacle);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    collisionDetected = true;
                    return true;
                }
            }
        }

        return false;
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
