package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Frogger");

        Group root = new Group();
        Scene scene = new Scene( root );
        primaryStage.setScene( scene );

        double w = 800;
        double h = 600;
        Canvas canvas = new Canvas( w, h );
        StackPane canvasContainer = new StackPane(canvas);
        canvasContainer.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        root.getChildren().add( canvasContainer );
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        final Game game = new Game(gc, w, h);

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        game.keyPressed(e);
                    }
                });

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        game.keyReleased(e);
                    }
                });

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                game.thick(currentNanoTime);
            }
        }.start();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
