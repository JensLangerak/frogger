package frogger.entities;

import frogger.Game;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The entity that the player should try to get to.
 */
public class Target extends Entity {

    /**
     * Create a new target.
     *
     * @param position position of the target.
     */
    public Target(Point2D position) {
        super(position, Game.TILE_SIZE, Game.TILE_SIZE);
    }

    @Override
    public void draw(GraphicsContext context, double x, double y) {
        context.setFill(Color.RED);
        context.fillOval(x, y, getWidth(), getHeight());
    }

    @Override
    public void checkBoundary(double width, double height) {

    }

    @Override
    public void hit(Entity other) {
        if (other instanceof Player) {
            Player player = (Player) other;
            player.setStatus(Player.Status.Won);
        }
    }
}
