package sk.patrik.oop.walls;

import sk.patrik.oop.game.GameObject;
import sk.patrik.oop.game.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {
    private BufferedImage block_image;
    public Block(int x, int y, ID id, String path) {
        super(x, y, id,path);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, 32, 32);
        g.setColor(Color.BLACK);
        g.drawRect(x,y,32,32);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
