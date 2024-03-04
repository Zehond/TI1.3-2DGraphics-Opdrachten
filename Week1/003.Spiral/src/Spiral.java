import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spiral extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Spiral");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.translate(1920/2,1080/2);
        graphics.scale(1,-1);

        double resolution = 0.1f;
        double scale = 50.0;
        double lastY = 0;
        double lastX = 0;

        for (double i = 0; i < 100; i+=resolution) {
            float y = (float) (Math.sin(i) * i);
            float x = (float) (Math.cos(i) * i);
            graphics.draw(new Line2D.Double(x * scale, y * scale, lastX * scale, lastY * scale));
            lastY = y;
            lastX = x;

        }

    }
    
    
    
    public static void main(String[] args) {
        launch(Spiral.class);
    }

}
