package frogger.entities.LaneEntities.builders;

import frogger.entities.LaneEntities.Car;
import javafx.geometry.Point2D;

/**
 * A class that create cars.
 */
public class CarBuilder implements LaneEntityBuilder {
    /**
     * Create a new car builder.
     */
    public CarBuilder() { }

    /**
     * Create a new Car.
     * @param position start position.
     * @param length length of the car.
     * @param speed speed of the car.
     * @return a new car.
     */
    public Car build(Point2D position, double length, double speed) {
        return new Car(position, length, speed);
    }

}
