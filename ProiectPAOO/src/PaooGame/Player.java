package PaooGame;

import PaooGame.GameWindow.GameWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.Assets.*;

import PaooGame.Graphics.SpriteSheet.*;


public class Player {
    private int x, y; // pozitia personajului - nefolosita momentan
    private int speed = 7;
    //private double gravity = 0.5;
    private BufferedImage[] idleFrames; // animatii stare idle
    private BufferedImage[] moveLeftFrames; // animatii stare run
    private BufferedImage[] moveRightFrames; // animatii jump
    private int nrIdleFrames = 6;
    private int nrMoveRightFrames = 6;
    private int nrMoveLeftFrames = 6;

    private static final int frameWidth = 64;
    private static final int frameHeight = 34;
    private int currentFrame; // frame-ul curent din animatie

    private int frameDelay; //intarziere de frame-uri pentru animatii

    private int frameDelayCounter; //contor pentru a numara intarzierea de frame-uri

    private boolean isIdle;
    private boolean isMovingLeft;
    private boolean isMovingRight;


    private int groundLevel;


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


    }

    public void update() {




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
        else if (isMovingLeft) {
            frameDelayCounter++;
            if (frameDelayCounter >= frameDelay) {
                frameDelayCounter = 0;
                currentFrame = (currentFrame + 1) % nrMoveRightFrames;
            }
        }
        // actualizare animatie jump
        else if (isMovingRight) {
            frameDelayCounter++;
            if (frameDelayCounter >= frameDelay) {
                frameDelayCounter = 0;
                currentFrame = (currentFrame + 1) % nrMoveLeftFrames;
                if (currentFrame == 0) {
                    isMovingRight = false;
                    isIdle = true;
                }
            }
        }



    }

    public void render(Graphics g) {
        //System.out.println("Render - currentFrame: " + currentFrame); //debug pentru ca frame-urile totale depaseau frame-urile de pe ecran
        // Desenare player pe ecran
        // verificare ca frame-ul curent sa nu treaca out of bounds
        if (isIdle && currentFrame >= 0 && currentFrame < idleFrames.length) {
            g.drawImage(idleFrames[currentFrame], x, y, null);
        } else if (isMovingLeft && currentFrame >= 0 && currentFrame < moveLeftFrames.length) {
            g.drawImage(moveLeftFrames[currentFrame], x, y, null);
        } else if (isMovingRight && currentFrame >= 0 && currentFrame < moveRightFrames.length) {
            g.drawImage(moveRightFrames[currentFrame], x, y, null);
        }
    }

    private BufferedImage getCurrentAnimationFrame()
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

    public void moveLeft()
    {
        //implementare miscare stanga
        x = x - speed;
        isIdle = false;
        isMovingLeft = true;
        isMovingRight=false;
    }

    public void moveRight()
    {
        x = x + speed;
        isIdle = false;
        isMovingLeft=false;
        isMovingRight = true;
    }

     public void stopRunning()
    {
        isMovingLeft = false;
        isMovingRight=false;
        isIdle = true;
    }
}
