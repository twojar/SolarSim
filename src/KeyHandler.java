import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        // empty
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) Constants.PAN_X += 20;
        if (key == KeyEvent.VK_RIGHT) Constants.PAN_X -= 20;
        if (key == KeyEvent.VK_UP) Constants.PAN_Y += 20;
        if (key == KeyEvent.VK_DOWN) Constants.PAN_Y -= 20;
        if (key == KeyEvent.VK_EQUALS) Constants.ZOOM *= 1.1;
        if (key == KeyEvent.VK_MINUS) Constants.ZOOM /= 1.1;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //empty
    }
}
