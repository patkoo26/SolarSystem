package sk.patrik.oop.game.objects.openable;

import sk.patrik.oop.game.objects.players.Soldier;

public class VerticalDoor extends AbstractOpenable {
    public VerticalDoor(int x, int y, String path) {
        super(x, y, path);
    }

    @Override
    public void open(Soldier soldier) {

    }

    @Override
    public void close() {

    }
}
