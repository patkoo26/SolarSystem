package sk.patrik.oop.usable;

import sk.patrik.oop.game.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ammo extends Usable {

    public Ammo(int x, int y, ID id, String path,Color color, int use) {
        super(x, y, id,path, color, use);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        super.render(g);
    }
}
