package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {
    private Point2D centerPosition;
    private double hitHeight;
    private double hitWidth;

    public Entity(Point2D position, double width, double height){
        centerPosition = position;
        hitHeight = height;
        hitWidth = width;
    }

    public double getHeight() {
         return hitHeight;
    }

    public double getWidth() {
        return hitWidth;
    }

    public Point2D getCenterPosition() {
        return centerPosition;
    }

    protected void Move(double x, double y){
        centerPosition = centerPosition.add(x, y);
    }

    /**
     * Draw the object onto the context
     */
    abstract void Draw(GraphicsContext context);

    /**
     * Check if two entities collide.
     * @param other
     * @return true if this collides with other.
     */
    public boolean Collision(Entity other) {
        double distanceX = Math.abs(this.getCenterPosition().getX() - other.getCenterPosition().getX());
        double distanceY = Math.abs(this.getCenterPosition().getY() - other.getCenterPosition().getY());

        double hitDistanceX = (this.getWidth() + other.getWidth()) * 0.5;
        double hitDistanceY = (this.getHeight() + other.getHeight()) * 0.5;

        return distanceX < hitDistanceX && distanceY < hitDistanceY;
    }

    public void Thick(long nanoTime){};

    public abstract void CheckBoundary(double width, double height);
}

