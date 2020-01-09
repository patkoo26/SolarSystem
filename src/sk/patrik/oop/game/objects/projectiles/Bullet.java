package sk.patrik.oop.game.objects.projectiles;

import sk.patrik.oop.game.objects.AbstractGameObject;
import sk.patrik.oop.game.objects.GameObject;
import sk.patrik.oop.game.objects.walls.Wall;

import java.awt.*;

public class Bullet extends AbstractGameObject implements Projectile {

    public Bullet(int x, int y, int mx, int my, String path) {
        super(x, y, path);

        velX = (mx - x) / 10;
        velY = (my - y) / 10;
    }

    public void tick() {
        x += velX;
        y += velY;
        for (int i = 0; i < getHandler().getObjects().size(); i++) {
            GameObject tempObject = getHandler().getObjects().get(i);

            if (tempObject instanceof Wall) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    getHandler().removeObject(this);
                }
            }
        }

    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, 8, 8);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
    }
}
