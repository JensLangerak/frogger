package frogger.entities.LaneEntities;

import frogger.entities.Entity;
import frogger.entities.Player;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * A boat entity.
 */
public class Boat extends LaneEntity {
    /**
     * Create a new boat.
     * @param position start position.
     * @param length length of the boat.
     * @param speed speed of the boat.
     */
    public Boat(Point2D position, double length, double speed) {
        super(position, length, speed);
    }

    @Override
    void drawShape(GraphicsContext context, double x, double y) {
        context.fillOval(x, y, getWidth(), getHeight());
    }

    /**
     * A player can stand on a boat. This prevents drowning.
     * On collision it will move with the boat.
     * @param other
     */
    @Override
    public void hit(Entity other) {
        if (other instanceof Player) {
            Player player = (Player) other;
            player.move(getSpeed(), 0);
            player.setStatus(Player.Status.Floating);
        }
    }
}

