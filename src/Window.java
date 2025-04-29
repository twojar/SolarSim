import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame implements Runnable {

    //array for drawing circles
    private ArrayList<Circle> circles = new ArrayList<>();

    //sun body for testing
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

        g2 = (Graphics2D) this.getGraphics();

        //sun
        this.sun = new Body(1.989e30, 400, 400, Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2);

        //create circles
        circles.add(new Circle(sun, 20, Color.YELLOW));
    }


    //update every frame
    public void update(double dt){
        //double buffer
        Image dbImage = createImage(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);
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
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;
            update(deltaTime);
        }
    }
}
