import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class YingYang extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Ying Yang");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        GeneralPath generalPath = new GeneralPath();
        GeneralPath generalPath2 = new GeneralPath();
        GeneralPath generalPathC1 = new GeneralPath();
        GeneralPath generalPathC2 = new GeneralPath();



        //curve in cirkel
        generalPath.moveTo(250,350);
        generalPath.curveTo(220,360,220,400,250,400);
        //cirkel
        generalPathC1.moveTo(250,300);
        generalPathC1.curveTo(175,310,190,395,250,400);
        generalPathC2.moveTo(250,300);
        generalPathC2.curveTo(325,310,310,395,250,400);
        //curve in cirkel
        generalPath2.moveTo(250,300);
        generalPath2.curveTo(270,300,280,340,250,350);

        graphics.draw(generalPathC1);
        graphics.fill(generalPathC2);
        graphics.fill(generalPath);
        graphics.setColor(Color.white);
        graphics.fill(generalPath2);
        graphics.setColor(Color.BLACK);
        Area kleineWit = new Area(new Ellipse2D.Double(240,315,15,15));
        graphics.setColor(Color.BLACK);
        graphics.fill(kleineWit);
        graphics.draw(kleineWit);
        graphics.setColor(Color.white);
        Area kleineZwart = new Area(new Ellipse2D.Double(240,370,15,15));
        graphics.fill(kleineZwart);
        graphics.draw(kleineZwart);





    }


    public static void main(String[] args)
    {
        launch(YingYang.class);
    }

}
