import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Rainbow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Rainbow");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.translate(1920/ 2, 1000);
        graphics.scale(1,-1);


        float scale = 8;
        float radius = 500.0f;
        for (int i = 0; i < radius; i++) {
            graphics.setColor(Color.getHSBColor(i/500.0f, 1, 1));
            graphics.setStroke(new BasicStroke(10));
            double cos = Math.cos(i / radius * Math.PI);
            double sin = Math.sin(i / radius * Math.PI);

            double x1 = (70 * cos);
            double y1 = (70 * sin);
            double x2 = (90 * cos);
            double y2 = (90 * sin);

            graphics.drawLine((int)(x1*scale), (int)(y1*scale), (int)(x2*scale), (int)(y2*scale));
            graphics.setColor(Color.getHSBColor(i/radius, 1, 1));
        }




    }
    
    
    
    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
