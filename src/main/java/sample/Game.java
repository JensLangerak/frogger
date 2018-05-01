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
import java.util.stream.Collectors;

public class Game {
    public static final double TileSize = 30;

    private Player player;
    private GraphicsContext context;
    private long startTimeNanoSeconds;
    private double width;
    private double height;
    private Map<KeyCode, KeyEvent> pressedKeys;

    private List<Lane> carLanes;
    private List<Target> targets;
    private List<Lane> boatLanes;
    private Water water;

    public Game(GraphicsContext context, double width, double height)
    {
        this.context = context;
        this.width = width;
        this.height = height;
        pressedKeys = new HashMap<KeyCode, KeyEvent>();
        carLanes = new LinkedList<>();
        boatLanes = new LinkedList<>();
        targets = new LinkedList<>();
        restart();
    }

    public void restart() {
        startTimeNanoSeconds = System.nanoTime();
        player = new Player(new Point2D(width / 2,height - 1 * TileSize));
        carLanes.clear();
        boatLanes.clear();
        createLanes();
        targets.add(new Target(new Point2D(width / 2,height - 18 * TileSize)));
        gameOver = false;
    }

    private void createLanes() {
        double speed = 4;
        for (int i = 0; i < 6; i++) {
            double x = speed > 0 ? 0 : width;
            CarBuilder builder = new CarBuilder();
            carLanes.add(new Lane(new Point2D(x, height - (i + 3) * TileSize), speed, builder, 3, 1, 0.013));
            speed *= -1;
        }

        speed = 3;
        for (int i = 0; i < 6; i++) {
            double x = speed > 0 ? 0 : width;
            BoatBuilder builder = new BoatBuilder();
            boatLanes.add(new Lane(new Point2D(x, height - (i + 3 + 8) * TileSize), speed, builder, 6, 2, 0.02));
            speed *= -1;
        }

        water = new Water(new Point2D(width / 2, height - 13.5 * TileSize), width, 6 * TileSize);
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

        double timeSinceStart = (nanoTime - startTimeNanoSeconds) / 1000000000.0;
        int waitTime = (int) (6 - timeSinceStart);
        if (waitTime <= 0)
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

        carLanes.forEach(lane -> lane.moveCars(nanoTime));
        carLanes.forEach(lane ->lane.checkOutOfBoundaries(width,height ));
        carLanes.forEach(lane ->lane.spawn(nanoTime));

        boatLanes.forEach(lane -> lane.moveCars(nanoTime));
        boatLanes.forEach(lane ->lane.checkOutOfBoundaries(width,height ));
        boatLanes.forEach(lane ->lane.spawn(nanoTime));

        boolean onBoat = false;
        if ( boatLanes.stream().anyMatch(lane -> lane.checkCollision(player))) {
            onBoat =true;
            for (Car boat:
                    boatLanes.stream().map(lane -> lane.getCollisions(player)).flatMap(l -> l.stream()).collect(Collectors.toList())
                 ) {
                player.Move(boat.getSpeed(), 0);
            }
        }

        if (carLanes.stream().anyMatch(lane -> lane.checkCollision(player))
                || water.collision(player) && !onBoat) {
            context.setTextAlign(TextAlignment.CENTER);
            context.setTextBaseline(VPos.CENTER);
            context.setFill(Color.RED);
            context.setFont(new Font(60));
            context.fillText("GAME OVER", width / 2, height /2);
            gameOver = true;
            return;
        }
        water.draw(context);

        carLanes.forEach(lane ->lane.draw(context));
        boatLanes.forEach(lane -> lane.draw(context));
        targets.forEach(target -> target.draw(context));

        player.draw(context);

        if (waitTime >= 0) {
            context.setTextAlign(TextAlignment.CENTER);
            context.setTextBaseline(VPos.CENTER);
            context.setFill(Color.ORANGE);
            context.setFont(new Font(80));
            if (waitTime > 0)
                context.fillText(Integer.toString((int) waitTime), width / 2, height / 2);
            if (waitTime == 0)
                context.fillText("GO", width / 2, height / 2);

        }

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
