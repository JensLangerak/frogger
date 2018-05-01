package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Boat extends Car {
    private Color color;
    public Boat(Point2D position, double length, double speed) {
        super(position, length, speed);
        color = Color.color(Math.random(), Math.random(), Math.random());
    }


    @Override
    void draw(GraphicsContext context) {
        double x = getCenterPosition().getX() - getWidth() / 2;
        double y = getCenterPosition().getY() - getHeight() / 2;

        context.setFill(color);
        context.fillOval(x, y, getWidth(), getHeight());
    }
}

