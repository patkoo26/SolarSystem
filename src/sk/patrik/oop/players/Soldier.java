package sk.patrik.oop.players;

import sk.patrik.oop.game.ID;
import sk.patrik.oop.game.SpriteSheet;
import sk.patrik.oop.game.Animation;
import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.GameObject;
import sk.patrik.oop.game.Handler;
import sk.patrik.oop.usable.Usable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Soldier extends GameObject {

    private Handler handler;
    private GameObject tempObject;
    private int hp;
    private int ammo;
    private Graphics2D graphics2D;
    private BufferedImage[] soldier_image = new BufferedImage[5];
    private SpriteSheet soldierSpriteSheet;
    private Animation anim;



    private static boolean isEnd;

    public Soldier(int x, int y, ID id, Handler handler, String path, int hp, int ammo) {
        super(x, y, id,path);
        this.handler = handler;
        this.hp = hp;
        this.ammo = ammo;
        isEnd = false;
        BufferedImageLoader loader = new BufferedImageLoader();
        soldierSpriteSheet = new SpriteSheet(loader.loadImage(path));
        for(int i = 0; i < soldier_image.length;i++) {
            soldier_image[i] = soldierSpriteSheet.grabImage(i+1, 1, 32, 32);
        }
        anim = new Animation(3, soldier_image[0],soldier_image[1],soldier_image[2],soldier_image[3],soldier_image[4]);
    }

    public void tick() {
        x += velX;
        y += velY;

        collision();

        if(handler.isUp()) velY = -5;
        else if(!handler.isDown()) velY = 0;

        if(handler.isDown()) velY = 5;
        else if(!handler.isUp()) velY = 0;

        if(handler.isRight()) velX = 5;
        else if (!handler.isLeft()) velX = 0;

        if(handler.isLeft()) velX = -5;
        else if(!handler.isRight()) velX = 0;

        anim.runAnimation();
    }

    private void collision(){
        /*Usable usable = null;
        for(int i = 0;i < handler.getObject().size(); i++){
            if(usable.getId() == ID.UsableItem){
                usable =(Usable) handler.getObject().get(i);
                if(getBounds().intersects(usable.getBounds()) && getHp() < 100){
                    setHp(usable.getUse());
                    handler.removeObject(tempObject);
                }
            }
        }*/


        for(int i = 0; i < handler.getObject().size(); i++){
            tempObject = handler.getObject().get(i);
            if(tempObject.getId() == ID.Block){
                if(getBounds().intersects(tempObject.getBounds())){
                    x += velX * -1;
                    y += velY * -1;
                }
            }

            if(tempObject.getId() == ID.Ammo){
                if(getBounds().intersects(tempObject.getBounds())){
                    setAmmo(getAmmo()+10);
                    handler.removeObject(tempObject);
                }
            }

            if(tempObject.getId() == ID.FirstAid){
                if(getBounds().intersects(tempObject.getBounds()) && getHp() < 100){
                    setHp(100);
                    handler.removeObject(tempObject);
                }
            }

            if(tempObject.getId() == ID.Enemy){
                if(getBounds().intersects(tempObject.getBounds())){
                    setHp(getHp()-1);
                    if(getHp() <= 0){
                        isEnd = true;
                        handler.removeObject(this);
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        if(velX == 0 && velY == 0) {
            g.drawImage(soldier_image[0], x, y, null);
        }else{
            anim.drawAnimation(g,x,y,0);
        }
    }

    //********************health*****************
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    //************************************ammo***********************

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    //***********************************************


    public static boolean isEnd() {
        return isEnd;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32,32);
    }
}
