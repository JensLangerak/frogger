package frogger.entities.LaneEntities;

import frogger.Game;
import frogger.entities.Entity;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * An entity that moves in a lane.
 */
public abstract class LaneEntity extends Entity {
    private Color color;
    private double speed;

    /**
     * Create a new entity that move in a lane.
     * @param position start position.
     * @param length length of the entity.
     * @param speed speed of the entity.
     */
    public LaneEntity(Point2D position, double length, double speed) {
        super(position, length, Game.TILE_SIZE * 0.9);
        this.speed = speed;
        color = Color.color(Math.random(), Math.random(), Math.random());
    }


    @Override
    public void draw(GraphicsContext context, double x, double y) {
        context.setFill(color);
        drawShape(context, x, y);
    }

    /**
     * Draw the shape of the entity at position x,y.
     * @param context context to draw on.
     * @param x x position.
     * @param y y position.
     */
    abstract void drawShape(GraphicsContext context, double x, double y);

    @Override
    public void thick(long nanoTime) {
        move(speed, 0);
    }

    @Override
    public void checkBoundary(double width, double height) {
    }

    /**
     * Check if the entity is outside the game.
     * @param width width of the game.
     * @param height height of the game.
     * @param useDirection if true it will only check the boundary
     *                     at the direction it is traveling. This allows an entity to be spawn
     *                     outside the game.
     * @return true if the entity is outside the game.
     */
    public boolean outOfBounds(double width, double height, boolean useDirection) {
        double left = getCenterPosition().getX() - getWidth() / 2;
        double right = getCenterPosition().getX() + getWidth() / 2;
        if (!useDirection) {
            return left > width || right < 0;
        } else {
            return speed < 0 ? right < 0 : left > width;
        }
    }

    /**
     * Get the speed of the entity.
     * @return the speed.
     */
    public double getSpeed() {
        return speed;
    }
}
