package sk.patrik.oop.game;

import java.awt.*;
import java.awt.image.BufferedImage;
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