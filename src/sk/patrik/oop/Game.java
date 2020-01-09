package sk.patrik.oop;

import sk.patrik.oop.game.Window;
import sk.patrik.oop.game.*;
import sk.patrik.oop.game.inputs.KeyInput;
import sk.patrik.oop.game.inputs.MouseInput;
import sk.patrik.oop.game.objects.GameObject;
import sk.patrik.oop.game.objects.enemies.BasicEnemy;
import sk.patrik.oop.game.objects.enemies.SmartEnemy;
import sk.patrik.oop.game.objects.players.Asteroid;
import sk.patrik.oop.game.objects.players.Player;
import sk.patrik.oop.game.objects.players.Soldier;
import sk.patrik.oop.game.objects.players.SpaceShip;
import sk.patrik.oop.game.objects.spacebodies.OrbitingSpaceBody;
import sk.patrik.oop.game.objects.spacebodies.StaticSpaceBody;
import sk.patrik.oop.game.objects.usable.Ammo;
import sk.patrik.oop.game.objects.usable.FirstAid;
import sk.patrik.oop.game.objects.walls.Block;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Main class
 */
public class Game extends Canvas implements Runnable {
    private static final String LEVEL2_IMAGE_PATH = "/someLevel.png";
    private static final String LEVEL1_IMAGE_PATH = "/newSolarSystem.png";
    private static final String LEVEL1_BACKGROUND_PATH = "/stars.png";
    private static final String LEVEL2_BACKGROUND_PATH = "/sprite_sheet.png";
    private static final String SOLDIER_IMAGE_PATH = "/player.png";
    private static final String ENEMY_IMAGE_PATH = "/sprite_sheet.png";
    private static final String BOX_IMAGE_PATH = "/box.png";

    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private Camera camera;
    private BufferedImage level2;
    private BufferedImage floor = null;
    private SpriteSheet ss;
    private Soldier soldier;
    private SpriteSheet starAnimation;
    private StaticSpaceBody sun;
    private static int level;

    /**
     * Constructor for game object
     */
    private Game() {
        new Window(1000, 563, "SolarSystem", this);
        start();
        level = 1;
        handler = new Handler();
        camera = new Camera(0, 0);
        BufferedImageLoader loader = new BufferedImageLoader();
        level2 = loader.loadImage(LEVEL2_IMAGE_PATH);
        BufferedImage level1 = loader.loadImage(LEVEL1_IMAGE_PATH);
        starAnimation = new SpriteSheet(loader.loadImage(LEVEL1_BACKGROUND_PATH));
        ss = new SpriteSheet(loader.loadImage(LEVEL2_BACKGROUND_PATH));
        loadLevel(level1, level, handler);
    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.requestFocus();
        long now;
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while (isRunning) {
            now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            render();

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }
        stop();
    }

    public void tick() {

        for (int i = 0; i < handler.getObjects().size(); i++) {
            if (handler.getObjects().get(i) instanceof Player) {
                camera.tick(handler.getObjects().get(i));
            }
        }
        for (int i = 0; i < handler.getObjects().size(); i++) {
            if (handler.getObjects().get(i) instanceof SpaceShip) {
                camera.tick(handler.getObjects().get(i));
                if (SpaceShip.getEnd()) {
                    SpaceShip.setEnd(false);
                    handler = new Handler();
                    level = 2;
                    loadLevel(level2, level, handler);
                }
            }
        }

        handler.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        /////////////////////////////////////////

        g2d.translate(-camera.getX(), -camera.getY());

        for (int xx = 0; xx < 30 * 72; xx += 32) {
            for (int yy = 0; yy < 30 * 72; yy += 32) {
                g.drawImage(floor, xx, yy, null);
            }
        }

        handler.render(g);
        g2d.translate(camera.getX(), camera.getY());

        if (level == 2) {
            //**********health bar********************
            g.setColor(Color.GRAY);
            g.fillRect(5, 5, 200, 32);
            g.setColor(Color.GREEN);
            g.fillRect(5, 5, soldier.getHp() * 2, 32);
            g.setColor(Color.black);
            g.drawRect(5, 5, 200, 32);

            //***************ammo***************
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.BOLD, 15));
            g.drawString("Ammo : " + soldier.getAmmo(), 5, 50);

            //*****************game over*****************

            if (Soldier.isEnd()) {
                g.setColor(Color.RED);
                g.setFont(new Font("TimesRoman", Font.BOLD, 50));
                g.drawString("GAME OVER!", 300, 300);
            }
        }
        if (level == 1) {
            g.setColor(Color.RED);
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g.drawString("Destroy the Earth!", 5, 50);
        }

        //////////////////////////////////////////
        g.dispose();
        bs.show();
    }


    public static int getLevel() {
        return level;
    }

    //***********************************************
    //loading level

    /**
     * Method that loads level
     *
     * @param image   level image
     * @param level   level number
     * @param handler asd asd
     */
    private void loadLevel(BufferedImage image, int level, Handler handler) {

        //private int ammo;
        int pixel;
        int red;
        int blue;
        int green;
        if (level == 2) {
            int w = image.getWidth();
            int h = image.getHeight();
            floor = ss.grabImage(4, 2, 32, 32);

            for (int xx = 0; xx < w; xx++) {
                for (int yy = 0; yy < h; yy++) {
                    pixel = image.getRGB(xx, yy);
                    red = (pixel >> 16) & 0xff;
                    green = (pixel >> 8) & 0xff;
                    blue = (pixel) & 0xff;

                    if (red == 255 && blue == 0 && green == 0)
                        handler.addObject(new Block(xx * 32, yy * 32, null));

                    if (blue == 255 && green == 0 && red == 0) {
                        soldier = new Soldier(xx * 32, yy * 32, SOLDIER_IMAGE_PATH, 100, 20);
                        handler.addObject(soldier);
                        this.addMouseListener(new MouseInput(handler, camera, soldier));
                    }

                    if (green == 255 && blue == 0 && red == 0)
                        handler.addObject(new BasicEnemy(xx * 32, yy * 32, ENEMY_IMAGE_PATH, 100));

                    if (green == 255 && blue == 255 && red == 255)
                        handler.addObject(new SmartEnemy(xx * 32, yy * 32, ENEMY_IMAGE_PATH, 200));

                    if (green == 255 && blue == 255 && red == 0)
                        handler.addObject(new Ammo(xx * 32, yy * 32, BOX_IMAGE_PATH, Color.ORANGE, 100));

                    if (red == 255 && green == 255 && blue == 0)
                        handler.addObject(new FirstAid(xx * 32, yy * 32, BOX_IMAGE_PATH, Color.WHITE, 100));
                }
            }
        }
        if (level == 1) {
            int w = image.getWidth();
            int h = image.getHeight();


            floor = starAnimation.grabImage(1, 1, 32, 32);  //load background from sprite_sheet

            for (int xx = 0; xx < w; xx++) {
                for (int yy = 0; yy < h; yy++) {
                    pixel = image.getRGB(xx, yy);
                    red = (pixel >> 16) & 0xff;
                    green = (pixel >> 8) & 0xff;
                    blue = (pixel) & 0xff;

                    if (red == 255 && blue == 0 && green == 0) {
                        sun = new StaticSpaceBody(xx * 32, yy * 32, "/sun.png");
                        handler.addObject(sun);
                    }

                    if (red == 0 && blue == 0 && green == 255)
                        handler.addObject(new OrbitingSpaceBody(xx * 32, yy * 32, "/mercury.png", "Mercury", 0.1, sun));

                    if (red == 0 && blue == 255 && green == 0)
                        handler.addObject(new OrbitingSpaceBody(xx * 32, yy * 32, "/venus.png", "Venus", 0.4, sun));

                    if (red == 0 && blue == 255 && green == 255) {
                        GameObject earth = new OrbitingSpaceBody(xx * 32, yy * 32, "/earth.png", "Earth", 0.5, sun);
                        handler.addObject(earth);
                        handler.addObject(new OrbitingSpaceBody((xx * 32) + 10, yy * 32, "/venus.png", "Moon", 1, earth));
                    }

                    if (red == 255 && blue == 255 && green == 255)
                        handler.addObject(new OrbitingSpaceBody(xx * 32, yy * 32, "/mars.png", "Mars", 0.75, sun));

                    if (red == 255 && blue == 255 && green == 0)
                        handler.addObject(new OrbitingSpaceBody(xx * 32, yy * 32, "/jupiter.png", "Jupiter", 1, sun));

                    if (red == 255 && blue == 0 && green == 255)
                        handler.addObject(new OrbitingSpaceBody(xx * 32, yy * 32, "/saturn.png", "Saturn", 0.55, sun));

                    if (red == 255 && blue == 0 && green == 128)
                        handler.addObject(new OrbitingSpaceBody(xx * 32, yy * 32, "/uranus.png", "Uranus", 0.59, sun));

                    if (red == 255 && blue == 128 && green == 128)
                        handler.addObject(new OrbitingSpaceBody(xx * 32, yy * 32, "/neptune.png", "Neptune", 1, sun));

                    if (red == 128 && blue == 128 && green == 128)
                        handler.addObject(new Asteroid(xx * 32, yy * 32, "/asteroid.png"));
                }
            }
        }
        this.addKeyListener(new KeyInput(handler));

    }

    public static void main(String[] args) {
        new Game();
    }


}
