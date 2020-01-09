package sk.patrik.oop.game.inputs;

import sk.patrik.oop.Game;
import sk.patrik.oop.game.Camera;
import sk.patrik.oop.game.Handler;
import sk.patrik.oop.game.objects.players.Soldier;
import sk.patrik.oop.game.objects.projectiles.Bullet;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    private Handler handler;
    private Camera camera;
    private Soldier soldier;

    public MouseInput(Handler handler, Camera camera, Soldier soldier) {
        this.handler = handler;
        this.camera = camera;
        this.soldier = soldier;
    }

    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        if (soldier != null) {
            if (soldier.getAmmo() > 0 && Game.getLevel() == 2) {
                handler.addObject(new Bullet(soldier.getX() + 16, soldier.getY() + 16, mx, my, null));
                soldier.setAmmo(soldier.getAmmo() - 1);
            }
        }
    }


}
