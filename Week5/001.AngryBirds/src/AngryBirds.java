
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dyn4j.collision.Fixture;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.Force;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class AngryBirds extends Application {

    private ResizableCanvas canvas;
    private World world;
    private MousePicker mousePicker;
    private Camera camera;
    private boolean debugSelected = false;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Body bigBoiBird;
    private Body slingshotBody;
    private Body angryBlok;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();

        // Add debug button
        javafx.scene.control.CheckBox showDebug = new CheckBox("Show debug");
        showDebug.setOnAction(e -> {
            debugSelected = showDebug.isSelected();
        });
        mainPane.setTop(showDebug);

        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);
        mousePicker = new MousePicker(canvas);

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

        stage.setScene(new Scene(mainPane, 1920, 1080));
        stage.setTitle("Angry Birds");
        stage.show();
        draw(g2d);
    }

    public void bigBoiBirdBody(){
        //bigRedbird body
        bigBoiBird = new Body();
        gameObjects.add(new GameObject("angryBirdBigBoi.png",bigBoiBird,new Vector2(0,0),0.2));
        BodyFixture bigBoiFixture = new BodyFixture(Geometry.createCircle(0.3));
        bigBoiFixture.setRestitution(0.5);
        bigBoiBird.addFixture(bigBoiFixture);
        bigBoiBird.getTransform().setTranslation(new Vector2(-7,-2.5));
        bigBoiBird.getFixture(0).setFriction(3);
        bigBoiBird.getFixture(0).setDensity(20);
        bigBoiBird.setMass(MassType.NORMAL);
        world.addBody(bigBoiBird);
    }

//    public void isMouseCLicked(MouseEvent mouseButton) {
//        Point2D mouse = new Point2D.Double(mouseButton.getX(), mouseButton.getY());
//        if (mouseButton.getButton() == MouseButton.PRIMARY){
//            bigBoiBird.setMass(MassType.NORMAL);
//            bigBoiBird.applyForce(new Vector2(-mouseButton.getX(),-mouseButton.getY()));
//        }
//    }
    public void init(){
        this.world = new World();
        world.setGravity(new Vector2(0,-9.81));
        Body background = new Body();
        gameObjects.add(new GameObject("Angry birds background by gsgill37-d3kogmx_jpg.jpg",background,new Vector2(0,0),2.4));

        setBorders();
        slingshotBody();
        bigBoiBirdBody();
        angryBlok(new Vector2(4,-3));
        angryBlok(new Vector2(5,-3));
        angryBlok(new Vector2(4,-4));
        angryBlok(new Vector2(5,-4));
        angryBlok(new Vector2(4.5,-1));


    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform originalTransform = graphics.getTransform();
        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        graphics.scale(1, -1);

        for (GameObject go : gameObjects) {
            go.draw(graphics);
        }

        if (debugSelected) {
            graphics.setColor(Color.blue);
            DebugDraw.draw(graphics, world, 100);
        }

        graphics.setTransform(originalTransform);
    }

    public void update(double deltaTime) {
        mousePicker.update(world, camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()), 100);
//        canvas.setOnMouseDragged(this::isMouseCLicked);
        world.update(deltaTime);
    }

    public void setBorders(){
        //ground body
        Body ground = new Body();
        ground.addFixture(Geometry.createRectangle(50,1.5));
        ground.getTransform().setTranslation(new Vector2(0,-4.3));
        ground.setMass(MassType.INFINITE);
        ground.getFixture(0).setRestitution(0.5);
        world.addBody(ground);

        //rightBorder body
        Body rightBorder = new Body();
        rightBorder.addFixture(Geometry.createRectangle(2,15));
        rightBorder.getTransform().setTranslation(new Vector2(9.7,0));
        rightBorder.setMass(MassType.INFINITE);
        rightBorder.getFixture(0).setRestitution(0.5);
        world.addBody(rightBorder);

        //leftBorder body
        Body leftBorder = new Body();
        leftBorder.addFixture(Geometry.createRectangle(2,15));
        leftBorder.getTransform().setTranslation(new Vector2(-9.7,0));
        leftBorder.setMass(MassType.INFINITE);
        leftBorder.getFixture(0).setRestitution(0.5);
        world.addBody(leftBorder);

        //topBorder body
        Body topBorder = new Body();
        topBorder.addFixture(Geometry.createRectangle(22,2));
        topBorder.getTransform().setTranslation(new Vector2(0,5.7));
        topBorder.setMass(MassType.INFINITE);
        topBorder.getFixture(0).setRestitution(0.5);
        world.addBody(topBorder);

    }

    public void slingshotBody(){
        slingshotBody = new Body();
        gameObjects.add(new GameObject("slingshot.png",slingshotBody,new Vector2(0,0),0.3));
//        BodyFixture slingshotFixture = new BodyFixture(Geometry.createRectangle(0.5,0.5));
//        slingshotFixture.setRestitution(0);
//        slingshotBody.addFixture(slingshotFixture);
        slingshotBody.getTransform().setTranslation(new Vector2(-7,-3));
        slingshotBody.setMass(MassType.INFINITE);
        world.addBody(slingshotBody);

    }

    public void angryBlok(Vector2 placement){
        angryBlok = new Body();
        gameObjects.add(new GameObject("angryBlok.png",angryBlok,new Vector2(0,0),0.3));
        BodyFixture angryBlokFixture = new BodyFixture(Geometry.createSquare(1.1));
        angryBlokFixture.setRestitution(0.5);
        angryBlok.addFixture(angryBlokFixture);
        angryBlok.getTransform().setTranslation(new Vector2(placement));
        angryBlok.getFixture(0).setFriction(5);
        angryBlok.setMass(MassType.NORMAL);
        world.addBody(angryBlok);
    }

    public void shootBirb(){
        bigBoiBird.applyForce(new Vector2(20));
    }

    public static void main(String[] args) {
        launch(AngryBirds.class);
    }

}
