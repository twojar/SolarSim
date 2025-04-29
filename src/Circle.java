import java.awt.*;

public class Circle {
    public Color color;
    public Body body;
    public double radius;

    //construct
    public Circle(Body body, int radius, Color color) {
        this.body = body;
        this.radius = radius;
        this.color = color;
    }

    //draw circle
    public void draw(Graphics g) {
        g.setColor(color);
        //g.drawOval((int) body.x, (int) body.y, (int) radius * 2, (int) radius * 2);
        g.fillOval((int) body.x, (int) body.y, (int) radius* 2, (int) radius * 2);
    }


}
