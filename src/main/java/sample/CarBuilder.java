package sample;

import javafx.geometry.Point2D;

public class CarBuilder {
    public CarBuilder(){};

    public Car build(Point2D position, double length, double speed)
    {
        return new Car(position, length, speed);
    }

}
