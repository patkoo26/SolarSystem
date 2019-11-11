package sk.patrik.oop.usable;

import com.sun.xml.internal.bind.WhiteSpaceProcessor;
import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.GameObject;
import sk.patrik.oop.game.ID;
import sk.patrik.oop.game.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FirstAid extends GameObject {
    private BufferedImage firstAid_image;

    public FirstAid(int x, int y, ID id, String path) {
        super(x, y, id, path);
        SpriteSheet firstAidSpriteSheet;
        BufferedImageLoader loader = new BufferedImageLoader();
        firstAidSpriteSheet = new SpriteSheet(loader.loadImage(path));
        firstAid_image = firstAidSpriteSheet.grabImage(1,1,32,32);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(x-1,y-1,33,33);
        g.drawImage(firstAid_image,x,y,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }
}
