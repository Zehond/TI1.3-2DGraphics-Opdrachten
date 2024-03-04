import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class MovingCharacter extends Application {
    private ResizableCanvas canvas;
    private int imageIndex = 0;
    private double x;
    private int y;
    private boolean jump = false;

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
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.drawImage(tiles[imageIndex],(int)x,y,null);
    }

    private BufferedImage[] tiles;
    public MovingCharacter() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/images/sprite.png"));
            tiles = new BufferedImage[65];
            for(int i = 0; i < 65; i++) {
                tiles[i] = image.getSubimage(64 * (i % 8), 64 * (i / 8), 64, 64);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private double timeLastUpdate = 0;
    private double frameUpdate = 0.1;
    public void update(double deltaTime) {
        //met behulp van AI, want ik zat wel goed, alleen kwam er niet helemaal uit hoe ik ervoor zorgde dat die niet te snel ging updaten.
        canvas.setOnMouseClicked(event -> {
            jump = true;
        });
        y = 100;
        timeLastUpdate += deltaTime;
        if (timeLastUpdate >= frameUpdate && !jump){
            timeLastUpdate -= frameUpdate;
            if (x < canvas.getWidth() && !jump){
                imageIndex = 31 + (imageIndex) % 10;
                if (imageIndex == 40) {
                    imageIndex = 32;
                }
            } else if (x > canvas.getWidth()) {
                x = 0;
            }
        }else if (timeLastUpdate >= frameUpdate){
            timeLastUpdate -= frameUpdate;
            imageIndex = 40 + (imageIndex + 1) % 10;
            if (imageIndex == 46){
                jump = false;
            }
        }
        x += 30 * deltaTime;
    }

    public static void main(String[] args) {
        launch(MovingCharacter.class);
    }

}
