package sk.patrik.oop;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Crate extends GameObject {

    private Handler handler;
    private BufferedImage crate_image;
    private SpriteSheet crateSpriteSheet;

    public Crate(int x, int y, ID id, String path) {
        super(x, y, id,path);

        BufferedImageLoader loader = new BufferedImageLoader();
        crateSpriteSheet = new SpriteSheet(loader.loadImage(path));
        crate_image = crateSpriteSheet.grabImage(1,1,32,32);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(crate_image,x,y,null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32,32);
    }
}
