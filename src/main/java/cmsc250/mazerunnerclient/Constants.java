package cmsc250.mazerunnerclient;

public interface Constants {
    public static int MOVE_UP = 1;
    public static int MOVE_DOWN = 2;
    public static int GET_GAME_STATE = 5;
    public static int MOVE_LEFT = 3;
    public static int MOVE_RIGHT = 4;
    // changed the constant values to see ifitmakes difference in game state output
    public static double MARGIN = 20.0;
    public static double THICKNESS = 20.0;
    public static double LENGTH = 20.0;
    public static double WIDTH = 640.0;
    public static double HEIGHT = 480.0;
    // RED player (left)
    public static int LEFT_COLLISION = 6;
    // BLUE player (right)
    public static int RIGHT_COLLISION = 7;
}