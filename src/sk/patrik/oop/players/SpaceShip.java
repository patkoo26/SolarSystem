package sk.patrik.oop.players;

import sk.patrik.oop.game.ID;
import sk.patrik.oop.game.SpriteSheet;
import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpaceShip extends GameObject {
    private SpriteSheet spaceShipSpriteSheet;
    private BufferedImage spaceShipAnimation;
    private int startX;
    private static Boolean isEnd = false;

    public SpaceShip(int x, int y, ID id, String path) {
        super(x, y, id, path);
        startX = x;
        BufferedImageLoader loader = new BufferedImageLoader();
        spaceShipSpriteSheet = new SpriteSheet(loader.loadImage(path));
        spaceShipAnimation = spaceShipSpriteSheet.grabImage(1,1,32,32);
    }

    public void tick() {
        x +=4;
        if(startX + 1200 == x){
            setEnd(true);
        }
    }

    public void render(Graphics g) {
        g.drawImage(spaceShipAnimation,x,y,null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }
    public static Boolean getEnd() {
        return isEnd;
    }

    public static void setEnd(Boolean end) {
        isEnd = end;
    }
}
