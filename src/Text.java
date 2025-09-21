import java.awt.*;
public class Text {
    public int x;
    public int y;
    public String text;
    public Font font;
    public Color color;

    public Text(int x, int y, String text, Font font, Color color) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = font;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    public void updateZoom(Text text, Double value){
        text.text = String.format("ZOOM: %.1fx", value);
    }

    public void updateScale(Text text, Double value){
        text.text = String.format("TIME: %.0fx", value);
    }
}
