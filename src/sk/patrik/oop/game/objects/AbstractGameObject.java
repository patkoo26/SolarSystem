package sk.patrik.oop.game.objects;

import sk.patrik.oop.game.Handler;

import java.awt.*;

public abstract class AbstractGameObject implements GameObject {
    protected int x, y;
    protected float velX = 0, velY = 0;
    protected String path;
    private Handler handler;

    public AbstractGameObject(int x, int y, String path) {
        this.x = x;
        this.y = y;
        this.path = path;
    }

    @Override
    public abstract void tick();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void render(Graphics g);

    @Override
    public abstract Rectangle getBounds();

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public float getVelX() {
        return velX;
    }

    @Override
    public void setVelX(float velX) {
        this.velX = velX;
    }

    @Override
    public float getVelY() {
        return velY;
    }

    @Override
    public void setVelY(float velY) {
        this.velY = velY;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void onHandlerAdd(Handler handler) {
        this.handler = handler;
    }
}
