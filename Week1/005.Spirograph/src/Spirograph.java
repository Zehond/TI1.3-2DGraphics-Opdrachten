import java.awt.*;
import java.awt.geom.*;

import com.sun.applet2.Applet2Context;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spirograph extends Application {
    
    private TextField v1;
    private TextField v2;
    private TextField v3;
    private TextField v4;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
       
        VBox mainBox = new VBox();
        HBox topBar = new HBox();
        mainBox.getChildren().add(topBar);
        mainBox.getChildren().add(new Group(canvas));
        
        topBar.getChildren().add(v1 = new TextField("300"));
        topBar.getChildren().add(v2 = new TextField("1"));
        topBar.getChildren().add(v3 = new TextField("300"));
        topBar.getChildren().add(v4 = new TextField("10"));
                
        v1.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v2.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v3.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v4.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Spirograph");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        //you can use Double.parseDouble(v1.getText()) to get a double value from the first textfield
        //feel free to add more textfields or other controls if needed, but beware that swing components might clash in naming
        graphics.translate(1920/2, 1080/2);
        graphics.scale(1,-1);
        double scale = 1;

        double v1x = Double.parseDouble(v1.getText());
        double v2y = Double.parseDouble(v2.getText());
        double v3x = Double.parseDouble(v3.getText());
        double v4y = Double.parseDouble(v4.getText());

        double lastY = 0;


        double resolution = 0.1;
        for(double i = 0; i < 500; i += resolution) {


            double y = (v1x * Math.sin(Math.toRadians(v2y * i)) + v3x * Math.sin(Math.toRadians(v4y * i)));
            double x = (v1x * Math.cos(Math.toRadians(v2y * i)) + v3x * Math.cos(Math.toRadians(v4y * i)));

            graphics.draw(new Line2D.Double(x * scale, y * scale, (x-resolution) * scale, lastY * scale));
            lastY = y;
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Spirograph.class);
    }

}
