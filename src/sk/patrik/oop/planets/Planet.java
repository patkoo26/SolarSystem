package sk.patrik.oop.planets;

import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.GameObject;
import sk.patrik.oop.game.ID;
import sk.patrik.oop.game.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Planet extends GameObject {
    private int radiusOfOrbit;
    private String name;
    private GameObject sun;
    private double speed, z =0.0;
    private SpriteSheet planetSpriteSheet;
    private BufferedImage planetAnimation;

    public Planet(int x, int y, ID id, String path, String name,double speed, GameObject sun) {
        super(x, y, id, path);
        this.name = name;
        this.sun = sun;
        this.speed = speed;
        BufferedImageLoader loader = new BufferedImageLoader();
        planetSpriteSheet = new SpriteSheet(loader.loadImage(path));
        planetAnimation = planetSpriteSheet.grabImage(1,1,32,32);
        radiusOfOrbit = calculateDistanceBetweenPoints(sun.getX(),sun.getY(),x,y);
    }


    public int calculateDistanceBetweenPoints(int x1, int y1, int x2, int y2) {
        return (int)Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public String getName(){
        return name;
    }

    public void tick() {

        x = (int) (sun.getX() + radiusOfOrbit * Math.cos(Math.toRadians(z)));
        y = (int) (sun.getY() + (radiusOfOrbit + 20) * Math.sin(Math.toRadians(z)));
        z = z+speed;
        if(z >= 360) {
            z=0;
        }
    }

    public void render(Graphics g) {
        //Draw orbit
        g.setColor(Color.white);
        g.drawOval(sun.getX()+16 - radiusOfOrbit, sun.getY()+16 - (radiusOfOrbit+20), radiusOfOrbit * 2, (radiusOfOrbit+20) * 2);

        //Draw planet
        g.drawImage(planetAnimation,x,y,null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }
}
