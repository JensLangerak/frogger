package frogger.entities.LaneEntities;

import frogger.entities.Entity;
import frogger.entities.Player;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * A Car entity.
 */
public class Car extends LaneEntity {
    /**
     * Create a new Car.
     * @param position start position.
     * @param length length of the car.
     * @param speed speed of the car.
     */
    public Car(Point2D position, double length, double speed) {
        super(position, length, speed);
    }

    @Override
    void drawShape(GraphicsContext context, double x, double y) {
        context.fillRect(x, y, getWidth(), getHeight());
    }

    /**
     * A player dies when it hits a car.
     * @param other
     */
    @Override
    public void hit(Entity other) {
        if (other instanceof Player) {
            Player player = (Player) other;
            player.setStatus(Player.Status.Dead);
        }
    }
}

