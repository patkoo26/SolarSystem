package sk.patrik.oop;

import java.awt.*;

public class Box extends GameObject{
    public Box(int x, int y) {
        super(x, y);
    }

    public void tick() {
        x += velX;
        y += velY;
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x,y,32,32);
    }

    public Rectangle getBounds() {
        return null;
    }
}
