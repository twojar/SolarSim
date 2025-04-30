public class Body {
    public double mass;
    public double vx,vy;
    public double x,y;
    public double radius;

    //construct
    public Body(double mass, double vx, double vy, double x, double y, double radius) {
        this.mass = mass;
        this.vx = vx;
        this.vy = vy;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public double getVisualRadius(){
        return  Math.cbrt(this.radius) * 0.01;
    }


}
