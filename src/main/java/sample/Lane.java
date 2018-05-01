package sample;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Lane {

    private Point2D spawnPoint;
    private double speed;
    private LinkedList<Car> cars;

    private double maxLength;
    private double minLength;
    private CarBuilder builder;
    private double spawnChance;

    public Lane(Point2D spawn, double speed, CarBuilder builder, double maxLength, double minLength, double spawnChance)
    {
        this.spawnPoint = spawn;
        this.speed = speed;
        cars = new LinkedList<>();
        this.builder = builder;

        this.maxLength = maxLength;
        this.minLength = minLength;
        this.spawnChance = spawnChance;
    }

    public void spawn(long time) {
        if (!cars.isEmpty()) {
            double xRef = cars.getLast().getCenterPosition().getX();
            double outerPoint = cars.getLast().getWidth() / 2 + 0.25 * Game.TileSize;

            if (speed > 0 && xRef - outerPoint <= spawnPoint.getX())
                return;
            if (speed < 0 && xRef + outerPoint >= spawnPoint.getX())
                return;
        }

        if (Math.random() < spawnChance) {
            double dx = speed > 0 ? -maxLength : maxLength;
            dx *= Game.TileSize;
            double r = Math.random();

            cars.add(builder.build(new Point2D(spawnPoint.getX() + dx, spawnPoint.getY()), (r * maxLength + (1 - r) * minLength) * Game.TileSize, speed));
        }
    }

    public void moveCars(long time)
    {
        cars.forEach(car -> car.thick(time));
    }

    public void checkOutOfBoundaries(double width, double height)
    {
        cars.removeIf(car -> car.outOfBounds(width, height , true));
    }

    public boolean checkCollision(Entity entity)
    {
        return cars.stream().anyMatch(car -> car.collision(entity));
    }

    public List<Car> getCollisions(Entity entity)
    {
        return cars.stream().filter(car -> car.collision(entity)).collect(Collectors.toList());
    }

    public void draw(GraphicsContext context) {
        cars.forEach(car -> car.draw(context));
    }
}
