package frogger.entities.LaneEntities.builders;

import frogger.entities.LaneEntities.LaneEntity;
import javafx.geometry.Point2D;

/**
 * Class that creates entities.
 */
public interface LaneEntityBuilder {
    /**
     * Create a new entity.
     * @param position start position of the entity.
     * @param length length of the entity.
     * @param speed speed of the entity.
     * @return a new entity.
     */
    LaneEntity build(Point2D position, double length, double speed);
}
