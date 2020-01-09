package sk.patrik.oop.game.objects.spacebodies;


import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.SpriteSheet;
import sk.patrik.oop.game.objects.AbstractGameObject;

import java.awt.*;
import java.awt.image.BufferedImage;


public class StaticSpaceBody extends AbstractGameObject implements SpaceBody {
    private BufferedImage sunAnimation;

    public StaticSpaceBody(int x, int y, String path) {
        super(x, y, path);
        BufferedImageLoader loader = new BufferedImageLoader();
        SpriteSheet sunSpriteSheet = new SpriteSheet(loader.loadImage(path));
        sunAnimation = sunSpriteSheet.grabImage(1, 1, 32, 32);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sunAnimation, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
