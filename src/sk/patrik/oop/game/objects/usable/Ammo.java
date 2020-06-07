package sk.patrik.oop.game.objects.usable;

import sk.patrik.oop.game.objects.players.Soldier;

import java.awt.*;

public class Ammo extends AbstractUsable {

    public Ammo(int x, int y, String path, Color color) {
        super(x, y, path, color);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        super.render(g);
    }

    @Override
    public void use(Soldier soldier) {
        soldier.setAmmo(soldier.getAmmo() + 10);
    }
}
