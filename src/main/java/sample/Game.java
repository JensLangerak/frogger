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

    private Car test;

    public Game(GraphicsContext context, double width, double height)
    {
        startTimeNanoSeconds = System.nanoTime();
        player = new Player(new Point2D(width / 2,height - 1 * TileSize));
        this.context = context;
        this.width = width;
        this.height = height;
        pressedKeys = new HashMap<KeyCode, KeyEvent>();

        test = new Car(new Point2D(width / 2, height - 10 * TileSize), 40, 5);
    }
    boolean gameOver = false;
    public void Thick(long nanoTime)
    {
        if (gameOver) return;

        context.clearRect(0,0,width,height);
        ProcessKeyEvents();
        player.CheckBoundary(width, height);
        test.Thick(nanoTime);
        test.CheckBoundary(width, height);

        if (player.Collision(test)) {
            context.setTextAlign(TextAlignment.CENTER);
            context.setTextBaseline(VPos.CENTER);
            context.setFill(Color.RED);
            context.setFont(new Font(60));
            context.fillText("GAME OVER", width / 2, height /2);
            gameOver = true;
            return;
        }
        player.Draw(context);
        test.Draw(context);

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
