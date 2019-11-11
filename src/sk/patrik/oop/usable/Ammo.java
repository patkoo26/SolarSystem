package sk.patrik.oop.usable;

import sk.patrik.oop.game.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ammo extends GameObject {

    private Handler handler;
    private BufferedImage ammo_image;
    private SpriteSheet ammoSpriteSheet;

    public Ammo(int x, int y, ID id, String path) {
        super(x, y, id,path);

        BufferedImageLoader loader = new BufferedImageLoader();
        ammoSpriteSheet = new SpriteSheet(loader.loadImage(path));
        ammo_image = ammoSpriteSheet.grabImage(1,1,32,32);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(x-1,y-1,33,33);
        g.drawImage(ammo_image,x,y,null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32,32);
    }
}
