import java.awt.*;

public class Circle {
    public Color color;
    public Body body;

    //construct
    public Circle(Body body, Color color) {
        this.body = body;
        this.color = color;
    }

    //draw circle
    public void draw(Graphics g) {
        g.setColor(color);

        //draw relative to center
        int screenX = (int)(Constants.SCREEN_WIDTH / 2 + body.x / Constants.SCALE);
        int screenY = (int)(Constants.SCREEN_HEIGHT / 2 + body.y / Constants.SCALE);
        g.fillOval((int) (screenX - body.radius), (int) (screenY - body.radius), (int) body.radius * 2, (int) body.radius * 2);
    }


}
