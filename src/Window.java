import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame implements Runnable {
    //array for drawing circles, array for bodies
    private ArrayList<Circle> circles = new ArrayList<>();
    public ArrayList<Body> bodies = new ArrayList<>();
    public Graphics2D g2;
    public Text zoomText;
    public Text timeScaleText;

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
        this.addMouseWheelListener(new MouseHandler());

        this.zoomText =  new Text(30, 70, "ZOOM:" + Constants.ZOOM, new Font("Lucida Console", Font.PLAIN, 30), Color.WHITE);
        this.timeScaleText =  new Text(30, 100, "TIME:" + Constants.TIME_SCALE, new Font("Lucida Console", Font.PLAIN, 30), Color.WHITE);

        /*
        * Body-Circle Pairs for Testing (Solar System)
        */

        //Sun
        Body sun = new Body(1.989e30, 0, 0, 0, 0,6.9634e8);
        bodies.add(sun);
        circles.add(new Circle(sun, Color.YELLOW));

        //Mercury
        Body mercury = new Body(3.285e23, 0, 0, 66.575e9 , 0,2.439e6);
        bodies.add(mercury);
        circles.add(new Circle(mercury, new Color(169, 169, 169)));

        /*
        //Venus
        Body venus = new Body(4.867e24, 0, 35020, 108.45e9, 0,6.0518e6);
        bodies.add(venus);
        circles.add(new Circle(venus, new Color(255, 204, 153)));

        //Earth
        Body earth = new Body(5.972e24, 0, 29783, 1.496e11, 0, 6.371e6);
        bodies.add(earth);
        circles.add(new Circle(earth, new Color(80, 160, 255)));

        //Mars
        Body mars = new Body(6.39e23,0,24077, 250.58e9,0, 3.3895e6);
        bodies.add(mars);
        circles.add(new Circle(mars, new Color(188, 39, 50)));

        // Jupiter
        Body jupiter = new Body(1.898e27, 0, 13070, 778.57e9, 0, 6.9911e7);
        bodies.add(jupiter);
        circles.add(new Circle(jupiter, new Color(194, 142, 85)));

        // Saturn
        Body saturn = new Body(5.683e26, 0, 9680, 1.4335e12, 0, 5.8232e7);
        bodies.add(saturn);
        circles.add(new Circle(saturn, new Color(226, 204, 128)));

        // Uranus
        Body uranus = new Body(8.681e25, 0, 6800, 2.8725e12, 0, 2.5362e7);
        bodies.add(uranus);
        circles.add(new Circle(uranus, new Color(173, 216, 230)));

        // Neptune
        Body neptune = new Body(1.024e26, 0, 5430, 4.4951e12, 0, 2.4622e7);
        bodies.add(neptune);
        circles.add(new Circle(neptune, new Color(72, 61, 139)));

         */
    }

    //update every frame
    public void update(double dt){
        updateGravity(dt);

        //double buffer
        Image dbImage = createImage(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);
        this.zoomText.updateZoom(zoomText, Constants.ZOOM);
        this.timeScaleText.updateScale(timeScaleText, Constants.TIME_SCALE);
    }

    /*
    * GENERALIZED FUNCTION TO HANDLE GRAVITY CALCULATIONS FOR N-BODIES
    */
    public void updateGravity(double dt){
        for(Body a : bodies){
            //net force
            double fx = 0;
            double fy = 0;
            for(Body b : bodies){
                if (a == b) continue;

                //get x and y direction vector and calculate total distance
                double dx = b.x - a.x;
                double dy = b.y - a.y;
                double distance = Math.sqrt(dx * dx + dy * dy);

                // very rough collision handling
                // this is a complete pos :(
                //screen-space
                double aScreenX = (int)(Constants.SCREEN_WIDTH / 2 + (a.x / Constants.SCALE) * Constants.ZOOM + Constants.PAN_X);
                double aScreenY = (int)(Constants.SCREEN_HEIGHT / 2 + (a.y / Constants.SCALE) * Constants.ZOOM + Constants.PAN_Y);
                double bScreenX = (int)(Constants.SCREEN_WIDTH / 2 + (b.x / Constants.SCALE) * Constants.ZOOM + Constants.PAN_X);
                double bScreenY = (int)(Constants.SCREEN_HEIGHT / 2 + (b.y / Constants.SCALE) * Constants.ZOOM + Constants.PAN_Y);
                //touching colours
                double ar = a.getVisualRadius() * Constants.ZOOM;
                double br = a.getVisualRadius() * Constants.ZOOM;
                double visualDistance = Math.hypot(bScreenX - aScreenX, bScreenY - aScreenY);
                if (visualDistance <= (ar + br)){
                    System.out.println("Touching!");
                }
                //end of the collision handling

                //Newton's gravity force calculation
                double force = Constants.GRAVITATIONAL_CONSTANT * a.mass * b.mass / (distance * distance);

                //break force down in x and y components
                fx += force * dx/distance;
                fy += force * dy/distance;
            }

            //acceleration
            double ax = fx /a.mass;
            double ay = fy /a.mass;

            //update velocity of a
            a.vx += ax * dt;
            a.vy += ay * dt;
        }

        for (Body b: bodies){
            //update position of each body after loop
            b.x += b.vx * dt;
            b.y += b.vy * dt;
        }
    }

    //draw to buffer image
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        for (Circle circle : circles) {
            circle.draw(g2);
        }
        zoomText.draw(g2);
        timeScaleText.draw(g2);
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
