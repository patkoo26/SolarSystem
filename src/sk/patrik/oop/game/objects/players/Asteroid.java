package sk.patrik.oop.game.objects.players;

import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.SpriteSheet;
import sk.patrik.oop.game.objects.GameObject;
import sk.patrik.oop.game.objects.spacebodies.OrbitingSpaceBody;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Asteroid extends AbstractControllablePlayer implements Player {
    private BufferedImage asteroidAnimation;

    public Asteroid(int x, int y, String path) {
        super(x, y, path);
        BufferedImageLoader loader = new BufferedImageLoader();
        SpriteSheet asteroidSpriteSheet = new SpriteSheet(loader.loadImage(path));
        asteroidAnimation = asteroidSpriteSheet.grabImage(1, 1, 32, 32);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        control();
        collision();
    }

    private void collision() {
        for (int i = 0; i < getHandler().getObjects().size(); i++) {
            GameObject gameObject = getHandler().getObjects().get(i);
            if (gameObject instanceof OrbitingSpaceBody && ((OrbitingSpaceBody) gameObject).getName().equalsIgnoreCase("earth")) {
                if (getBounds().intersects(gameObject.getBounds())) {
                    getHandler().addObject(new SpaceShip(gameObject.getX(), gameObject.getY(), "/spaceShip.png"));
                    getHandler().removeObject(gameObject);
                    getHandler().removeObject(this);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(asteroidAnimation, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);

    }

    @Override
    public void control() {
        super.control();
    }
}
