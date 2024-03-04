import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;


public class spotlightShape {

    private Shape shape;
    private Point2D position;

    public spotlightShape(Shape shape, Point2D position){
        this.shape = shape;
        this.position = position;
    }
    public void draw(FXGraphics2D g2d)
    {
        g2d.draw(getTransformedShape());
    }
    public void setPosition(Point2D position) {
        this.position = position;
    }

    public java.awt.Shape getTransformedShape() {
        return getTransform().createTransformedShape(shape);
    }
    public AffineTransform getTransform() {
        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX(), position.getY());
        return tx;
    }
}
