package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Water extends Entity {
    public Water(Point2D center, double width, double height)
    {
        super(center, width, height);
    }

    @Override
    void draw(GraphicsContext context) {
        double x = getCenterPosition().getX() - getWidth() / 2;
        double y = getCenterPosition().getY() - getHeight() / 2;

        context.setFill(Color.BLUE);
        context.fillRect(x, y, getWidth(), getHeight());
    }

    @Override
    public void checkBoundary(double width, double height) {

    }
}
