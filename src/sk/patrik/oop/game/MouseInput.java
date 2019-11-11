package sk.patrik.oop.game;

import sk.patrik.oop.weapons.Bullet;
import sk.patrik.oop.Game;
import sk.patrik.oop.players.Soldier;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    private Handler handler;
    private Camera camera;
    private Soldier soldier;

    private GameObject tempObject;

    public MouseInput(Handler handler, Camera camera, Soldier soldier){
        this.handler = handler;
        this.camera = camera;
        this.soldier = soldier;
    }


    public void mousePressed(MouseEvent e){
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        /*for(int i = 0; i < handler.getObject().size(); i++){
            tempObject = handler.getObject().get(i);

            if(tempObject.getId() == ID.Player && soldier.getAmmo() > 0 && Game.getLevel() == 2)  {
                handler.addObject(new Bullet(tempObject.getX()+16,tempObject.getY()+16,ID.Bullet,handler, mx,my,null));
                soldier.setAmmo(soldier.getAmmo()-1);
            }
        }*/
        if(soldier != null) {
            if (soldier.getAmmo() > 0 && Game.getLevel() == 2) {
                handler.addObject(new Bullet(soldier.getX() + 16, soldier.getY() + 16, ID.Bullet, handler, mx, my, null));
                soldier.setAmmo(soldier.getAmmo() - 1);
            }
        }
    }


}
