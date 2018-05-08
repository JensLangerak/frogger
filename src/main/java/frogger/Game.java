package frogger;

import frogger.Utils.Direction;
import frogger.entities.Entity;
import frogger.entities.LaneEntities.builders.BoatBuilder;
import frogger.entities.LaneEntities.builders.CarBuilder;
import frogger.entities.Player;
import frogger.entities.Target;
import frogger.entities.Water;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class that controls the game.
 */
public class Game {
    public static final double TILE_SIZE = 30;

    private Player player;
    private GraphicsContext context;
    private long startTimeNanoSeconds;
    private double width;
    private double height;
    private Map<KeyCode, KeyEvent> pressedKeys;

    private List<Lane> lanes;
    private List<Entity> entities;

    /**
     * Create a new frogger game.
     * @param context context to draw the game on.
     * @param width width of the field.
     * @param height height of the field.
     */
    public Game(GraphicsContext context, double width, double height) {
        this.context = context;
        this.width = width;
        this.height = height;

        pressedKeys = new HashMap<>();
        lanes = new LinkedList<>();
        entities = new LinkedList<>();

        restart();
    }

    /**
     * Restart the game.
     */
    private void restart() {
        startTimeNanoSeconds = System.nanoTime();
        player = new Player(new Point2D(width / 2, height - 1 * TILE_SIZE));

        lanes.clear();
        createLanes();
        entities.clear();

        entities.add(new Target(new Point2D(width / 2, height - 18 * TILE_SIZE)));
        entities.add(new Water(
                new Point2D(width / 2, height - 13.5 * TILE_SIZE),
                width,
                6 * TILE_SIZE));
    }

    /**
     * Create the lanes.
     */
    private void createLanes() {
        double speed = 4;
        for (int i = 0; i < 6; i++) {
            double x = speed > 0 ? 0 : width;
            CarBuilder builder = new CarBuilder();
            lanes.add(new Lane(
                    new Point2D(x, height - (i + 3) * TILE_SIZE),
                    speed,
                    builder,
                    3,
                    1,
                    0.013));
            speed *= -1;
        }

        speed = 3;
        for (int i = 0; i < 6; i++) {
            double x = speed > 0 ? 0 : width;
            BoatBuilder builder = new BoatBuilder();
            lanes.add(new Lane(
                    new Point2D(x, height - (i + 3 + 8) * TILE_SIZE),
                    speed,
                    builder,
                    6,
                    2,
                    0.02));
            speed *= -1;
        }
    }

    /**
     *
     * @return true if the player is alive and has not yet won.
     */
    private boolean gameRunning() {
        return !player.hasWon() && player.isAlive();
    }

    /**
     * Perform a game thick.
     * Handle the key inputs, move the entities and draw the game.
     * @param nanoTime time of the thick.
     */
    public void thick(long nanoTime) {
        if (!gameRunning()) {
            if (pressedKeys.containsKey(KeyCode.SPACE)) {
                restart();
            }
            return;
        }

        context.clearRect(0, 0, width, height);
        player.resetStatus();

        double timeSinceStart = (nanoTime - startTimeNanoSeconds) / 1000000000.0;
        int waitTime = (int) (4 - timeSinceStart);
        if (waitTime <= 0) {
            processKeyEvents();
        }

        moveEntities(nanoTime);

        draw(waitTime);
    }

    /**
     * Draw the game.
     * @param waitTime seconds since start.
     */
    private void draw(int waitTime) {
        if (player.hasWon()) {
            drawWon();
            return;
        }

        if (!player.isAlive()) {
            drawGameOver();
            return;
        }

        entities.forEach(target -> target.draw(context));
        lanes.forEach(lane -> lane.draw(context));
        player.draw(context);

        if (waitTime >= 0) {
            drawCountDown(waitTime);
        }
    }

    /**
     * Perform a thick on all the entities.
     * Move all the entities and check collisions.
     * @param nanoTime time of the thick.
     */
    private void moveEntities(long nanoTime) {
        entities.forEach(e -> e.collision(player));

        lanes.forEach(lane -> lane.moveEntities(nanoTime));
        lanes.forEach(lane -> lane.checkOutOfBoundaries(width, height));
        lanes.forEach(lane -> lane.spawn(nanoTime));
        lanes.forEach(lane -> lane.checkCollision(player));
    }

    /**
     * Draw the won message.
     */
    private  void drawWon() {
        drawCenteredText(Color.YELLOW, 80, "YOU WON!");
    }

    /**
     * Draw the game over message.
     */
    private void drawGameOver() {
        drawCenteredText(Color.RED, 60, "GAME OVER");
    }

    /**
     * Draw the countdown.
     * @param waitTime time to draw.
     */
    private void drawCountDown(int waitTime) {
        String text = waitTime > 0 ? Integer.toString(waitTime) : "GO";
        drawCenteredText(Color.ORANGE, 80, text);
    }

    /**
     * Draw the text at the center of the screen.
     * @param color color of the text.
     * @param fontSize text size.
     * @param text text to draw.
     */
    private void drawCenteredText(Color color, int fontSize, String text) {
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
        context.setFill(color);
        context.setFont(new Font(fontSize));
        context.fillText(text, width / 2, height / 2);
    }

    /**
     * Notify the game that a key is pressed during the current thick. Add it
     * to the list of keys that should be handled.
     * @param event pressed key event.
     */
    public void keyPressed(KeyEvent event) {
        pressedKeys.put(event.getCode(), event);
    }

    /**
     * Notify the game that a key is released during the current thick. Remove this
     * key from the list of keys that should be handled.
     * @param event released key event.
     */
    public void keyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
    }

    /**
     * Check which keys have been pressed.
     */
    private void processKeyEvents() {
        for (KeyEvent event: pressedKeys.values()) {
            switch (event.getCode()) {
                case UP:
                    player.move(Direction.UP);
                    break;
                case DOWN:
                    player.move(Direction.DOWN);
                    break;
                case LEFT:
                    player.move(Direction.LEFT);
                    break;
                case RIGHT:
                    player.move(Direction.RIGHT);
                    break;
                case SPACE:
                    restart();
                    break;
                default:
                    break;
            }
            
        }
        pressedKeys.clear();
        player.checkBoundary(width, height);
    }
}
