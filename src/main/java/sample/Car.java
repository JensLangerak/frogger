package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Car extends Entity {

    private Color color;
    public Car(Point2D position, double length, double speed) {
        super(position, length, Game.TileSize * 0.9);
        this.speed = speed;
        color = Color.color(Math.random(), Math.random(), Math.random());
    }

    private double speed;

    void draw(GraphicsContext context) {
        double x = getCenterPosition().getX() - getWidth() / 2;
        double y = getCenterPosition().getY() - getHeight() / 2;

        context.setFill(color);
        context.fillRect(x, y, getWidth(), getHeight());
    }

    @Override
    public void thick(long nanoTime){
        Move(speed, 0);
    }

    public void checkBoundary(double width, double height) {

        double left = getCenterPosition().getX() - getWidth() / 2;
        double right = getCenterPosition().getX() + getWidth() / 2;

        //if (left < 0 || right > width)
        //    speed = speed * -1;

    }

    public boolean outOfBounds(double width, double height, boolean useDirection) {
        double left = getCenterPosition().getX() - getWidth() / 2;
        double right = getCenterPosition().getX() + getWidth() / 2;
        if (!useDirection)
            return left > width || right < 0;
        else
            return (speed < 0) ? right < 0 : left > width;
    }

}
