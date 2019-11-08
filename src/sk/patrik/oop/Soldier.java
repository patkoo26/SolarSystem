package sk.patrik.oop;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Soldier extends GameObject {

    private Handler handler;
    private GameObject tempObject;
    private Game game;
    private BufferedImage[] wizzard_image = new BufferedImage[3];

    private Animation anim;

    public Soldier(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
        super(x, y, id,ss);
        this.handler = handler;
        this.game = game;
        for(int i = 0; i < wizzard_image.length;i++) {
            wizzard_image[i] = ss.grabImage(i+1, 1, 32, 48);
        }

        anim = new Animation(3, wizzard_image[0],wizzard_image[1],wizzard_image[2]);
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
        for(int i = 0; i < handler.object.size(); i++){
            tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Block){
                if(getBounds().intersects(tempObject.getBounds())){
                    x += velX * -1;
                    y += velY * -1;
                }
            }

            if(tempObject.getId() == ID.Crate){
                if(getBounds().intersects(tempObject.getBounds())){
                    game.setAmmo(game.getAmmo()+10);
                    handler.removeObject(tempObject);
                }
            }

            if(tempObject.getId() == ID.Enemy){
                if(getBounds().intersects(tempObject.getBounds())){
                    game.setHp(game.getHp()-1);
                    if(game.getHp() <= 0){
                        handler.removeObject(this);
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        if(velX == 0 && velY == 0) {
            g.drawImage(wizzard_image[0], x, y, null);
        }else{
            anim.drawAnimation(g,x,y,0);
        }

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32,48);
    }
}
