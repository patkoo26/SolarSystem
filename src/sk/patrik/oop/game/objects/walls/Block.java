package sk.patrik.oop.game.objects.walls;

import sk.patrik.oop.game.objects.AbstractGameObject;

import java.awt.*;

public class Block extends AbstractGameObject implements Wall {
    public Block(int x, int y, String path) {
        super(x, y, path);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, 32, 32);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 32, 32);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
