package frogger.entities;


import frogger.Game;
import frogger.Utils.Direction;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The player entity.
 */
public class Player extends Entity {
    private double speed = Game.TILE_SIZE;

    /**
     * Status of the player.
     */
    public enum Status {
        Normal,
        Dead,
        Drowning,
        Floating,
        Won
    }
    private Status status;

    /**
     * Create a new player entity.
     *
     * @param position start position.
     */
    public Player(Point2D position) {
        super(position, Game.TILE_SIZE * 0.9, Game.TILE_SIZE * 0.9);
        status = Status.Normal;
    }

    /**
     * Try to set the status of the player. However it also considers the current state,
     *
     * @param status status of the player.
     */
    public void setStatus(Status status) {
        if (this.status == Status.Dead || this.status == Status.Won) {
            return;
        }
        switch (status) {
            case Won:
                this.status = status;
                break;
            case Normal:
                break;
            case Dead:
                this.status = status;
                break;
            case Drowning:
                if (this.status != Status.Floating) {
                    this.status = status;
                }
                break;
            case Floating:
                this.status = status;
                break;
            default:
                break;
        }
    }

    /**
     * Reset the status to normal.
     */
    public void resetStatus() {
        this.status = Status.Normal;
    }

    /**
     * @return true if the player is alive.
     */
    public boolean isAlive() {
        return status != Status.Dead && status != Status.Drowning;
    }

    /**
     * @return true if the player has won.
     */
    public boolean hasWon() {
        return this.status == Status.Won;
    }

    @Override
    public void collision(Entity other) {
        if (other instanceof Player) {
            return;
        }
        other.collision(this);
    }

    @Override
    public void draw(GraphicsContext context, double x, double y) {
        context.setFill(Color.GREEN);
        context.fillOval(x, y, getWidth(), getHeight());
    }

    @Override
    public void checkBoundary(double width, double height) {
        double left = getCenterPosition().getX() - getWidth() / 2;
        double right = getCenterPosition().getX() + getWidth() / 2;
        double top = getCenterPosition().getY() + getHeight() / 2;
        double bottom = getCenterPosition().getY() - getHeight() / 2;

        if (left < 0) {
            move(-left, 0);
        }
        if (right > width) {
            move(width - right, 0);
        }
        if (top > height) {
            move(Direction.UP);
        }
        if (bottom < 0) {
            move(Direction.DOWN);
        }
    }


    /**
     * Move the player one step in the given direction.
     *
     * @param direction the direction the player should move to.
     */
    public void move(Direction direction) {
        switch (direction) {
            case RIGHT:
                move(speed, 0);
                break;
            case LEFT:
                move(-speed, 0);
                break;
            case UP:
                move(0, -speed);
                break;
            case DOWN:
                move(0, speed);
                break;
            default:
                break;
        }
    }
}
