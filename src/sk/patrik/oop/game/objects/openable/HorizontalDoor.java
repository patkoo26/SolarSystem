package sk.patrik.oop.game.objects.openable;

import sk.patrik.oop.game.objects.players.Soldier;

public class HorizontalDoor extends AbstractOpenable {
    public HorizontalDoor(int x, int y, String path) {
        super(x, y, path);
    }

    @Override
    public void open(Soldier soldier) {

    }

    @Override
    public void close() {

    }
}
