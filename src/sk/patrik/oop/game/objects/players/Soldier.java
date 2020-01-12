package sk.patrik.oop.game.objects.players;

import sk.patrik.oop.game.Animation;
import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.SpriteSheet;
import sk.patrik.oop.game.objects.usable.FirstAid;
import sk.patrik.oop.game.objects.usable.Usable;
import sk.patrik.oop.game.objects.walls.Wall;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Soldier extends AbstractControllablePlayer implements Player {

    private int health;
    private int ammo;
    private List<BufferedImage> soldier_image = new ArrayList<>();
    private Animation anim;
    private static boolean isEnd;

    /**
     * Constructor
     * @param x starting x position
     * @param y starting y position
     * @param path path to the sprite
     * @param health starting health points
     * @param ammo  starting ammo points
     */
    public Soldier(int x, int y, String path, int health, int ammo) {
        super(x, y, path);
        this.health = health;
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

    /**
     * control move direction
     */
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

    /**
     * Check whether object is colliding with a block
     * @return true if object is colliding with a block, false otherwise
     */
    private boolean collidesWithBlock() {
        return getHandler().getObjects().stream().anyMatch(gameObject -> gameObject instanceof Wall && getBounds().intersects(gameObject.getBounds()));
    }

    /**
     * handle collision with usable items
     */
    private void handleCollisions() {
        getHandler().getObjects().stream()
                .filter(gameObject -> gameObject instanceof Usable && getBounds().intersects(gameObject.getBounds()))
                .filter(gameObject -> !(gameObject instanceof FirstAid && getHealth() >= 100))
                .forEach(gameObject -> {
                    ((Usable) gameObject).use(this);
                    getHandler().removeObject(gameObject);
                });
    }

    /**
     * {@inheritDoc}
     */
    public void render(Graphics g) {
        if (velX == 0 && velY == 0) {
            g.drawImage(soldier_image.get(0), x, y, null);
        } else {
            anim.drawAnimation(g, x, y, 0);
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int hp) {
        this.health = hp;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void control() {
        super.control();
    }
}
