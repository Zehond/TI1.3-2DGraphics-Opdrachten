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

public class Mirror extends Application {
    ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Area vierkant = new Area(new Rectangle2D.Double(-50,100,100,100));
        graphics.translate(canvas.getWidth()/2,canvas.getHeight()/2);
        graphics.scale(1,-1);
        graphics.setColor(Color.red);
        graphics.drawLine((int) (-1 * canvas.getWidth()),0, (int) canvas.getWidth(),0);
        graphics.setColor(Color.green);
        graphics.drawLine(0, (int) (-1 * canvas.getHeight()),0, (int) canvas.getHeight());
        graphics.setColor(Color.BLACK);

        double resolution = 1.0f;
        double scale = 10;
        double lastY = 0;
        for(double x = (-1 * canvas.getHeight()); x < canvas.getHeight(); x += resolution) {
            double y = 2.5 * x;
            graphics.draw(new Line2D.Double(x*scale, y*scale, (x-resolution)*scale, lastY*scale));
            lastY = y;
        }
        graphics.draw(vierkant);

        AffineTransform affineTransform =
                new AffineTransform((2/(1+Math.pow(2.5,2)))-1,(2*2.5)/(1+Math.pow(2.5,2))
                        ,(2*2.5)/(1+Math.pow(2.5,2)),2*Math.pow(2.5,2)/(1+Math.pow(2.5,2))-1,0,0);
        vierkant.transform(affineTransform);
        graphics.draw(vierkant);



    }


    public static void main(String[] args)
    {
        launch(Mirror.class);
    }

}
