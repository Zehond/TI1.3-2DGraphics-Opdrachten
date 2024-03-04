import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Spotlight extends Application {
    private ResizableCanvas canvas;
    private ArrayList<spotlightShape> spotlightShapes = new ArrayList<>();

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
        stage.setTitle("Spotlight");
        stage.show();
        draw(g2d);
        canvas.setOnMouseMoved(this::setOnMouseMoved);
    }

    private BufferedImage image;
    public Spotlight(){
        try {
            image = ImageIO.read(new File("Week3/003.FadingImage/resources/Beluga cat meme.jpeg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        spotlightShapes.add(new spotlightShape(new Rectangle2D.Double(20,20,100,100),point2D));
    }
    private Area vierkant;
    private Point2D point2D = new Point2D.Double(100,100);
    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());


        for (spotlightShape spotlightShape : spotlightShapes) {
            spotlightShape.draw(graphics);
            graphics.setClip(spotlightShape.getTransformedShape());
        }
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        graphics.drawImage(image,0,0,null);
        graphics.setClip(null);
    }

    public void setOnMouseMoved(MouseEvent e){
        for (spotlightShape spotlightShape : spotlightShapes) {
            spotlightShape.setPosition(new Point2D.Double(e.getX()-50,e.getY()-50));
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        }
    }

    public void init()
    {

    }
    public void update(double deltaTime)
    {

    }

    public static void main(String[] args)
    {
        launch(Spotlight.class);
    }

}
