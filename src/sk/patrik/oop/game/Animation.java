package sk.patrik.oop.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.List;

public class Animation {

    private int speed;
    private List<BufferedImage> images;
    private int index = 0;
    private int currentImgIndex = 0;
    private BufferedImage currentImg;

    public Animation(int speed, List<BufferedImage> images) {
        this.speed = speed;
        this.images = images;
    }

    public void runAnimation() {
        index++;
        if (index > speed) {
            index = 0;
            nextFrame();
        }
    }

    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {

        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, (BufferedImageOp) this, 0,0);
        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();

        return rotated;
    }

    public void nextFrame() {
        currentImgIndex++;
        if (currentImgIndex >= images.size()) {
            currentImgIndex = 0;
        }
        currentImg = images.get(currentImgIndex);
    }

    public void drawAnimation(Graphics g, double x, double y, int offset) {
        g.drawImage(currentImg, (int) x - offset, (int) y, null);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCurrentImgIndex() {
        return currentImgIndex;
    }

    public void setCurrentImgIndex(int currentImgIndex) {
        this.currentImgIndex = currentImgIndex;
    }
}