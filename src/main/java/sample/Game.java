package sample;

import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.sql.Time;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static javafx.scene.input.KeyCode.K;
import static javafx.scene.input.KeyCode.M;
import static javafx.scene.input.KeyCode.P;

public class Game {
    public static final double TileSize = 30;

    private Player player;
    private GraphicsContext context;
    private long startTimeNanoSeconds;
    private double width;
    private double height;
    private Map<KeyCode, KeyEvent> pressedKeys;

    private List<Lane> lanes;

    public Game(GraphicsContext context, double width, double height)
    {
        this.context = context;
        this.width = width;
        this.height = height;
        pressedKeys = new HashMap<KeyCode, KeyEvent>();
        lanes = new LinkedList<>();
        Restart();
    }

    public void Restart() {
        startTimeNanoSeconds = System.nanoTime();
        player = new Player(new Point2D(width / 2,height - 1 * TileSize));
        lanes.clear();
        createLanes();

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
    public void Thick(long nanoTime)
    {
        if (gameOver) {
            if (pressedKeys.containsKey(KeyCode.SPACE))
                Restart();
            return;
        };

        context.clearRect(0,0,width,height);
        ProcessKeyEvents();
        player.CheckBoundary(width, height);
        lanes.forEach(lane -> lane.MoveCars(nanoTime));
        lanes.forEach(lane ->lane.CheckOutOfBoundaries(width,height ));
        lanes.forEach(lane ->lane.Spawn(nanoTime));

        if ( lanes.stream().anyMatch(lane -> lane.CheckCollision(player))) {
            context.setTextAlign(TextAlignment.CENTER);
            context.setTextBaseline(VPos.CENTER);
            context.setFill(Color.RED);
            context.setFont(new Font(60));
            context.fillText("GAME OVER", width / 2, height /2);
            gameOver = true;
            return;
        }
        player.Draw(context);
        lanes.forEach(lane ->lane.Draw(context));

    }

    public void KeyPressed(KeyEvent event){
        pressedKeys.put(event.getCode(), event);
    }

    public void KeyReleased(KeyEvent event){
        pressedKeys.remove(event.getCode());
    }

    private void ProcessKeyEvents() {
        for (KeyEvent event: pressedKeys.values()) {
            switch (event.getCode()) {
                case UP:
                    player.Move(Player.Direction.UP);
                    break;
                case DOWN:
                    player.Move(Player.Direction.DOWN);
                    break;
                case LEFT:
                    player.Move(Player.Direction.LEFT);
                    break;
                case RIGHT:
                    player.Move(Player.Direction.RIGHT);
                    break;
                case SPACE:
                    Restart();
                    break;
            }
            
        }
        pressedKeys.clear();
    }


}
