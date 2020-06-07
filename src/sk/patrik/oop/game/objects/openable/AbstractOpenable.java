package sk.patrik.oop.game.objects.openable;

import sk.patrik.oop.game.objects.AbstractGameObject;

import java.awt.*;

public abstract class AbstractOpenable extends AbstractGameObject implements Openable {
    private boolean isOpen = false;
    public AbstractOpenable(int x, int y, String path) {
        super(x, y, path);
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, 32, 32);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
