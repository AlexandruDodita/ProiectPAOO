package PaooGame;

import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.Assets.*;
import static PaooGame.GameWindow.GameWindow.*;

public class Player {
    private int x, y; // pozitia personajului - nefolosita momentan
    private int speed = 7;
    private int jumpSpeed = 14;
    //private double gravity = 0.5;
    private BufferedImage[] idleFrames; // animatii stare idle
    private BufferedImage[] moveLeftFrames; // animatii stare run
    private BufferedImage[] moveRightFrames; // animatii jump
    private int numberIdleFrames = 6;
    private int numberRunFrames = 8;
    private int numberJumpFrames = 12;

    private static final int frameWidth = 128;
    private static final int frameHeight = 128;
    private int currentFrame; // frame-ul curent din animatie

    private int frameDelay; //intarziere de frame-uri pentru animatii

    private int frameDelayCounter; //contor pentru a numara intarzierea de frame-uri

    private boolean isIdle;
    private boolean isRunning;
    private boolean isJumping;
   // private boolean isFalling;

    private int groundLevel;


    public Player() {
        //frame-urile pentru idle sunt 6, run 8 si jump 12

        idleFrames = new BufferedImage[6];
        moveLeftFrames = new BufferedImage[8];
        moveRightFrames = new BufferedImage[12];

        for (int i = 0; i < 6; i++) {
            idleFrames[i] = playerIdle.cropMainChar(i, 0, frameWidth, frameHeight);
        }
        for (int i = 0; i < 8; i++) {
            moveLeftFrames[i] = playerRunLeft.cropMainChar(i, 0, frameWidth, frameHeight);
        }
        for (int i = 0; i < 12; i++) {
            moveRightFrames[i] = playerRunRight.cropMainChar(i, 0, frameWidth, frameHeight);
        }
        //initializare date

        currentFrame = 0; //frame-urile incep de la 0
        frameDelay = 5;     //cu un delay de 5 frame-uri
        frameDelayCounter = 0;

        isIdle = true;      //personajul la inceput este idle, nu se spawneaza fugind sau sarind
        isRunning = false;
        isJumping = false;

        GameWindow gameWindow = new GameWindow("Joc",1920,1080);
        groundLevel = gameWindow.GetWndHeight() - frameHeight - 50;
        System.out.println("Ground level: " + groundLevel); //debug
        x = 0;
        y = groundLevel;

    }

    public void update() {



        //System.out.println("Update - isIdle: " + isIdle + ", isRunning: " + isRunning + ", isJumping: " + isJumping); //debug pentru ca frame-urile curente depaseau frame-urile totale alocate pe ecran
        //portiune cod actualizarea animatiei
        //actualizare idle
        if (isIdle) {
            frameDelayCounter++;
            if (frameDelayCounter >= frameDelay) {
                frameDelayCounter = 0;
                currentFrame = (currentFrame + 1) % numberIdleFrames;
            }
        }
        // actualizare run
        else if (isRunning) {
            frameDelayCounter++;
            if (frameDelayCounter >= frameDelay) {
                frameDelayCounter = 0;
                currentFrame = (currentFrame + 1) % numberRunFrames;
            }
        }
        // actualizare animatie jump
        else if (isJumping) {
            frameDelayCounter++;
            if (frameDelayCounter >= frameDelay) {
                frameDelayCounter = 0;
                currentFrame = (currentFrame + 1) % numberJumpFrames;
                if (currentFrame == 0) {
                    isJumping = false;
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
        } else if (isRunning && currentFrame >= 0 && currentFrame < moveLeftFrames.length) {
            g.drawImage(moveLeftFrames[currentFrame], x, y, null);
        } else if (isJumping && currentFrame >= 0 && currentFrame < moveRightFrames.length) {
            g.drawImage(moveRightFrames[currentFrame], x, y, null);
        }
    }

    private BufferedImage getCurrentAnimationFrame()
    {
        if (isJumping && currentFrame >= 0 && currentFrame < moveRightFrames.length) {
            return moveRightFrames[currentFrame];
        } else if (isRunning && currentFrame >= 0 && currentFrame < moveLeftFrames.length) {
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
        isRunning = true;
    }

    public void moveRight()
    {
        x = x + speed;
        isIdle = false;
        isRunning = true;
    }

     public void stopRunning()
    {
        isRunning = false;
        isIdle = true;
    }
}
