package sample;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends Entity {
    public Player(Point2D position)
    {
        super(position, Game.TileSize * 0.9,  Game.TileSize * 0.9);
    }

    double speed =  Game.TileSize;

    void Draw(GraphicsContext context) {
        double x = getCenterPosition().getX() - getWidth() / 2;
        double y = getCenterPosition().getY() - getHeight() / 2;

        context.setFill(Color.GREEN);
        context.fillOval(x, y, getWidth(), getHeight());
    }

    public void CheckBoundary(double width, double height) {
        double left = getCenterPosition().getX() - getWidth() / 2;
        double right = getCenterPosition().getX() + getWidth() / 2;
        double top = getCenterPosition().getY() + getHeight() / 2;
        double bottom = getCenterPosition().getY() - getHeight() / 2;

        if (left < 0)
            Move(Direction.RIGHT);
        if (right > width)
            Move(Direction.LEFT);
        if (top > height)
            Move(Direction.UP);
        if (bottom < 0)
            Move(Direction.DOWN);
    }

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    void Move(Direction direction)
    {
        switch (direction){
            case RIGHT:
                Move(speed, 0);
                break;
            case LEFT:
                Move(-speed, 0);
                break;
            case UP:
                Move(0, -speed);
                break;
            case DOWN:
                Move(0, speed);
                break;
        }

    }
}
