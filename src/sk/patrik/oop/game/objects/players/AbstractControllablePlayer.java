package sk.patrik.oop.game.objects.players;

import sk.patrik.oop.game.objects.AbstractGameObject;

/**
 * Represents Controllable Player
 */
public abstract class AbstractControllablePlayer extends AbstractGameObject implements Player {

    /**
     * Constructor
     *
     * @param x    starting x position
     * @param y    starting y position
     * @param path path to the sprite
     */
    AbstractControllablePlayer(int x, int y, String path) {
        super(x, y, path);
    }

    /**
     * controls velocities after change of direction
     */
    public void control() {
        if (getHandler().isUp()) velY = -5;
        else if (!getHandler().isDown()) velY = 0;

        if (getHandler().isDown()) velY = 5;
        else if (!getHandler().isUp()) velY = 0;

        if (getHandler().isRight()) velX = 5;
        else if (!getHandler().isLeft()) velX = 0;

        if (getHandler().isLeft()) velX = -5;
        else if (!getHandler().isRight()) velX = 0;
    }
}
