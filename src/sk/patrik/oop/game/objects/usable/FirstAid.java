package sk.patrik.oop.game.objects.usable;

import sk.patrik.oop.game.objects.players.Soldier;

import java.awt.*;

public class FirstAid extends AbstractUsable {

    public FirstAid(int x, int y, String path, Color color) {
        super(x, y, path, color);
    }

    @Override
    public void use(Soldier soldier) {
        soldier.setHealth(100);
    }
}
