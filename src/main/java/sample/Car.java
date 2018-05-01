package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Car extends Entity {

    public Car(Point2D position, double length, double speed) {
        super(position, length, Game.TileSize * 0.9);
        this.speed = speed;
    }

    private double speed;

    void Draw(GraphicsContext context) {
        double x = getCenterPosition().getX() - getWidth() / 2;
        double y = getCenterPosition().getY() - getHeight() / 2;

        context.setFill(Color.YELLOW);
        context.fillRect(x, y, getWidth(), getHeight());
    }

    @Override
    public void Thick(long nanoTime){
        Move(speed, 0);
    }

    public void CheckBoundary(double width, double height) {
        double left = getCenterPosition().getX() - getWidth() / 2;
        double right = getCenterPosition().getX() + getWidth() / 2;

        if (left < 0 || right > width)
            speed = speed * -1;

    }
}
