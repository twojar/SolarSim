import java.awt.*;
import java.util.ArrayList;

public class Circle {
    public Color color;
    public Body body;
    private ArrayList<Point> trail = new ArrayList<>();

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

        //update trail and draw
        trail.add(new Point(screenX, screenY));
        if(trail.size() > 100000) trail.remove(0);
        g.setColor(color.darker());
        for(Point p : trail) {
            g.fillRect(p.x, p.y, 1, 1);
        }
    }


}
