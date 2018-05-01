package sample;

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

public class Game {
    public static final double TileSize = 30;

    private Player player;
    private GraphicsContext context;
    private long startTimeNanoSeconds;
    private double width;
    private double height;
    private Map<KeyCode, KeyEvent> pressedKeys;

    private List<Lane> lanes;
    private List<Target> targets;

    public Game(GraphicsContext context, double width, double height)
    {
        this.context = context;
        this.width = width;
        this.height = height;
        pressedKeys = new HashMap<KeyCode, KeyEvent>();
        lanes = new LinkedList<>();
        targets = new LinkedList<>();
        restart();
    }

    public void restart() {
        startTimeNanoSeconds = System.nanoTime();
        player = new Player(new Point2D(width / 2,height - 1 * TileSize));
        lanes.clear();
        createLanes();
        targets.add(new Target(new Point2D(width / 2,height - 18 * TileSize)));
        gameOver = false;
    }

    private void createLanes() {
        double speed = 4;
        for (int i = 0; i < 6; i++) {
            double x = speed > 0 ? 0 : width;
            lanes.add(new Lane(new Point2D(x, height - (i + 3) * TileSize), speed));
            speed *= -1;
        }
    }

    boolean gameOver = false;
    public void thick(long nanoTime)
    {
        if (gameOver) {
            if (pressedKeys.containsKey(KeyCode.SPACE))
                restart();
            return;
        };

        context.clearRect(0,0,width,height);
        processKeyEvents();
        player.checkBoundary(width, height);
        if (targets.stream().anyMatch(target -> target.collision(player))) {
            context.setTextAlign(TextAlignment.CENTER);
            context.setTextBaseline(VPos.CENTER);
            context.setFill(Color.YELLOW);
            context.setFont(new Font(80));
            context.fillText("YOU WON!", width / 2, height /2);
            gameOver = true;
            return;
        }

        lanes.forEach(lane -> lane.moveCars(nanoTime));
        lanes.forEach(lane ->lane.checkOutOfBoundaries(width,height ));
        lanes.forEach(lane ->lane.spawn(nanoTime));

        if ( lanes.stream().anyMatch(lane -> lane.checkCollision(player))) {
            context.setTextAlign(TextAlignment.CENTER);
            context.setTextBaseline(VPos.CENTER);
            context.setFill(Color.RED);
            context.setFont(new Font(60));
            context.fillText("GAME OVER", width / 2, height /2);
            gameOver = true;
            return;
        }
        player.draw(context);
        lanes.forEach(lane ->lane.draw(context));
        targets.forEach(target -> target.draw(context));

    }

    public void keyPressed(KeyEvent event){
        pressedKeys.put(event.getCode(), event);
    }

    public void keyReleased(KeyEvent event){
        pressedKeys.remove(event.getCode());
    }

    private void processKeyEvents() {
        for (KeyEvent event: pressedKeys.values()) {
            switch (event.getCode()) {
                case UP:
                    player.move(Player.Direction.UP);
                    break;
                case DOWN:
                    player.move(Player.Direction.DOWN);
                    break;
                case LEFT:
                    player.move(Player.Direction.LEFT);
                    break;
                case RIGHT:
                    player.move(Player.Direction.RIGHT);
                    break;
                case SPACE:
                    restart();
                    break;
            }
            
        }
        pressedKeys.clear();
    }


}
