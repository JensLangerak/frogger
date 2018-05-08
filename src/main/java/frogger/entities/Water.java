package frogger.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Deadly water.
 */
public class Water extends Entity {
    /**
     * Create water.
     *
     * @param center center position of the water.
     * @param width width of the water.
     * @param height height of the water.
     */
    public Water(Point2D center, double width, double height) {
        super(center, width, height);
    }

    @Override
    public void draw(GraphicsContext context, double x, double y) {
        context.setFill(Color.BLUE);
        context.fillRect(x, y, getWidth(), getHeight());
    }

    @Override
    public void checkBoundary(double width, double height) {

    }

    @Override
    public void hit(Entity other) {
        if (other instanceof Player) {
            Player player = (Player) other;
            player.setStatus(Player.Status.Drowning);
        }
    }
}
