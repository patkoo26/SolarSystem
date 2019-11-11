package sk.patrik.oop.enemies;


import sk.patrik.oop.Game;
import sk.patrik.oop.game.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SmartEnemy extends GameObject {
    private Handler handler;
    private GameObject gameObject;
    private SpriteSheet enemySpriteSheet;
    private Animation anim;
    private int hp;
    private BufferedImage[] enemy_image = new BufferedImage[3];

    public SmartEnemy(int x, int y, ID id, Handler handler, String path, int hp) {
        super(x, y, id, path);
        this.handler = handler;
        this.hp = hp;
        BufferedImageLoader loader = new BufferedImageLoader();
        enemySpriteSheet = new SpriteSheet(loader.loadImage(path));
        for(int i = 0; i < enemy_image.length; i++) {
            enemy_image[i] = enemySpriteSheet.grabImage(i+4, 1, 32, 32);
        }

        anim = new Animation(3,enemy_image[0],enemy_image[1],enemy_image[2]);
    }



    public void tick() {

        for(int i = 0; i < handler.getObject().size(); i++){
            gameObject = handler.getObject().get(i);
            if(gameObject.getId() == ID.Player && Game.getLevel() == 2){
                if(gameObject.getX() > x){
                    x++;
                } else if(gameObject.getX() < x){
                    x--;
                }
                if(gameObject.getY() > y){
                    y++;
                } else if(gameObject.getY() < y){
                    y--;
                }
            }
            if(gameObject.getId() == ID.Bullet) {
                if (getBounds().intersects(gameObject.getBounds())) {
                    hp -= 50;
                    handler.removeObject(gameObject);
                }

            }
        }
        anim.runAnimation();
        if(hp <= 0){
            handler.removeObject(this);
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.drawOval(x,y,32,32);
        anim.drawAnimation(g,x,y,0);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

    private Rectangle getBoundsBig() {
        return new Rectangle(x, y, 64, 64);
    }
}
