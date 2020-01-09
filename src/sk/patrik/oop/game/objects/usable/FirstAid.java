package sk.patrik.oop.game.objects.usable;

import sk.patrik.oop.game.objects.players.Soldier;

import java.awt.*;

public class FirstAid extends AbstractUsable {

    public FirstAid(int x, int y, String path, Color color, int use) {
        super(x, y, path, color, use);
    }

    @Override
    public void use(Soldier soldier) {
        soldier.setHp(100);
    }
}