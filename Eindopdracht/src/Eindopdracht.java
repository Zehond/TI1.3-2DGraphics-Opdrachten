import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.text.AttributedCharacterIterator;

public class Eindopdracht extends Application {

    private double WIDTH = 600, HEIGHT = 400;
    private double hue = 0;
    private double scale = 1;
    private double x = 50, y = 100;
    private GraphicsContext gc;
//    private FXGraphics2D graphics2D;
    private double offsetX;
    private double offsetY;
    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
//        graphics2D = new FXGraphics2D(canvas.getGraphicsContext2D());

        Text text = new Text("Hello, World!");
        text.setFont(Font.font("Verdana", 50));
        canvas.setOnMousePressed(event -> {
            offsetX = event.getX() - text.getX() ;
            offsetY = event.getY() - text.getY();
            System.out.println("OFx " +offsetX);
            System.out.println("OFy " +offsetY);
        });
        canvas.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                x = event.getX() - offsetX;
                y = event.getY() - offsetY;
                System.out.println("x "+x);
                System.out.println("y " +y);
            } else if (event.isSecondaryButtonDown()) {
                scale += 0.01;
            }
        });


        canvas.setOnScroll(event -> {
            if (event.isControlDown()){
                scale += 0.01;
            }else {
                scale -= 0.01;
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
                draw(gc, text);
            }




        }.start();

        StackPane layout = new StackPane(canvas);
        layout.setPrefSize(WIDTH, HEIGHT);

        stage.setScene(new Scene(layout));
        stage.show();
    }
    private void draw(GraphicsContext gc, Text text) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        float[] fraction = {0.5f,0.8f,1.0f};
        java.awt.Color[] colors = {java.awt.Color.BLACK, java.awt.Color.blue,java.awt.Color.yellow};
        LinearGradientPaint linearGradientPaint = new LinearGradientPaint(new Point2D.Double(2,2), new Point2D.Double(4,4),fraction,colors,MultipleGradientPaint.CycleMethod.REPEAT);

        gc.setFill(javafx.scene.paint.Color.hsb(hue,1,1));

        AffineTransform tx = new AffineTransform();
        tx.translate(x,y);


        text.setTranslateX(tx.getTranslateX());
        text.setTranslateY(tx.getTranslateY());
        gc.save();
        gc.translate(x, y);
        gc.scale(scale, scale);
        gc.setFont(text.getFont());
        gc.fillText(text.getText(), 0, 0);
        gc.restore();
    }
    private void update(double delta) {
        hue += delta * 60;
        hue %= 360;
    }


}


//{
//
//    private double WIDTH = 600, HEIGHT = 400;
//    private double hue = 0;
//    private double scale = 1;
//    private double x = 50, y = 100;
//    private GraphicsContext gc;
//    private FXGraphics2D graphics2D;
//    private double offsetX;
//    private double offsetY;
//    @Override
//    public void start(Stage stage) {
//        Canvas canvas = new Canvas(WIDTH, HEIGHT);
//        gc = canvas.getGraphicsContext2D();
//        graphics2D = new FXGraphics2D(canvas.getGraphicsContext2D());
//
//        Text text = new Text("Hello, World!");
//        text.setFont(Font.font("Verdana", 50));
//        canvas.setOnMousePressed(event -> {
//            offsetX = event.getX() - text.getX() ;
//            offsetY = event.getY() - text.getY();
//        });
//        canvas.setOnMouseDragged(event -> {
//            if (event.isPrimaryButtonDown()) {
//                x = event.getX() - offsetX;
//                y = event.getY() - offsetY;
//            } else if (event.isSecondaryButtonDown()) {
//                scale += 0.01;
//            }
//        });
//
//
//        canvas.setOnScroll(event -> {
//            if (event.isControlDown()){
//                scale += 0.01;
//            }else {
//                scale -= 0.01;
//            }
//        });
//
//        new AnimationTimer() {
//            long last = -1;
//
//            @Override
//            public void handle(long now) {
//                if (last == -1) {
//                    last = now;
//                }
//                update((now - last) / 1000000000.0);
//                last = now;
//                draw(gc, text);
//            }
//
//
//
//
//        }.start();
//
//        StackPane layout = new StackPane(canvas);
//        layout.setPrefSize(WIDTH, HEIGHT);
//
//        stage.setScene(new Scene(layout));
//        stage.show();
//    }
//    private void draw(GraphicsContext gc, Text text) {
//        gc.clearRect(0, 0, WIDTH, HEIGHT);
//        float[] fraction = {0.5f,0.8f,1.0f};
//        java.awt.Color[] colors = {java.awt.Color.BLACK, java.awt.Color.blue,java.awt.Color.yellow};
//        LinearGradientPaint linearGradientPaint = new LinearGradientPaint(new Point2D.Double(2,2), new Point2D.Double(4,4),fraction,colors,MultipleGradientPaint.CycleMethod.REPEAT);
//
//        gc.setFill(Color.hsb(hue, 1, 1));
//
//        AffineTransform tx = new AffineTransform();
//        tx.translate(x,y);
//
//
//
//        gc.save();
//        gc.translate(x, y);
//        gc.scale(scale, scale);
//        gc.setFont(text.getFont());
//        gc.fillText(text.getText(), 0, 0);
//        gc.restore();
//    }
//    private void update(double delta) {
//        hue += delta * 60;
//        hue %= 360;
//    }
//
//
//}




//    private static final double WIDTH = 1500, HEIGHT = 1000;
//    private TextObject text;
//    private int hue;
//    private double scale = 1;
//
//    @Override
//    public void start(Stage stage) {
//        Canvas canvas = new Canvas(WIDTH,HEIGHT);
////        GraphicsContext gc = canvas.getGraphicsContext2D();
//        FXGraphics2D gc = new FXGraphics2D(canvas.getGraphicsContext2D());
//
//        text = new TextObject(new Text("Hello world"),new Point2D.Double(120,120),hue,1,new Font(10));
//        canvas.setOnMouseDragged(this::draggingMouse);
//        new AnimationTimer() {
//
//            long last = -1;
//
//            @Override
//            public void handle(long now) {
//                if (last == -1) {
//                    last = now;
//                }
//                update((now - last) / 1000000000.0);
//                last = now;
//                draw(gc,text);
//            }
//
//        }.start();
//
//
//        StackPane layout = new StackPane(canvas);
//        layout.setPrefSize(WIDTH, HEIGHT);
//
//        stage.setScene(new Scene(layout));
//        stage.show();
//    }
//    private void update(double delta) {
//        hue += (int) (delta * 60);
//        hue %= 360;
//    }
//    private void draw(FXGraphics2D gc, TextObject text) {
//        gc.clearRect(0, 0, (int) WIDTH, (int) HEIGHT); // clear canvas
//
//        // create gradient that changes over time
////        gc.setFill(Color.hsb(hue, 1, 1));
//        gc.setPaint(new GradientPaint(0, 0, java.awt.Color.RED, (float) WIDTH, (float) HEIGHT, java.awt.Color.BLUE));
////        gc.drawString(text.getText(),2,2);
//
//        // draw text
////        gc.setFont(text.getFont()));
//        gc.drawString(text.getText(), (float) text.getPosition().getX(), (float) text.getPosition().getY());
////        text.draw(gc);
//    }
//
////    public void draggingMouse(MouseEvent event){
////        if (event.getButton() == MouseButton.PRIMARY){
////            text.setPosition(new Point2D.Double(event.getX(),event.getY()));
////        } else if (event.getButton() == MouseButton.SECONDARY) {
////            scale+=0.01;
////            text.setScale(scale);
////        }
////        if (event.getButton() == null){
////            scale = 1;
////        }
////    }
//public void draggingMouse(MouseEvent event){
//    if (event.getButton() == MouseButton.PRIMARY){
//        double dx = event.getX() - text.getPosition().getX();
//        double dy = event.getY() - text.getPosition().getY();
//        text.getPosition().setLocation(text.getPosition().getX() + dx, text.getPosition().getY() + dy);
//    } else if (event.getButton() == MouseButton.SECONDARY) {
//        double dy = event.getY() - text.getPosition().getY();
//        scale += dy * 0.01;
//        text.setScale(scale);
//    }
//    if (event.getButton() == null){
//        scale = 1;
//    }
//}
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
//public class Eindopdracht extends Application {
//
//
//    private ResizableCanvas canvas;
//    private int WIDTH = 800;
//    private int HEIGHT = 600;
//    private double xLower;
//    private double yLower;
//    private FXGraphics2D g2d;
//
//
//    @Override
//    public void start(Stage primaryStage) {
//
//        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
//        new AnimationTimer() {
//            long last = -1;
//
//            @Override
//            public void handle(long now) {
//                if (last == -1) {
//                    last = now;
//                }
//                update((now - last) / 1000000000.0);
//                last = now;
//                draw(g2d);
//            }
//        }.start();
//
//
//
//
//    }
//
//    public void update(double deltaTime) {
//
//    }
//
//    public void draw(FXGraphics2D graphics2D) {
//
//    }





//    WritableImage image = new WritableImage(WIDTH, HEIGHT);
//    PixelWriter pixelWriter = image.getPixelWriter();
//
//        for (int x = 0; x < WIDTH; x++) {
//        for (int y = 0; y < HEIGHT; y++) {
//            double zx = 0;
//            double zy = 0;
//            double cX = (x - 400) / 200.0;
//            double cY = (y - 300) / 200.0;
//            int iter = 0;
//            double tmp;
//
//            while ((zx * zx + zy * zy < 4) && (iter < 100)) {
//                tmp = zx * zx - zy * zy + cX;
//                zy = 2.0 * zx * zy + cY;
//                zx = tmp;
//                iter++;
//            }
//
//            if (iter < 100) {
//                pixelWriter.setColor(x, y, Color.BLACK);
//            } else {
//                pixelWriter.setColor(x, y, Color.WHITE);
//            }
//        }
//    }
//    ImageView imageView = new ImageView(image);
//    Group root = new Group(imageView);
//    Scene scene = new Scene(root, WIDTH, HEIGHT);
//    Scale scale = new Scale(1, 1, (double) WIDTH / 2, (double) HEIGHT / 2);
//        root.getTransforms().add(scale);
//
//        scene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
//        scale.setPivotX(event.getX());
//        scale.setPivotY(event.getY());
//    });
//
//        new AnimationTimer() {
//        private long lastUpdate = 0;
//
//        @Override
//        public void handle(long now) {
//            if (now - lastUpdate >= 33000000) {
//                double scaleFactor = 1.01;
//                scale.setX(scale.getX() * scaleFactor);
//                scale.setY(scale.getY() * scaleFactor);
//                lastUpdate = now;
//            }
//        }
//    }.start();
//
//
//        primaryStage.setScene(scene);
//        primaryStage.show();
//}