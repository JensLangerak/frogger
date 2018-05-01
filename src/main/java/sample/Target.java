package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Target extends Entity {

    public Target(Point2D position){
        super(position, Game.TileSize, Game.TileSize);
    }

    @Override
    void draw(GraphicsContext context) {
        context.setFill(Color.RED);
        double x = getCenterPosition().getX() - getWidth() / 2;
        double y = getCenterPosition().getY() - getHeight() / 2;
        context.fillOval(x, y, getWidth(), getHeight());
    }

    @Override
    public void checkBoundary(double width, double height) {
        throw new NotImplementedException();
    }
}
