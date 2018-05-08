package frogger;


import frogger.entities.Entity;
import frogger.entities.LaneEntities.LaneEntity;
import frogger.entities.LaneEntities.builders.LaneEntityBuilder;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;

/**
 * Class that represents a lane. Keeps track of the entities in the lane and is able
 * to spawn new entities.
 */
public class Lane {

    private Point2D spawnPoint;
    private double speed;
    private LinkedList<LaneEntity> entities;

    private double maxLength;
    private double minLength;
    private LaneEntityBuilder builder;
    private double spawnChance;

    /**
     * Create a new lane.
     * @param spawn spawn point of the new entities.
     * @param speed speed of the entities.
     * @param builder builder that can create the entities.
     * @param maxLength max length of an entity.
     * @param minLength min length of an entity.
     * @param spawnChance spawn rate.
     */
    public Lane(Point2D spawn, double speed, LaneEntityBuilder builder,
                double maxLength, double minLength, double spawnChance) {
        this.spawnPoint = spawn;
        this.speed = speed;
        entities = new LinkedList<>();
        this.builder = builder;

        this.maxLength = maxLength;
        this.minLength = minLength;
        this.spawnChance = spawnChance;
    }

    /**
     * Check if a new entity should be spawned. Based on last spawned item and the spawnChange.
     * @param time time of the current thick.
     */
    public void spawn(long time) {
        if (!entities.isEmpty()) {
            double xRef = entities.getLast().getCenterPosition().getX();
            double outerPoint = entities.getLast().getWidth() / 2 + 0.25 * Game.TILE_SIZE;

            if (speed > 0 && xRef - outerPoint <= spawnPoint.getX()) {
                return;
            }
            if (speed < 0 && xRef + outerPoint >= spawnPoint.getX()) {
                return;
            }
        }

        if (Math.random() < spawnChance) {
            double dx = speed > 0 ? -maxLength : maxLength;
            dx *= Game.TILE_SIZE;
            double r = Math.random();

            entities.add(builder.build(
                    new Point2D(spawnPoint.getX() + dx, spawnPoint.getY()),
                    (r * maxLength + (1 - r) * minLength) * Game.TILE_SIZE,
                    speed));
        }
    }

    /**
     * Move all the entities in this lane.
     * @param time current thick time.
     */
    public void moveEntities(long time) {
        entities.forEach(car -> car.thick(time));
    }

    /**
     * Check if an entity is out of bounds and should be removed.
     * @param width width of the game.
     * @param height height of the game.
     */
    public void checkOutOfBoundaries(double width, double height) {
        entities.removeIf(car -> car.outOfBounds(width, height, true));
    }

    /**
     * Check if any entity collides with entity, and perform the collision actions.
     * @param entity other entity.
     */
    public void checkCollision(Entity entity) {
        entities.forEach(e -> e.collision(entity));
    }


    /**
     * Draw the lane on the screen.
     * @param context context to draw onto.
     */
    public void draw(GraphicsContext context) {
        entities.forEach(car -> car.draw(context));
    }
}
