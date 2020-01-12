package sk.patrik.oop.game.objects.enemies;

import sk.patrik.oop.game.Animation;
import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.SpriteSheet;
import sk.patrik.oop.game.objects.AbstractGameObject;
import sk.patrik.oop.game.objects.players.Soldier;
import sk.patrik.oop.game.objects.projectiles.Projectile;
import sk.patrik.oop.game.objects.walls.Wall;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents basic enemy with random movement around the map.
 */
public class BasicEnemy extends AbstractGameObject implements Enemy {
    private Animation animation;
    private Random r = new Random();
    private int hp;

    /**
     * Constructor
     *
     * @param x    starting x position
     * @param y    starting y position
     * @param path path to the sprite
     * @param hp   starting health points
     */
    public BasicEnemy(int x, int y, String path, int hp) {
        super(x, y, path);
        this.hp = hp;
        BufferedImageLoader loader = new BufferedImageLoader();
        SpriteSheet enemySpriteSheet = new SpriteSheet(loader.loadImage(path));
        List<BufferedImage> enemy_image = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            enemy_image.add(enemySpriteSheet.grabImage(i + 4, 1, 32, 32));
        }

        animation = new Animation(3, enemy_image);
    }

    @Override
    public void tick() {
        move();
        handleCollisions();
        getHandler().getObjects().forEach(gameObject -> {
            if (gameObject instanceof Projectile) {
                if (getBounds().intersects(gameObject.getBounds())) {
                    hp -= 50;
                    getHandler().removeObject(gameObject);
                }
            }
        });

        animation.runAnimation();
        if (hp <= 0) {
            getHandler().removeObject(this);
        }
    }

    /**
     * Handles behaviour of enemy on collision with player
     */
    private void handleCollisions() {
        getHandler().getObjects().stream()
                .filter(gameObject -> gameObject instanceof Soldier && getBounds().intersects(gameObject.getBounds()))
                .findFirst()
                .ifPresent(player -> ((Soldier) player).setHealth(((Soldier) player).getHealth() - 1));
    }

    /**
     * Handles randomized movement of the enemy
     */
    private void move() {
        setVelocities();
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
     * Sets randomized direction for this enemy
     */
    protected void setVelocities() {
        int changeDirectionChance = r.nextInt(14);
        if (changeDirectionChance == 1) {
            velX = (r.nextInt(3) - 1) * 2;
            velY = (r.nextInt(3) - 1) * 2;
        }
    }

    /**
     * Check whether object is colliding with a block
     *
     * @return true if object is colliding with a block, false otherwise
     */
    private boolean collidesWithBlock() {
        return getHandler().getObjects().stream().anyMatch(gameObject -> gameObject instanceof Wall && getBounds().intersects(gameObject.getBounds()));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

    @Override
    public void render(Graphics g) {
        animation.drawAnimation(g, x, y, 0);
    }
}
