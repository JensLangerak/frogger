package sample;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Lane {

    private Point2D spawnPoint;
    private double speed;
    private LinkedList<Car> cars;

    private int maxLength = 3;
    private int minLength = 1;

    public Lane(Point2D spawn, double speed)
    {
        this.spawnPoint = spawn;
        this.speed = speed;
        cars = new LinkedList<>();
    }

    public void Spawn(long time) {
        if (!cars.isEmpty()) {
            double xRef = cars.getLast().getCenterPosition().getX();
            double outerPoint = cars.getLast().getWidth() / 2 + 0.25 * Game.TileSize;

            if (speed > 0 && xRef - outerPoint <= spawnPoint.getX())
                return;
            if (speed < 0 && xRef + outerPoint >= spawnPoint.getX())
                return;
        }

        if (Math.random() < 0.013) {
            double dx = speed > 0 ? -maxLength : maxLength;
            dx *= Game.TileSize;
            double r = Math.random();

            cars.add(new Car(new Point2D(spawnPoint.getX() + dx, spawnPoint.getY()), (r * maxLength + (1 - r) * minLength) * Game.TileSize, speed));
        }
    }

    public void MoveCars(long time)
    {
        cars.forEach(car -> car.Thick(time));
    }

    public void CheckOutOfBoundaries(double width, double height)
    {
        cars.removeIf(car -> car.OutOfBounds(width, height , true));
    }

    public boolean CheckCollision(Entity entity)
    {
        return cars.stream().anyMatch(car -> car.Collision(entity));
    }

    public void Draw(GraphicsContext context) {
        cars.forEach(car -> car.Draw(context));
    }
}
