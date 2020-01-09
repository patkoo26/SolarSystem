package sk.patrik.oop.game.objects.enemies;


import sk.patrik.oop.game.objects.players.Player;

import java.awt.*;

/**
 * Represents smart enemy witch is following player
 */
public class SmartEnemy extends BasicEnemy {

    /**
     * Constructor
     *
     * @param x    starting x position
     * @param y    starting y position
     * @param path path to the sprite
     * @param hp   starting health points
     */
    public SmartEnemy(int x, int y, String path, int hp) {
        super(x, y, path, hp);
    }

    /**
     * Set direction of this enemy in direction of player
     */
    @Override
    protected void setVelocities() {
        getHandler().getObjects().stream()
                .filter(gameObject -> gameObject instanceof Player)
                .findFirst()
                .ifPresent(player -> {
                    velX = player.getX() > x ? 2 : -2;
                    velY = player.getY() > y ? 2 : -2;
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(Color.RED);
        g.drawOval(x, y, 32, 32);
    }
}
