import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class PointShape {
    private Point2D position;
    private double speedX = 1;
    private double speedY = 1;

    public PointShape( Point2D position){
        this.position = position;
    }


    public Point2D getPosition() {
        return position;
    }

//    public void draw(FXGraphics2D g2d)
//    {
//        g2d.fill(getTransformedShape());
//
//    }
    public void updatePoint(ResizableCanvas canvas, int pointsInArrayList){
        checkBoundaries(canvas);
        double x = getPosition().getX();
        double y = getPosition().getY();
        switch (pointsInArrayList){
            case 0:
               this.position = new Point2D.Double(x-speedX,y+speedY);
                break;
            case 1:
                this.position = new Point2D.Double(x+speedX,y-speedY);
                break;
            case 2:
                this.position = new Point2D.Double(x-speedX,y-speedY);
                break;
            case 3:
                this.position = new Point2D.Double(x+speedX,y+speedY);
                break;
        }
    }
    public void checkBoundaries(ResizableCanvas canvas){
        if (position.getX() >= canvas.getWidth() || position.getX() <= 0) {
            speedX = -speedX;
        }
        if (position.getY() >= canvas.getHeight() || position.getY() <= 0){
            speedY = -speedY;
        }
    }
    public void setPosition(Point2D position) {
        this.position = position;
    }

//    public java.awt.Shape getTransformedShape() {
//        return getTransform().createTransformedShape(shape);
//    }
//    public AffineTransform getTransform() {
//        AffineTransform tx = new AffineTransform();
//        tx.translate(position.getX(), position.getY());
//        tx.scale(scale,scale);
//        return tx;
//    }
}
