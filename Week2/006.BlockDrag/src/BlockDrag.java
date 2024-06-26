import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class BlockDrag extends Application {
    ResizableCanvas canvas;
    Stage stage;
    ArrayList<Renderable> renderables = new ArrayList<>();
    Renderable selectedRenderable;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Block Dragging");
        primaryStage.show();

        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));
        canvas.setOnMouseClicked(this::mouseClickedWithControl);

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

   public BlockDrag(){
        renderables.add(new Renderable(new Rectangle2D.Double(-50,-50,100,100), new Point2D.Double(400,400),0.75f));
        renderables.add(new Renderable(new Rectangle2D.Double(-50,-50,100,100), new Point2D.Double(200,300),0.75f));
   }
    private Color[] colors = {Color.BLACK,Color.blue,Color.cyan,Color.red,Color.orange,Color.pink};
    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        int i = 0;
        for (Renderable renderable : renderables) {
            i++;
            graphics.setColor(colors[i]);
            renderable.draw(graphics);
            if (i == 5){
                i = 0;
            }
        }
    }


    public static void main(String[] args) {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e) {
        for (Renderable renderable : renderables) {
            if (renderable.getTransformedShape().contains(e.getX(),e.getY()) && e.getButton() == MouseButton.PRIMARY){
                selectedRenderable = renderable;
            }
        }
    }
    private void mouseClickedWithControl(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY && event.isControlDown()){
            renderables.add(new Renderable(new Rectangle2D.Double(-50,-50,100,100), new Point2D.Double(event.getX(),event.getY()),0.75f));
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        }
    }

    private void mouseReleased(MouseEvent e) {
        selectedRenderable = null;
    }

    private void mouseDragged(MouseEvent e) {

        for (Renderable renderable : renderables) {
            if (renderable.getTransformedShape().contains(e.getX(),e.getY()) && e.getButton() == MouseButton.PRIMARY){
                selectedRenderable.setPosition(new Point2D.Double(e.getX(),e.getY()));
                draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
            }
        }
    }

}
