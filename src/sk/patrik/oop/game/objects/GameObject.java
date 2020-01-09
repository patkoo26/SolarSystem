package sk.patrik.oop.game.objects;

import sk.patrik.oop.game.Handler;

import java.awt.*;

/**
 * interface of all game objects in a game
 */
public interface GameObject {
    void tick();

    /**
     * @param g
     */
    void render(Graphics g);

    Rectangle getBounds();

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    float getVelX();

    void setVelX(float velX);

    float getVelY();

    void setVelY(float velY);

    Handler getHandler();

    void onHandlerAdd(Handler handler);
}
