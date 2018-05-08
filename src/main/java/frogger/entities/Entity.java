package frogger.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * An entity that exist in the game.
 */
public abstract class Entity {
    private Point2D centerPosition;
    private double hitHeight;
    private double hitWidth;

    /**
     * Create a new entity.
     *
     * @param position start position.
     * @param width    width of the entity.
     * @param height   height of the entity.
     */
    public Entity(Point2D position, double width, double height) {
        centerPosition = position;
        hitHeight = height;
        hitWidth = width;
    }

    /**
     * @return the height.
     */
    public double getHeight() {
        return hitHeight;
    }

    /**
     * @return the width.
     */
    public double getWidth() {
        return hitWidth;
    }

    /**
     * @return the center position.
     */
    public Point2D getCenterPosition() {
        return centerPosition;
    }

    /**
     * Move the object by (x,y).
     *
     * @param x horizontal movement.
     * @param y vertical movement.
     */
    public void move(double x, double y) {
        centerPosition = centerPosition.add(x, y);
    }

    /**
     * Draw the object onto the context.
     * @param context the context to draw on.
     */
    public void draw(GraphicsContext context) {
        double x = getCenterPosition().getX() - getWidth() / 2;
        double y = getCenterPosition().getY() - getHeight() / 2;

        draw(context, x, y);
    }

    /**
     * Draw the object at position x, y.
     *
     * @param context context to draw on.
     * @param x x position.
     * @param y y position.
     */
    public abstract void draw(GraphicsContext context, double x, double y);


    /**
     * Check if two entities collide.
     *
     * @param other other entity.
     * @return true if this collides with other.
     */
    public boolean checkCollision(Entity other) {
        double distanceX =
                Math.abs(this.getCenterPosition().getX() - other.getCenterPosition().getX());
        double distanceY =
                Math.abs(this.getCenterPosition().getY() - other.getCenterPosition().getY());

        double hitDistanceX = (this.getWidth() + other.getWidth()) * 0.5;
        double hitDistanceY = (this.getHeight() + other.getHeight()) * 0.5;

        return distanceX < hitDistanceX && distanceY < hitDistanceY;
    }

    /**
     * Check if this entity collides with other and perform the necessary actions.
     *
     * @param other other entity.
     */
    public void collision(Entity other) {
        if (checkCollision(other)) {
            hit(other);
        }
    }

    /**
     * Handle the collision of the two entities.
     *
     * @param other other entity.
     */
    public void hit(Entity other) {
    }

    /**
     * Perform a thick.
     *
     * @param nanoTime thick time.
     */
    public void thick(long nanoTime) {
    }

    /**
     * Check if the object is still inside the boundaries of the game.
     *
     * @param width game width.
     * @param height game height.
     */
    public abstract void checkBoundary(double width, double height);
}

