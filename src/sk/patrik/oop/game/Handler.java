package sk.patrik.oop.game;

import sk.patrik.oop.game.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Handler {
    private List<GameObject> objects = new ArrayList<>();
    private boolean up = false, down = false, right = false, left = false;

    public void tick() {
        for (int i = 0; i < objects.size(); i++) {
            GameObject gameObject = objects.get(i);
            gameObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject gameObject = objects.get(i);
            gameObject.render(g);
        }
    }

    public List<GameObject> getObjects() {
        return new ArrayList<>(objects);
    }

    public void addObject(GameObject gameObject) {
        objects.add(gameObject);
        gameObject.onHandlerAdd(this);
    }

    public void removeObject(GameObject gameObject) {
        objects.remove(gameObject);
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
}
