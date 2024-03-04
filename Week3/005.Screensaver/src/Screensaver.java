import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Screensaver extends Application {
    private ResizableCanvas canvas;
    private Point2D position1 = new Point2D.Double(100,200);
    private Point2D position2 = new Point2D.Double(160,240);
    private Point2D position3 = new Point2D.Double(200,400);
    private Point2D position4 = new Point2D.Double(99,360);
    @Override
    public void start(Stage stage) throws Exception
    {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now)
            {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Screensaver");
        stage.show();
        draw(g2d);
    }
    ArrayList<PointShape> pointShapes = new ArrayList<>();
    ArrayList<ArrayList<PointShape>> lines = new ArrayList<>();
    public Screensaver(){
        pointShapes.add(new PointShape (position1));
        pointShapes.add(new PointShape (position2));
        pointShapes.add(new PointShape (position3));
        pointShapes.add(new PointShape (position4));
        lines.add(pointShapes);

    }
    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.setBackground(Color.black);
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        graphics.clearRect(0,0,width,height);

        for (ArrayList<PointShape> positions : lines) {
            for (int i = 0; i < positions.size() - 1; i++) {
                Line2D line424 = new Line2D.Double(positions.get(i).getPosition(), positions.get(i + 1).getPosition());
                graphics.draw(line424);
            }
        }

        for (ArrayList<PointShape> line : lines){
            int x1 = (int) line.get(0).getPosition().getX();
            int y1 = (int) line.get(0).getPosition().getY();
            int x2 = (int) line.get(1).getPosition().getX();
            int y2 = (int) line.get(1).getPosition().getY();
            int x3 = (int) line.get(2).getPosition().getX();
            int y3 = (int) line.get(2).getPosition().getY();
            int x4 = (int) line.get(3).getPosition().getX();
            int y4 = (int) line.get(3).getPosition().getY();

            graphics.setColor(Color.BLUE);
            //1 -2
            graphics.drawLine(x1,y1,x2,y2);
            //2-3
            graphics.drawLine(x2,y2,x3,y3);
            //3-4
            graphics.drawLine(x3,y3,x4,y4);
            //4-1
            graphics.drawLine(x4,y4,x1,y1);


        }

    }

    public void init(){

    }
    ArrayList<ArrayList<Point2D>> lineHistory = new ArrayList<>();
    public void update(double deltaTime) {
        ArrayList<PointShape> newPointLines = new ArrayList<>();
        for (int i = 0; i < pointShapes.size(); i++) {
            PointShape pointShape = pointShapes.get(i);
            pointShape.updatePoint(canvas, i);
            Point2D line =  pointShapes.get(i).getPosition();
            newPointLines.add(new PointShape(line));
            lines.add(newPointLines);
            if (lines.size() > 100) {
                lines.remove(0);
            }
        }


    }


    public static void main(String[] args){
        launch(Screensaver.class);
    }

}
