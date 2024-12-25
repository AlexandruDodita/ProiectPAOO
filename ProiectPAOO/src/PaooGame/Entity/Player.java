package PaooGame.Entity;

import PaooGame.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.Assets.*;
import static PaooGame.Graphics.MapBuilder.g1;
import static PaooGame.Graphics.MapBuilder.g2;

import PaooGame.Graphics.MapBuilder;
import PaooGame.Tiles.*;

public class Player {
    private int x, y;
    private final int speed = 7;
    private static BufferedImage[][] idleFrames;
    private static BufferedImage[][] moveFrames;
    private static BufferedImage[][] attackFrames;

    private final int nrIdleFrames = 1;
    private final int directions = 4;
    private final int nrRunFrames = 8;
    private final int nrAttackFrames = 6;
    private static int Health=100;
    private static final int frameWidth = 64;
    private static final int CollisionWidth=30; //pentru a rezolva problema cu aparenta coliziunii
    private static final int frameHeight = 64;
    private static int CameraX=0,CameraY=0;
    private int holdX,holdY;
    private int currentFrame;

    private static final int frameAttackWidth=192;
    private static final int frameAttackHeight=192;

    private int frameDelay;

    private int frameDelayCounter;

    private boolean isIdle;
    private boolean isMovingLeft;
    private boolean isMovingRight;
    private boolean isMovingUp;
    private boolean isMovingDown;
    private static boolean isAttacking;
    public boolean CollisionOn;
    private static int lastDirection=3; //0 - up, 1 - left, 2 - down, 3 - right
    private Entity friendly;
    private static boolean enemyCollision;
    private static Entity enemy;


    public Player() {

        idleFrames = new BufferedImage[directions][nrIdleFrames];
        moveFrames = new BufferedImage[directions][nrRunFrames];
        attackFrames = new BufferedImage[directions][nrAttackFrames];

        for(int i=0;i<directions;i++) {
            idleFrames[i]= new BufferedImage[nrIdleFrames];
            moveFrames[i] = new BufferedImage[nrRunFrames];
            attackFrames[i] = new BufferedImage[nrAttackFrames];
        }

        updatePlayerTextures();

        currentFrame = 0; //frame-urile incep de la 0
        frameDelay = 5;     //cu un delay de 5 frame-uri
        frameDelayCounter = 0;

        isIdle = true;
        isMovingLeft = false;
        isMovingRight = false;
        isMovingUp = false;
        isMovingDown = false;
        isAttacking = false;


        x = 80;
        y = 900;

    }

    public void updatePlayerTextures(){
        //System.out.println("We updated the player textures");
        loadPlayerAnim();
        for(int j=0;j<directions;j++) {
            for (int i = 0; i < nrIdleFrames; i++) {
                idleFrames[j][i] = playerIdle.cropMainChar(i, j, frameWidth, frameHeight);
            }
            for(int i = 0; i < nrRunFrames; i++) {
                moveFrames[j][i]=playerRun.cropMainChar(i, j, frameWidth, frameHeight);
            }
            for(int i=0;i< nrAttackFrames;i++){
                attackFrames[j][i]=playerAttackSword.cropMainChar(i, j, frameAttackWidth, frameAttackHeight);
            }
        }
    }

    public void update() { //construit pentru a trata coliziunile, momentan nu functioneaza conform asteptarilor

        //portiune cod actualizarea animatiei
        //actualizare idle
        if (isIdle && !isAttacking) {
            frameDelayCounter++;
            if (frameDelayCounter >= frameDelay) {
                frameDelayCounter = 0;
                currentFrame = (currentFrame + 1) % nrIdleFrames;
            }
        }
        // actualizare run
        else if ((isMovingRight || isMovingUp || isMovingDown || isMovingLeft) && !isAttacking) {
            frameDelayCounter++;
            if (frameDelayCounter >= frameDelay) {
                frameDelayCounter = 0;
                currentFrame = (currentFrame + 1) % nrRunFrames;
            }
            if(checkXCollision(MapBuilder.map)){
                CollisionOn=true;
                this.stopRunning();
            }
        }else if(isAttacking){
            frameDelayCounter++;
            if (frameDelayCounter >= frameDelay && currentFrame<nrAttackFrames-1) {
                frameDelayCounter = 0;
              //  System.out.println(currentFrame);
                currentFrame++;

            }else if(currentFrame==nrAttackFrames-1){
                isAttacking=false;
                //System.out.println("Final frame" + currentFrame);
                currentFrame=0;

            }
            if(checkXCollision(MapBuilder.map)){
                CollisionOn=true;
                this.stopRunning();
            }
        }


    }


    public void render(Graphics g) {
        // System.out.println("Render - currentFrame: " + currentFrame); //debug pentru ca frame-urile totale depaseau frame-urile de pe ecran
        // Desenare player pe ecran
        // verificare ca frame-ul curent sa nu treaca out of bounds
        if (!isAttacking && (isIdle && currentFrame >= 0 && currentFrame < idleFrames[lastDirection].length)) {
            g.drawImage(idleFrames[lastDirection][currentFrame], x, y, null);
        } else if (!isAttacking && ((isMovingUp || isMovingDown || isMovingLeft || isMovingRight) && currentFrame >= 0 && currentFrame < moveFrames[lastDirection].length)) {
            g.drawImage(moveFrames[lastDirection][currentFrame], x, y, null);
        }else if(isAttacking && currentFrame>=0 && currentFrame<attackFrames[lastDirection].length){
            g.drawImage(attackFrames[lastDirection][currentFrame], x-64, y-64, null);
        }
       // g.setColor(Color.GREEN);
       // g.drawRect(x+20, y, CollisionWidth, frameHeight); //Folosit pentru a vedea hitbox-ul player-ului. Trebuie folosit in paralel cu echivalentul din MapBuilder

    }

    private boolean isOnTileAxis() { //nefolosit inca
        // Verificam dacă personajul se află pe un tile pe axa X
        return x % Tile.TILE_WIDTH == 0;
    }

    public boolean checkXCollision(TileFactory[][] map) {
        Rectangle playerBounds = new Rectangle(x + 20, y, CollisionWidth, frameHeight);

        if (isMovingLeft) {
            playerBounds = new Rectangle(x - speed + 20, y, CollisionWidth, frameHeight);
        } else if (isMovingRight) {
            playerBounds = new Rectangle(x + speed + 20, y, CollisionWidth, frameHeight);
        }

        Rectangle enemyBounds = null;
        if (enemy != null) {
            enemyBounds = new Rectangle(enemy.getX(), enemy.getY(), 256, 256); //bigger collision width for the enemy to simulate reach for the attack
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
                        isIdle=true;
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
                    enemyCollision = true;
                }else{
                    enemyCollision=false;
                }


            }
        }
        if (friendlyBounds != null && playerBounds.intersects(friendlyBounds)) {
             double playerX = friendlyBounds.getX();
             double playerY = friendlyBounds.getY();
             Game.drawMessage(playerX, playerY);
        }
        CollisionOn = false;
        return false;
    }


    //miscari stanga, dreapta, trebuie revizuit
    public void moveLeft(Graphics g)
    {
        lastDirection = 1;
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
        lastDirection = 3;

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
        lastDirection = 0;

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

        lastDirection = 2;

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
        if(isMovingUp){
            lastDirection=0;
        }else if(isMovingDown){
            lastDirection=2;
        }else if(isMovingLeft){
            lastDirection=1;
        }else if(isMovingRight){
            lastDirection=3;
        }
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

    public BufferedImage[][] getIdleFrames(){ return idleFrames; } //used to show character changes in-real-time in CHAR_CREATION state.
                                                                            //Will not work if there is no Player instance!!!

    public int getTextureWidth(){
        return frameWidth;
    }
    public int getTextureHeight(){
        return frameHeight;
    }

    public static void setAttackingState(boolean attacking){
        isAttacking = attacking;
    }
    public static boolean getAttackingState(){
        return isAttacking;
    }

    public static void setEnemy(Entity e){
        enemy=e;
    }
    public static Entity getEnemy(){
        return enemy;
    }
    public static boolean getEnemyCollision(){
        return enemyCollision;
    }
    public void setCurrentFrame(int newFrame){
        currentFrame=newFrame;
    }
}
