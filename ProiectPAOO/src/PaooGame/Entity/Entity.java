package PaooGame.Entity;
import PaooGame.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.Assets.EnemyIdle;
import static PaooGame.Graphics.Assets.playerIdle;

public class Entity {
    public enum EntityType{
        FRIENDLY, ENEMY
    }

    private EntityType type;
    private int Health;
    private int x=0, y=0;
    private int centerX = 40, centerY = 20;
    private int movingSpeed=7;
    private BufferedImage[] idleFrames;
    private int currentFrame;
    private boolean displayHealth;
    private int frameDelay;

    private int frameDelayCounter;

    public Entity(EntityType newType, int newHealth, boolean displayHealth){
        type=newType;
        Health=newHealth;
        this.displayHealth=displayHealth;

        if(type==EntityType.ENEMY) {
            idleFrames = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                idleFrames[i] = EnemyIdle.cropMainChar(i, 0, 128 , 128);
            }
        } else if(type==EntityType.FRIENDLY) {
            idleFrames = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                idleFrames[i] = EnemyIdle.cropMainChar(i, 0, 128 , 128);
            }
        }
        currentFrame = 0; //frame-urile incep de la 0
        frameDelay = 3;     //cu un delay de 3 frame-uri
        frameDelayCounter = 0;
    }

    public void update(){
        frameDelayCounter++;
        if (frameDelayCounter >= frameDelay) {
            frameDelayCounter = 0;
            currentFrame = (currentFrame + 1) % 4;
        }
    }

    public void render(Graphics g){
        for(int i=0;i<4;i++){
           // System.out.println("ERROR is here??");
            g.drawImage(idleFrames[currentFrame],x,y,null);
        }
        /*g.setColor(Color.GREEN);
        g.drawRect(x+20, y, CollisionWidth, frameHeight); //Folosit pentru a vedea hitbox-ul player-ului. Trebuie folosit in paralel cu echivalentul din MapBuilder
         */

        if(displayHealth){
            g.setColor(Color.BLACK);
            g.drawRect(x+centerX-1, y+centerY-1, getHealth(), 6);
            g.setColor(Color.RED);
            g.fillRect(x+centerX,y+centerY, getHealth(), 5);
        }
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
    public void setHealth(int newHealth){
        Health=newHealth;
    }

}