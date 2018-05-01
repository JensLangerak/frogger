package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import static javafx.scene.input.KeyCode.M;

public class Game {
    public static final double TileSize = 20;

    private Player player;
    private GraphicsContext context;
    private long startTimeNanoSeconds;
    private double width;
    private double height;
    private Map<KeyCode, KeyEvent> pressedKeys;

    public Game(GraphicsContext context, double width, double height)
    {
        startTimeNanoSeconds = System.nanoTime();
        player = new Player(new Point2D(width / 2,height - 1 * TileSize));
        this.context = context;
        this.width = width;
        this.height = height;
        pressedKeys = new HashMap<KeyCode, KeyEvent>();
    }

    public void Thick(long nanoTime)
    {
        context.clearRect(0,0,width,height);
        ProcessKeyEvents();
        player.CheckBoundary(width, height);




        player.Draw(context);

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
            }
            
        }
        pressedKeys.clear();
    }


}
