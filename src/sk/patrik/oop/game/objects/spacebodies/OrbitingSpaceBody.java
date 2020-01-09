package sk.patrik.oop.game.objects.spacebodies;

import sk.patrik.oop.game.BufferedImageLoader;
import sk.patrik.oop.game.SpriteSheet;
import sk.patrik.oop.game.objects.AbstractGameObject;
import sk.patrik.oop.game.objects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OrbitingSpaceBody extends AbstractGameObject implements SpaceBody {
    private int radiusOfOrbit;
    private String name;
    private GameObject centerOfGravity;
    private double speed, z = 0.0;
    private BufferedImage planetAnimation;

    public OrbitingSpaceBody(int x, int y, String path, String name, double speed, GameObject centerOfGravity) {
        super(x, y, path);
        this.name = name;
        this.centerOfGravity = centerOfGravity;
        this.speed = speed;
        radiusOfOrbit = calculateDistanceBetweenPoints(centerOfGravity.getX(), centerOfGravity.getY(), x, y);
        BufferedImageLoader loader = new BufferedImageLoader();
        SpriteSheet planetSpriteSheet = new SpriteSheet(loader.loadImage(path));
        planetAnimation = planetSpriteSheet.grabImage(1, 1, 32, 32);
    }


    public int calculateDistanceBetweenPoints(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public String getName() {
        return name;
    }

    public void tick() {

        x = (int) (centerOfGravity.getX() + radiusOfOrbit * Math.cos(Math.toRadians(z)));
        y = (int) (centerOfGravity.getY() + (radiusOfOrbit + 20) * Math.sin(Math.toRadians(z)));
        z = z + speed;
        if (z >= 360) {
            z = 0;
        }
    }

    public void render(Graphics g) {
        //Draw orbit
        g.setColor(Color.white);
        g.drawOval(centerOfGravity.getX() + 16 - radiusOfOrbit, centerOfGravity.getY() + 16 - (radiusOfOrbit + 20), radiusOfOrbit * 2, (radiusOfOrbit + 20) * 2);

        //Draw planet
        g.drawImage(planetAnimation, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
