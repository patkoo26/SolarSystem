package sk.patrik.oop.usable;

import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.GameObject;
import sk.patrik.oop.game.ID;
import sk.patrik.oop.game.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Usable extends GameObject {
    private BufferedImage usable_image;
    protected Color color;

    private int use;
    public Usable(int x, int y, ID id, String path, Color color, int use) {
        super(x, y, id, path);
        this.color = color;
        SpriteSheet firstAidSpriteSheet;
        BufferedImageLoader loader = new BufferedImageLoader();
        firstAidSpriteSheet = new SpriteSheet(loader.loadImage(path));
        usable_image = firstAidSpriteSheet.grabImage(1,1,32,32);
    }

    public int getUse() {
        return use;
    }

    @Override
    public void tick() {

    }


    public void render(Graphics g) {
        //g.setColor(color);
        g.drawRect(x-1,y-1,33,33);
        g.drawImage(usable_image,x,y,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }
}
