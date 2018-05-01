package sample;

import javafx.geometry.Point2D;

public class BoatBuilder extends CarBuilder{
    public BoatBuilder(){}

    public Boat build(Point2D position, double length, double speed)
    {
        return new Boat(position, length, speed);
    }

}
