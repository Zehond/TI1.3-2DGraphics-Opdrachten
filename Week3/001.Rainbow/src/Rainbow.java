import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Rainbow extends Application {
    private ResizableCanvas canvas;
    Font font;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        font = new Font("Ariel",Font.PLAIN,30);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        Font font = new Font("Arial", Font.PLAIN, 50);
        AffineTransform tx = new AffineTransform();

        Shape r = font.createGlyphVector(graphics.getFontRenderContext(),"R").getOutline();
        Shape e = font.createGlyphVector(graphics.getFontRenderContext(),"e").getOutline();
        Shape g = font.createGlyphVector(graphics.getFontRenderContext(),"g").getOutline();
        Shape n = font.createGlyphVector(graphics.getFontRenderContext(),"n").getOutline();
        Shape b = font.createGlyphVector(graphics.getFontRenderContext(),"b").getOutline();
        Shape o = font.createGlyphVector(graphics.getFontRenderContext(),"o").getOutline();

        double rotate = 0.3;
        tx.translate(200,200);
        tx.rotate(30);
        Shape newR = tx.createTransformedShape(r);
        graphics.setColor(Color.RED);
        graphics.fill(newR);
        tx.translate(33,0);
        tx.rotate(rotate);
        Shape newE = tx.createTransformedShape(e);
        graphics.setColor(Color.YELLOW);
        graphics.fill(newE);
        tx.translate(33,0);
        tx.rotate(rotate);
        Shape newG = tx.createTransformedShape(g);
        graphics.setColor(Color.green);
        graphics.fill(newG);
        tx.translate(35,0);
        tx.rotate(rotate);
        Shape newE2 = tx.createTransformedShape(e);
        graphics.setColor(Color.cyan);
        graphics.fill(newE2);
        tx.translate(37,0);
        tx.rotate(rotate);
        Shape newN = tx.createTransformedShape(n);
        graphics.setColor(Color.BLUE);
        graphics.fill(newN);
        tx.translate(40,7);
        tx.rotate(rotate);
        Shape newB = tx.createTransformedShape(b);
        graphics.setColor(Color.BLUE);
        graphics.fill(newB);
        tx.translate(37,10);
        tx.rotate(rotate);
        Shape newO = tx.createTransformedShape(o);
        graphics.setColor(Color.MAGENTA);
        graphics.fill(newO);
        tx.translate(35,17);
        tx.rotate(rotate);
        Shape newO2 = tx.createTransformedShape(o);
        graphics.setColor(Color.magenta);
        graphics.fill(newO2);
        tx.translate(38,20);
        tx.rotate(rotate);
        Shape newG2 = tx.createTransformedShape(g);
        graphics.setColor(Color.ORANGE);
        graphics.fill(newG2);
    }



    public static void main(String[] args)
    {
        launch(Rainbow.class);
    }


}
