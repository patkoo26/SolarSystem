package sk.patrik.oop.game.objects;

import sk.patrik.oop.game.Handler;

import java.awt.*;

/**
 * interface of all game objects in a game
 */
public interface GameObject {
    void tick();

    /**
     * @param g graphic
     */
    void render(Graphics g);

    Rectangle getBounds();

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    Handler getHandler();

    void onHandlerAdd(Handler handler);
}
