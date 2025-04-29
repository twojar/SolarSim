import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame implements Runnable {

    //array for drawing circles
    private ArrayList<Circle> circles = new ArrayList<>();

    //sun body for testing
    public Body earth;
    public Body sun;
    public Graphics2D g2;


    //init window
    public Window(){
        //window properties
        this.setTitle(Constants.SCREEN_TITLE);
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        //get insets of the window
        Insets insets = this.getInsets();
        Constants.INSETS_BOTTOM = insets.bottom;
        Constants.INSETS_RIGHT = insets.right;

        g2 = (Graphics2D) this.getGraphics();
        this.addKeyListener(new KeyHandler());

        //sun body pair
        this.sun = new Body(1.989e30, 0, 0, 0, 0,20);
        circles.add(new Circle(sun, Color.YELLOW));

        this.earth = new Body(5.972e24, 0, 29783, 1.496e11, 0, 5); // mass, vx, vy, x, y, radius
        circles.add(new Circle(earth, Color.BLUE));
    }


    //update every frame
    public void update(double dt){
        updateGravity(dt);

        //double buffer
        Image dbImage = createImage(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);
    }

    public void updateGravity(double dt){
        double dx = sun.x - earth.x;
        double dy = sun.y - earth.y;
        double distance = Math.sqrt(dx*dx + dy*dy); //a^2 + b^2 = c^2

        //force equation
        double force = Constants.GRAVITATIONAL_CONSTANT * ((sun.mass * earth.mass)/(distance * distance));

        //normalize direction
        double fx = force * dx/distance;
        double fy = force * dy/distance;

        //acceleration
        double ax = fx/earth.mass;
        double ay = fy/earth.mass;
        earth.vx += ax * dt;
        earth.vy += ay * dt;

        //update position with velocity
        earth.x += earth.vx * dt;
        earth.y += earth.vy * dt;
    }

    //draw to buffer image
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        for (Circle circle : circles) {
            circle.draw(g2);
        }
    }


    //run window thread
    public void run(){
        double lastFrameTime = 0.0;
        while(true){
            double time = Time.getTime();
            double deltaTime = (time - lastFrameTime) * Constants.TIME_SCALE;
            lastFrameTime = time;
            update(deltaTime);
        }
    }
}
