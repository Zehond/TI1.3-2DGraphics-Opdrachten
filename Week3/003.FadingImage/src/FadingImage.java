import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
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

public class FadingImage extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
		if(last == -1)
                    last = now;
		update((now - last) / 1000000000.0);
		last = now;
		draw(g2d);
            }
        }.start();
        
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }
    private BufferedImage image;
    private BufferedImage image2;
    public FadingImage(){
        try {
             image = ImageIO.read(new File("Week3/003.FadingImage/resources/Helldivers 2 image 1.jpg"));
             image2 = ImageIO.read(new File("Week3/003.FadingImage/resources/Beluga cat meme.jpeg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        graphics.drawImage(image,0,0,null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        graphics.drawImage(image2,0,0,null);
        //De plaatjes zijn niet even groot dus als je je maximaliseert dan is er nog een stuk over van de oude foto.
        //Maar het werkt.
    }
    private float alpha = 0.0f;

    public void update(double deltaTime) {
        alpha += deltaTime / 2.5;
        if (alpha >= 1.0f){
            alpha = 1.0f;
        }
    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
