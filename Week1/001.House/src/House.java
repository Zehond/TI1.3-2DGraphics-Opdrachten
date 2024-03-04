import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import java.awt.geom.Line2D;


public class House extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1024, 768);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("House");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        // test
        graphics.draw(new Line2D.Double(200,50,20,300));
        graphics.draw(new Line2D.Double(200,50,400,300));

        graphics.draw(new Line2D.Double(20,300,20,600));
        graphics.draw(new Line2D.Double(400,300,400,600));
        graphics.draw(new Line2D.Double(20,600,400,600));

        graphics.draw(new Line2D.Double(40,600,40,500));
        graphics.draw(new Line2D.Double(40,500,100,500));
        graphics.draw(new Line2D.Double(100,500,100,600));

        graphics.draw(new Line2D.Double(150,400,150,550));
        graphics.draw(new Line2D.Double(375,400,375,550));
        graphics.draw(new Line2D.Double(150,550,375,550));
        graphics.draw(new Line2D.Double(150,400,375,400));
    }



    public static void main(String[] args) {
        launch(House.class);
    }

}
