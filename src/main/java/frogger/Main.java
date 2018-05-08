package frogger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main application.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Frogger");

        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        double w = 800;
        double h = 600;
        Canvas canvas = new Canvas(w, h);
        StackPane canvasContainer = new StackPane(canvas);
        canvasContainer.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        root.getChildren().add(canvasContainer);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        final Game game = new Game(gc, w, h);

        scene.setOnKeyPressed(game::keyPressed);

        scene.setOnKeyReleased(game::keyReleased);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                game.thick(currentNanoTime);
            }
        }.start();

        primaryStage.show();
    }

    /**
     * Main.
     * @param args args.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
