package sk.patrik.oop.planets;

import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.GameObject;
import sk.patrik.oop.game.ID;
import sk.patrik.oop.game.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Moon extends Planet {
    private int radiusOfOrbit;
    private GameObject sun;
    private double speed, z = 0;
    public Moon(int x, int y, ID id, String path, String name, double speed, GameObject sun) {
        super(x,y,id,path,name,speed,sun);
        this.sun = sun;
        this.speed = speed;
        radiusOfOrbit = calculateDistanceBetweenPoints(sun.getX(),sun.getY(),x,y);
    }

    public void tick() {
        x = (int) (sun.getX() + radiusOfOrbit * Math.cos(Math.toRadians(z)));
        y = (int) (sun.getY() + (radiusOfOrbit + 20) * Math.sin(Math.toRadians(z)));
        z = z + speed;
        if(z >= 360) {
            z=0;
        }
    }
}
