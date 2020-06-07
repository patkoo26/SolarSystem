package sk.patrik.oop.game.objects.openable;

import sk.patrik.oop.game.objects.players.Soldier;

public interface Openable {
    void open(Soldier soldier);
    void close();
    boolean isOpen();
}
