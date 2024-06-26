package PaooGame.Entity;

import PaooGame.Game;
import PaooGame.GameWindow.GameWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.Assets.*;
import static PaooGame.Graphics.MapBuilder.g1;
import static PaooGame.Graphics.MapBuilder.g2;

import PaooGame.Graphics.Camera;
import PaooGame.Graphics.MapBuilder;
import PaooGame.Graphics.SpriteSheet.*; //probleme cu folosirea import-urilor
import PaooGame.Tiles.*;
import PaooGame.Graphics.MapBuilder.*;

import javax.swing.*;

public class Player{
    private int x, y;
    private int speed = 7;
    private BufferedImage[] idleFrames;
    private BufferedImage[] moveLeftFrames;
    private BufferedImage[] moveRightFrames;
    private final int nrIdleFrames = 6;
    private int nrMoveRightFrames = 6;
    private int nrMoveLeftFrames = 6;
    private static int Health=100;
    private static final int frameWidth = 133;
    private static final int CollisionWidth=70; //pentru a rezolva problema cu aparenta coliziunii
    private static final int frameHeight = 71;
    private static int CameraX=0,CameraY=0;
    private int holdX,holdY;
    private int currentFrame;

    private int frameDelay;

    private int frameDelayCounter;

    private boolean isIdle;
    private int groundLevel;
    private boolean isMovingLeft;
    private boolean isMovingRight;
    private boolean isMovingUp;
    private boolean isMovingDown;
    private boolean discovered=false;
    public boolean CollisionOn;
    private Entity friendly;




    public Player() {

        idleFrames = new BufferedImage[6];
        moveLeftFrames = new BufferedImage[6];
        moveRightFrames = new BufferedImage[6];

        for (int i = 0; i < nrIdleFrames; i++) {
            idleFrames[i] = playerIdle.cropMainChar(i, 0, frameWidth, frameHeight);
        }
        for (int i = 0; i < nrMoveLeftFrames; i++) {
            moveLeftFrames[i] = playerRunLeft.cropMainChar(i, 0, frameWidth, frameHeight);
        }
        for (int i = 0; i < nrMoveRightFrames; i++) {
            moveRightFrames[i] = playerRunRight.cropMainChar(i, 0, frameWidth, frameHeight);
        }


        currentFrame = 0; //frame-urile incep de la 0
        frameDelay = 5;     //cu un delay de 5 frame-uri
        frameDelayCounter = 0;

        isIdle = true;
        isMovingLeft = false;
        isMovingRight = false;


        x = 40;
        y = 900;

    }

    public void update(Entity e) { //construit pentru a trata coliziunile, momentan nu functioneaza conform asteptarilor

        //portiune cod actualizarea animatiei
        //actualizare idle
        if (isIdle) {
            frameDelayCounter++;
            if (frameDelayCounter >= frameDelay) {
                frameDelayCounter = 0;
                currentFrame = (currentFrame + 1) % nrIdleFrames;
            }
        }
        // actualizare run
        else if (isMovingRight) {
            frameDelayCounter++;
            if (frameDelayCounter >= frameDelay) {
                frameDelayCounter = 0;
                currentFrame = (currentFrame + 1) % nrMoveRightFrames;
            }
        }else if (isMovingLeft) {
            frameDelayCounter++;
            if (frameDelayCounter >= frameDelay) {
                frameDelayCounter = 0;
                currentFrame = (currentFrame + 1) % nrMoveLeftFrames;
            }
        }


        if ((isMovingLeft || isMovingRight || isMovingDown || isMovingUp) && checkXCollision(MapBuilder.map,e)) {
            CollisionOn=true;
            this.stopRunning();
        }
    }


    public void render(Graphics g) {
        // System.out.println("Render - currentFrame: " + currentFrame); //debug pentru ca frame-urile totale depaseau frame-urile de pe ecran
        // Desenare player pe ecran
        // verificare ca frame-ul curent sa nu treaca out of bounds
        if (isIdle && currentFrame >= 0 && currentFrame < idleFrames.length) {
            g.drawImage(idleFrames[currentFrame], x, y, null);
        } else if (isMovingLeft && currentFrame >= 0 && currentFrame < moveLeftFrames.length) {
            g.drawImage(moveLeftFrames[currentFrame], x, y, null);
        } else if ((isMovingRight||isMovingUp||isMovingDown) && currentFrame >= 0 && currentFrame < moveRightFrames.length) {
            g.drawImage(moveRightFrames[currentFrame], x, y, null);
        }
        /*g.setColor(Color.GREEN);
        g.drawRect(x+20, y, CollisionWidth, frameHeight); //Folosit pentru a vedea hitbox-ul player-ului. Trebuie folosit in paralel cu echivalentul din MapBuilder
         */
    }

    private boolean isOnTileAxis() { //nefolosit inca
        // Verificam dacă personajul se află pe un tile pe axa X
        return x % Tile.TILE_WIDTH == 0;
    }

    public boolean checkXCollision(TileFactory[][] map, Entity enemy) {
        Rectangle playerBounds = new Rectangle(x + 20, y, CollisionWidth, frameHeight);

        if (isMovingLeft) {
            playerBounds = new Rectangle(x - speed + 20, y, CollisionWidth, frameHeight);
        } else if (isMovingRight) {
            playerBounds = new Rectangle(x + speed + 20, y, CollisionWidth, frameHeight);
        }

        Rectangle enemyBounds = null;
        if (enemy != null) {
            enemyBounds = new Rectangle(enemy.getX(), enemy.getY(), 128, 128);
        }

        Rectangle friendlyBounds = null;
        if ((friendly = Game.getFriendly()) != null) {
            friendlyBounds = new Rectangle(friendly.getX(), friendly.getY(), 128, 128);
        }

        Graphics g = Game.getGraphicalContext();
        if (g == null)
            System.out.println("Ai un milion pana luni?");

        for (int i = 0; i < MapBuilder.mapWidth; i++) {
            for (int j = 0; j < MapBuilder.mapHeight; j++) {
                if ((map[i][j] == g1 || map[i][j] == g2) ) {
                   // System.out.println("DEBUG: gate hitbox are: " + i * 65 + " " + j * 67);
                    holdX = i * 65;
                    holdY = j * 67;
                    discovered = true;
                }

                if ((map[i][j] == null || map[i][j].getSolidState() == SolidState.NOT_SOLID) && (map[i][j] != g1 && map[i][j] != g2))
                    continue;

                Rectangle tileBounds = new Rectangle(i * 65, j * 67, 65, 67);
                if (playerBounds.intersects(tileBounds)) {
                    if ((tileBounds.getX() == holdX && tileBounds.getY() == holdY)) {
                        Game.state = Game.GAME_STATE.MENU;
                        return false;
                    }
                    System.out.println("Collision detected with tile at x=" + i * 65 + " y=" + j * 67);
                    if (isMovingLeft) {
                        stopRunning();
                        moveRight(null);
                        return true;
                    } else if (isMovingRight) {
                        stopRunning();
                        moveLeft(null);
                        return true;
                    } else if (isMovingUp) {
                        stopRunning();
                        moveDown(null);
                        return true;
                    } else if (isMovingDown) {
                        stopRunning();
                        moveUp(null);
                        return true;
                    }
                } else if (enemyBounds != null && playerBounds.intersects(enemyBounds)) {
                    Game.state = Game.GAME_STATE.FIGHT_SCENE;
                    modifyPlayerCamera(0, 0);
                }


            }
        }
        if (friendlyBounds != null && playerBounds.intersects(friendlyBounds)) {
             double playerX = friendlyBounds.getX();
             double playerY = friendlyBounds.getY();

            // Use SwingUtilities.invokeLater to ensure the drawing happens on the EDT
//            SwingUtilities.invokeLater(() -> {
//                //System.out.println("DEBUG: Calling drawMessage");
//
//            });
            Game.drawMessage(playerX, playerY);

        }
        CollisionOn = false;
        return false;
    }



    private BufferedImage getCurrentAnimationFrame() //obine cadrul curent al animatiei, nefolosit
    {
        if (isMovingRight && currentFrame >= 0 && currentFrame < moveRightFrames.length) {
            return moveRightFrames[currentFrame];
        } else if (isMovingLeft && currentFrame >= 0 && currentFrame < moveLeftFrames.length) {
            return moveLeftFrames[currentFrame];
        } else if (isIdle && currentFrame >= 0 && currentFrame < idleFrames.length) {
            return idleFrames[currentFrame];
        } else {
            // tratare eroare eventuala
            return null;
        }

    }

    //miscari stanga, dreapta, trebuie revizuit
    public void moveLeft(Graphics g)
    {
        x = x - speed;
        isIdle = false;
        isMovingLeft = true;
        isMovingRight=false;
        isMovingUp=false;
        isMovingDown=false;
        CameraX-=5;
        //Camera.setX(CameraX);
//        if(g!=null)
//            Camera.moveCamera(g);
    }

    public void moveRight(Graphics g)
    {
        x = x + speed;
        isIdle = false;
        isMovingLeft=false;
        isMovingRight = true;
        isMovingUp=false;
        isMovingDown=false;
        CameraX+=5;
        //Camera.setX(CameraX);
//        if(g!=null)
//            Camera.moveCamera(g);
    }
    public void moveUp(Graphics g){
        y=y-speed;
        isIdle=false;
        isMovingRight=false;
        isMovingLeft=false;
        isMovingUp=true;
        isMovingDown=false;
        CameraY-=5;
        //Camera.setY(CameraY);
//        if(g!=null)
//            Camera.moveCamera(g);
    }
    public void moveDown(Graphics g){
        y=y+speed;
        isIdle=false;
        isMovingLeft=false;
        isMovingRight=false;
        isMovingUp=false;
        isMovingDown=true;
        CameraY+=5;
        //Camera.setY(CameraY);
//        if(g!=null)
//            Camera.moveCamera(g);
    }
    public void stopRunning()
    {
        isMovingLeft = false;
        isMovingRight=false;
        isMovingUp=false;
        isMovingDown=false;
        isIdle = true;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void setX(int newX){
        x=newX;
    }

    public void setY(int newY){
        y=newY;
    }

    public int getHealth(){
        return Health;
    }
    public static void setHealth(int newHealth){
        Health=newHealth;
    }

    public static void modifyPlayerCamera(int x,int y){ //redunant momentan
        Player invis=new Player();
//        invis.setX(x);
//        invis.setY(y);


    }
}
