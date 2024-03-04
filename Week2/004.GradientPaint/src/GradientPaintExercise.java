import java.awt.*;
import java.awt.geom.*;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.CycleMethod;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;
    private Point2D focus = new Point2D.Float(100, 50);


    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Colors");
        primaryStage.show();

    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Point2D center = new Point2D.Float((float) (canvas.getWidth()/2), (float) (canvas.getHeight()/2));
        float radius = (float) Math.max(canvas.getWidth(),canvas.getHeight());//idioot
        float[] fractions = {0.1f,0.5f};
        Color[] color = {Color.yellow,Color.blue};
        RadialGradientPaint radialGradientPaint = new RadialGradientPaint(center, radius, focus, fractions, color, MultipleGradientPaint.CycleMethod.NO_CYCLE);
        graphics.setPaint(radialGradientPaint);
        graphics.fill(new Rectangle2D.Double(0,0,canvas.getWidth(),canvas.getHeight()));

        canvas.setOnMouseDragged(event -> {
            focus.setLocation(event.getX(), event.getY());
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        });
    }


    public static void main(String[] args) {
        launch(GradientPaintExercise.class);
    }

}
