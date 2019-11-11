package sk.patrik.oop;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Bullet extends GameObject {
    private Handler handler;
    private GameObject tempObject;

    public Bullet(int x, int y, ID id,Handler handler, int mx, int my,String path) {
        super(x, y, id,path);
        this.handler = handler;

        velX = (mx - x) / 10;
        velY = (my - y) / 10;
    }

    public void tick() {
        x += velX;
        y += velY;
        for(int i = 0; i < handler.getObject().size(); i++){
            tempObject = handler.getObject().get(i);

            if(tempObject.getId() == ID.Block){
                if(getBounds().intersects(tempObject.getBounds())){
                    handler.removeObject(this);
                }
            }

        }

    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x,y,8,8);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
    }
}
