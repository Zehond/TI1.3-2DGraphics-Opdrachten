import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.*;
public class TextObject {

    private double scale = 1;
    private Point2D position;
    private int hue = 0;
    private Font font;
    private Text text;

    public TextObject(Text text,Point2D position, int hue, double scale, Font font){
        this.position = position;
        this.hue = hue;
        this.scale = scale;
        this.font = font;
        this.text = text;
    }
    public void draw(FXGraphics2D g2d)
    {
        g2d.draw(getTransformedShape());
    }
//    public void draw(GraphicsContext graphicsContext){
//        graphicsContext.fillText("Hello world", position.getX(), position.getY());
//        graphicsContext.scale(scale,scale);
//
//    }

    public Shape getTransformedShape() {
        return getTransform().createTransformedShape((Shape) text);
    }

    public AffineTransform getTransform() {
        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX(), position.getY());
        tx.scale(scale,scale);
        return tx;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public int getHue() {
        return hue;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public String getText() {
        return text.getText();
    }

    public void setText(Text text) {
        this.text = text;
    }
}
