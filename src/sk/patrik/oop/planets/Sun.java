package sk.patrik.oop.planets;


import javafx.animation.Animation;
import sk.patrik.oop.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Sun extends GameObject {
    private SpriteSheet sunSpriteSheet;
    private BufferedImage sunAnimation;
    public Sun(int x, int y, ID id, String path) {
        super(x, y, id, path);
        BufferedImageLoader loader = new BufferedImageLoader();
        sunSpriteSheet = new SpriteSheet(loader.loadImage(path));
        sunAnimation = sunSpriteSheet.grabImage(1,1,32,32);
    }


    public int getSunX(){
        return x;
    }

    public int getSunY(){
        return y;
    }
    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sunAnimation,x,y,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }
}
