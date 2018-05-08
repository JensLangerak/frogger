package frogger.entities.LaneEntities.builders;

import frogger.entities.LaneEntities.Boat;
import javafx.geometry.Point2D;

/**
 * A class that create boats.
 */
public class BoatBuilder implements LaneEntityBuilder {
    /**
     * Create a new boat builder.
     */
    public BoatBuilder() { }

    /**
     * Create a new boat.
     * @param position start position.
     * @param length length of the boat.
     * @param speed speed of the boat.
     * @return a new boat.
     */
    public Boat build(Point2D position, double length, double speed) {
        return new Boat(position, length, speed);
    }

}
