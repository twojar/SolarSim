import java.awt.*;
import java.util.ArrayList;

public class Circle {
    public Color color;
    public Body body;
    private ArrayList<double[]> trail = new ArrayList<>();

    //construct
    public Circle(Body body, Color color) {
        this.body = body;
        this.color = color;
    }

    //draw circle
    public void draw(Graphics g) {
        g.setColor(color);

        //draw relative to center
        int screenX = (int)(Constants.SCREEN_WIDTH / 2 + (body.x / Constants.SCALE) * Constants.ZOOM + Constants.PAN_X);
        int screenY = (int)(Constants.SCREEN_HEIGHT / 2 + (body.y / Constants.SCALE) * Constants.ZOOM + Constants.PAN_Y);

        //use scaled radius and not actual radius for drawing
        double scaledRadius = body.getVisualRadius() * Constants.ZOOM;
        g.fillOval(
                (int) (screenX - scaledRadius),
                (int) (screenY - scaledRadius),
                (int) (scaledRadius * 2),
                (int) (scaledRadius * 2)
        );

        //update trail and draw
        trail.add(new double[]{body.x,body.y});
        if(trail.size() > 10000) trail.remove(0);
        g.setColor(color.darker());
        for (double[] pos: trail){
            //convert position to screen coordinates and draw
            int px = (int)(Constants.SCREEN_WIDTH / 2 + (pos[0] / Constants.SCALE) * Constants.ZOOM + Constants.PAN_X);
            int py = (int) (Constants.SCREEN_HEIGHT / 2 + (pos[1] / Constants.SCALE) * Constants.ZOOM + Constants.PAN_Y);
            g.fillRect(px,py,1,1);
        }
    }


}
