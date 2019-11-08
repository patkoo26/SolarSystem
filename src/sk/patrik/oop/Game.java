package sk.patrik.oop;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private Camera camera;
    private BufferedImage level = null;
    private BufferedImage sprite_sheet = null;
    private BufferedImage floor = null;
    private SpriteSheet ss;

    private int ammo;
    private int pixel, red, blue, green;

    private int hp = 100;



    public Game(){
        new Window(1000,563,"SolarSytem",this);
        start();
        setAmmo(10);
        handler = new Handler();
        camera = new Camera(0,0);
        this.addKeyListener(new KeyInput(handler));


        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/someLevel.png");
        sprite_sheet = loader.loadImage("/sprite_sheet.png");
        ss = new SpriteSheet(sprite_sheet);

        floor = ss.grabImage(4,2,32,32);

        this.addMouseListener(new MouseInput(handler, camera, this,ss));
        loadLevel(level);

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
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
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

        for(int i = 0; i < handler.object.size(); i++){
            if(handler.object.get(i).getId() == ID.Player){
                camera.tick(handler.object.get(i));
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

        //**********health bar********************
        g.setColor(Color.GRAY);
        g.fillRect(5,5,200,32);
        g.setColor(Color.GREEN);
        g.fillRect(5,5,hp*2,32);
        g.setColor(Color.black);
        g.drawRect(5,5,200,32);

        //***************ammo bar***************
        g.setColor(Color.white);
        g.drawString("Ammo : " + getAmmo(),5,50);

        if(getHp() <= 0){

        }

        //////////////////////////////////////////
        g.dispose();
        bs.show();
    }


    //************************************ammo***********************

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    //***********************************************

    //********************health*****************
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
    //***********************************************
    //loading level
    private void loadLevel (BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();

        for(int xx = 0; xx < w; xx++){
            for(int yy = 0; yy < h; yy++){
                pixel = image.getRGB(xx, yy);
                red = (pixel >> 16) & 0xff;
                green = (pixel >> 8) & 0xff;
                blue = (pixel) & 0xff;

                if(red == 255)
                    handler.addObject(new Block(xx*32, yy*32, ID.Block,ss));

                if(blue == 255 && green == 0)
                    handler.addObject(new Soldier(xx*32, yy*32,ID.Player, handler,this,ss));

                if(green == 255 && blue == 0)
                    handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handler,ss));

                if(green == 255 && blue == 255)
                    handler.addObject(new Crate(xx*32, yy*32,ID.Crate,ss));
            }
        }
    }

    public static void main(String[] args) {
        new Game();
    }


}
