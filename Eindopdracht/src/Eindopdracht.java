import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import org.jfree.fx.ResizableCanvas;

public class Eindopdracht extends Application {
    private double hue = 0;
    private double scaleX = 1;
    private double scaleY = 1;
    private double x = 200, y = 200;
    private double offsetX;
    private double offsetY;
    private ResizableCanvas canvas;
    private int fontSize = 40;
    private Resizable resizable;
    private double lastY = -1;
    private  Shape helloWorldPaint;
    private   Rectangle2D rectangle2DBounds;
    private boolean inRectangle = false;
    private Point2D centerpoint;
    private double zoomFactor;
    private Point2D originalMouseCoords;

    //TODO zorg ervoor dat als je de text dragged en je hebt gescaled dat je dan ook de muis nog op de tekst blijft
    // want vanwege de scale neemt die m wel mee maar je muis is niet in de buurt van de text
    // en je kan voor als je het leuk vindt een particle systeem toevoegen, bijvoorbeeld dat je klikt en dan gaat er aan
    // de zijkant van het scherm of van de tekst kleine particles schieten. idk doe nog wat leuks
    @Override
    public void start(Stage stage) {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        canvas.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                rectangle2DBounds = helloWorldPaint.getBounds2D();
                if (inRectangle){
                    double deltaX = (event.getX() - originalMouseCoords.getX()) / scaleX;
                    double deltaY = (event.getY() - originalMouseCoords.getY()) / scaleY;
                    x += deltaX;
                    y += deltaY;
                    originalMouseCoords = new Point2D.Double(event.getX(), event.getY());
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                if (lastY > 0){
                    double deltaY = event.getSceneY() - lastY;
                    zoomFactor = 1 - deltaY * 0.01;

                    scaleX = Math.max(0.1, Math.min(3.5, scaleX * zoomFactor));
                    scaleY = Math.max(0.1, Math.min(3.5, scaleY * zoomFactor));

                }
                lastY = event.getSceneY();
            }
        });
        canvas.setOnMousePressed(event -> {
            rectangle2DBounds = helloWorldPaint.getBounds2D();
            if (rectangle2DBounds.contains(event.getX(),event.getY())){
                inRectangle = true;
                originalMouseCoords = new Point2D.Double(event.getX(), event.getY());
            }
            if (event.getButton() == MouseButton.PRIMARY && event.isControlDown()){
                x = 200;
                y = 200;
                scaleX = 1;
                scaleY = 1;
            }
        });
        canvas.setOnMouseReleased(event -> {
            inRectangle = false;
            if (event.getButton() == MouseButton.SECONDARY) {
                lastY = -1;
            }
        });

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }

        }.start();

        stage.setScene(new Scene(mainPane,1900,980));
        stage.show();
        draw(g2d);
    }

    private void draw(FXGraphics2D graphics2D) {
        AffineTransform tx = new AffineTransform();
        graphics2D.setTransform(tx);
        graphics2D.setBackground(Color.white);
        graphics2D.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        tx.scale(scaleX,scaleY);
        float f = (float) (hue / 360.0);

        GradientPaint gradientPaint = new GradientPaint(0,0,Color.getHSBColor(f,1,1),(int) canvas.getWidth(),(int) canvas.getHeight() ,Color.getHSBColor(f+1,1,1));
        graphics2D.setPaint(gradientPaint);

        Font font = new java.awt.Font("Ariel", java.awt.Font.BOLD,fontSize);
        Shape helloWorld = font.createGlyphVector(graphics2D.getFontRenderContext(),"Hello World").getOutline();

        tx.translate(x,y);
        helloWorldPaint = tx.createTransformedShape(helloWorld);

        graphics2D.fill(helloWorldPaint);
        graphics2D.setColor(Color.BLACK);

    }
    private void update(double delta) {
        hue += delta * 60;
        hue %= 360;
    }
}