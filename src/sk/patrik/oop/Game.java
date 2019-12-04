package sk.patrik.oop;

import sk.patrik.oop.enemies.Enemy;
import sk.patrik.oop.enemies.SmartEnemy;
import sk.patrik.oop.game.*;
import sk.patrik.oop.game.Window;
import sk.patrik.oop.planets.Asteroid;
import sk.patrik.oop.planets.Moon;
import sk.patrik.oop.planets.Planet;
import sk.patrik.oop.planets.Sun;
import sk.patrik.oop.players.Soldier;
import sk.patrik.oop.players.SpaceShip;
import sk.patrik.oop.usable.Ammo;
import sk.patrik.oop.usable.FirstAid;
import sk.patrik.oop.walls.Block;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private Camera camera;
    private BufferedImage level2 = null;
    private BufferedImage level1 = null;
    private BufferedImage floor = null;
    private SpriteSheet ss;
    private Soldier soldier;
    private SpriteSheet starAnimation;
    private Sun sun;
    private static int level;

    //private int ammo;
    private int pixel, red, blue, green;

    //private int hp = 100;



    public Game(){
        new Window(1000,563,"SolarSystem",this);
        start();
        level = 1;
        handler = new Handler();
        camera = new Camera(0,0);
        BufferedImageLoader loader = new BufferedImageLoader();
        level2 = loader.loadImage("/someLevel.png");
        level1 = loader.loadImage("/newSolarSystem.png");
        starAnimation = new SpriteSheet(loader.loadImage("/stars.png"));
        ss = new SpriteSheet(loader.loadImage("/sprite_sheet.png"));
        loadLevel(level1, level ,handler);
    }

    private void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop(){
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
        int frames = 0;
        while (isRunning) {
            now = System.nanoTime();
            delta += (now - lastTime) /ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }
    public void tick(){

        for(int i = 0; i < handler.getObject().size(); i++){
            if(handler.getObject().get(i).getId() == ID.Player){
                camera.tick(handler.getObject().get(i));
            }
        }
        for(int i = 0; i < handler.getObject().size(); i++){
            if(handler.getObject().get(i).getId() == ID.SpaceShip){
                camera.tick(handler.getObject().get(i));
                if(SpaceShip.getEnd()){
                    SpaceShip.setEnd(false);
                    handler = null;
                    handler = new Handler();
                    level = 2;
                    loadLevel(level2,level,handler);

                }

            }
        }

        handler.tick();
    }
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        /////////////////////////////////////////

        g2d.translate(-camera.getX(), -camera.getY());

        for(int xx = 0; xx< 30*72;xx+=32){
            for(int yy = 0; yy< 30*72;yy+=32){
                g.drawImage(floor, xx, yy, null);
            }
        }

        handler.render(g);
        g2d.translate(camera.getX(), camera.getY());

        if(level == 2) {
            //**********health bar********************
            g.setColor(Color.GRAY);
            g.fillRect(5, 5, 200, 32);
            g.setColor(Color.GREEN);
            g.fillRect(5, 5, soldier.getHp() * 2, 32);
            g.setColor(Color.black);
            g.drawRect(5, 5, 200, 32);

            //***************ammo***************
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.BOLD,15));
            g.drawString("Ammo : " + soldier.getAmmo(), 5, 50);

            //*****************game over*****************

            if(Soldier.isEnd()){
                g.setColor(Color.RED);
                g.setFont(new Font("TimesRoman", Font.BOLD,50));
                g.drawString("GAME OVER!", 300,300);
            }
        }
        if(level == 1){
            g.setColor(Color.RED);
            g.setFont(new Font("TimesRoman", Font.BOLD,20));
            g.drawString("Destroy the Earth!", 5, 50);
        }

        //////////////////////////////////////////
        g.dispose();
        bs.show();
    }





    public static int getLevel(){
        return level;
    }

    //***********************************************
    //loading level
    public void loadLevel (BufferedImage image, int level, Handler handler){

        if(level == 2){
            int w = image.getWidth();
            int h = image.getHeight();
            floor = ss.grabImage(4,2,32,32);

            for(int xx = 0; xx < w; xx++) {
                for (int yy = 0; yy < h; yy++) {
                    pixel = image.getRGB(xx, yy);
                    red = (pixel >> 16) & 0xff;
                    green = (pixel >> 8) & 0xff;
                    blue = (pixel) & 0xff;

                    if (red == 255 && blue == 0 && green == 0)
                        handler.addObject(new Block(xx * 32, yy * 32, ID.Block, null));

                    if (blue == 255 && green == 0 && red == 0) {
                        soldier = new Soldier(xx * 32, yy * 32, ID.Player, handler, "/player.png", 100, 20);
                        handler.addObject(soldier);
                        this.addMouseListener(new MouseInput(handler, camera, soldier));
                    }

                    if (green == 255 && blue == 0 && red == 0)
                        handler.addObject(new Enemy(xx * 32, yy * 32, ID.Enemy, handler, "/sprite_sheet.png",100));

                    if (green == 255 && blue == 255 && red == 255)
                        handler.addObject(new SmartEnemy(xx * 32, yy * 32, ID.Enemy, handler, "/sprite_sheet.png",200));

                    if (green == 255 && blue == 255 && red == 0)
                        handler.addObject(new Ammo(xx * 32, yy * 32, ID.Ammo, "/box.png",Color.ORANGE, 100));

                    if (red == 255 && green == 255 && blue == 0)
                        handler.addObject(new FirstAid(xx * 32, yy * 32, ID.FirstAid, "/box.png",Color.WHITE, 100));
                }
            }
        }
        if(level == 1){
            int w = image.getWidth();
            int h = image.getHeight();


            floor = starAnimation.grabImage(1,1,32,32);  //background

            for(int xx = 0; xx < w; xx++) {
                for (int yy = 0; yy < h; yy++) {
                    pixel = image.getRGB(xx, yy);
                    red = (pixel >> 16) & 0xff;
                    green = (pixel >> 8) & 0xff;
                    blue = (pixel) & 0xff;

                    if (red == 255 && blue == 0 && green == 0) {
                        sun = new Sun(xx * 32, yy * 32, ID.Star, "/sun.png");
                        handler.addObject(sun);
                    }

                    if (red == 0 && blue == 0 && green == 255)
                        handler.addObject(new Planet(xx * 32, yy * 32, ID.Planet, "/mercury.png","Mercury",0.1,sun));

                    if (red == 0 && blue == 255 && green == 0)
                        handler.addObject(new Planet(xx * 32, yy * 32, ID.Planet, "/venus.png", "Venus",0.4,sun));

                    if (red == 0 && blue == 255 && green == 255) {
                        GameObject earth = new Planet(xx * 32, yy * 32, ID.Earth, "/earth.png", "Earth", 0.5, sun);
                        handler.addObject(earth);
                        handler.addObject(new Moon((xx*32)+10, yy*32, ID.Planet,"/venus.png", "Moon",1,earth));
                    }

                    if (red == 255 && blue == 255 && green == 255)
                        handler.addObject(new Planet(xx * 32, yy * 32, ID.Planet, "/mars.png","Mars",0.75,sun));

                    if (red == 255 && blue == 255 && green == 0)
                        handler.addObject(new Planet(xx * 32, yy * 32, ID.Planet, "/jupiter.png","Jupiter",1,sun));

                    if (red == 255 && blue == 0 && green == 255)
                        handler.addObject(new Planet(xx * 32, yy * 32, ID.Planet, "/saturn.png","Saturn",0.55,sun));

                    if (red == 255 && blue == 0 && green == 128)
                        handler.addObject(new Planet(xx * 32, yy * 32, ID.Planet, "/uranus.png","Uranus",0.59,sun));

                    if (red == 255 && blue == 128 && green == 128)
                        handler.addObject(new Planet(xx * 32, yy * 32, ID.Planet, "/neptune.png","Neptune",1,sun));

                    if (red == 128 && blue == 128 && green == 128)
                        handler.addObject(new Asteroid(xx * 32, yy * 32, ID.Player, "/asteroid.png",handler));
                }
            }
        }
        this.addKeyListener(new KeyInput(handler));

    }

    public static void main(String[] args) {
        new Game();
    }


}
