package sk.patrik.oop.planets;

import sk.patrik.oop.game.*;
import sk.patrik.oop.players.SpaceShip;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Asteroid extends GameObject {
    private SpriteSheet asteroidSpriteSheet;
    private BufferedImage asteroidAnimation;
    private GameObject tempObject;

    private Handler handler;
    public Asteroid(int x, int y, ID id, String path, Handler handler) {
        super(x, y, id, path);
        this.handler = handler;
        BufferedImageLoader loader = new BufferedImageLoader();
        asteroidSpriteSheet = new SpriteSheet(loader.loadImage(path));
        asteroidAnimation = asteroidSpriteSheet.grabImage(1,1,32,32);
    }

    @Override
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
    }

    private void collision() {
        for (int i = 0; i < handler.getObject().size(); i++) {
            tempObject = handler.getObject().get(i);
            if (tempObject.getId() == ID.Earth) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.addObject(new SpaceShip(tempObject.getX(),tempObject.getY(),ID.SpaceShip,"/spaceShip.png"));
                    handler.removeObject(tempObject);
                    handler.removeObject(this);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(asteroidAnimation,x,y,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);

    }
}
