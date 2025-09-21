import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseHandler implements MouseWheelListener{
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double oldZoom = Constants.ZOOM;
        int mouseX = e.getX();
        int mouseY = e.getY();
        if(e.getWheelRotation() < 0) {
            Constants.ZOOM *= Constants.ZOOM_FACTOR;
        }
        else{
            Constants.ZOOM /= Constants.ZOOM_FACTOR;
        }

        double newZoom = Constants.ZOOM;
        double dx = mouseX - Constants.SCREEN_WIDTH / 2.0 - Constants.PAN_X;
        double dy = mouseY - Constants.SCREEN_HEIGHT / 2.0 - Constants.PAN_Y;

        Constants.PAN_X -= dx * (newZoom - oldZoom) / oldZoom;
        Constants.PAN_Y -= dy * (newZoom - oldZoom) / oldZoom;
    }
}
