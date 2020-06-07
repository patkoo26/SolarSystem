package sk.patrik.oop.game.objects.usable;

import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.SpriteSheet;
import sk.patrik.oop.game.objects.AbstractGameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractUsable extends AbstractGameObject implements Usable {
    private BufferedImage usable_image;
    Color color;

    AbstractUsable(int x, int y, String path, Color color) {
        super(x, y, path);
        this.color = color;
        SpriteSheet firstAidSpriteSheet;
        BufferedImageLoader loader = new BufferedImageLoader();
        firstAidSpriteSheet = new SpriteSheet(loader.loadImage(path));
        usable_image = firstAidSpriteSheet.grabImage(1, 1, 32, 32);
    }

    @Override
    public void tick() {

    }

    public void render(Graphics g) {
        //g.setColor(color);
        g.drawRect(x - 1, y - 1, 33, 33);
        g.drawImage(usable_image, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
