package sk.patrik.oop.game.objects.players;

import sk.patrik.oop.game.Animation;
import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.SpriteSheet;
import sk.patrik.oop.game.objects.usable.Usable;
import sk.patrik.oop.game.objects.walls.Wall;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Soldier extends AbstractControllablePlayer implements Player {

    private int hp;
    private int ammo;
    private List<BufferedImage> soldier_image = new ArrayList<>();
    private Animation anim;

    private static boolean isEnd;

    public Soldier(int x, int y, String path, int hp, int ammo) {
        super(x, y, path);
        this.hp = hp;
        this.ammo = ammo;
        isEnd = false;
        BufferedImageLoader loader = new BufferedImageLoader();
        SpriteSheet soldierSpriteSheet = new SpriteSheet(loader.loadImage(path));
        for (int i = 0; i < 5; i++) {
            soldier_image.add(soldierSpriteSheet.grabImage(i + 1, 1, 32, 32));
        }
        anim = new Animation(3, soldier_image);
    }

    public void tick() {
        move();
        handleCollisions();
        control();

        anim.runAnimation();
    }

    private void move() {
        x += velX;
        if (collidesWithBlock()) {
            x -= velX;
        }

        y += velY;
        if (collidesWithBlock()) {
            y -= velY;
        }
    }

    private boolean collidesWithBlock() {
        return getHandler().getObjects().stream().anyMatch(gameObject -> gameObject instanceof Wall && getBounds().intersects(gameObject.getBounds()));
    }

    private void handleCollisions() {
        getHandler().getObjects().stream()
                .filter(gameObject -> gameObject instanceof Usable && getBounds().intersects(gameObject.getBounds()))
                .forEach(gameObject -> {
                    ((Usable) gameObject).use(this);
                    getHandler().removeObject(gameObject);
                });
    }

    public void render(Graphics g) {
        if (velX == 0 && velY == 0) {
            g.drawImage(soldier_image.get(0), x, y, null);
        } else {
            anim.drawAnimation(g, x, y, 0);
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (hp <= 0) {
            getHandler().removeObject(this);
            isEnd = true;
        }
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public static boolean isEnd() {
        return isEnd;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

    @Override
    public void control() {
        super.control();
    }
}
