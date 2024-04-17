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
    private double gravity = 0.5;
    private BufferedImage[] idleFrames; // animatii stare idle
    private BufferedImage[] runFrames; // animatii stare run
    private BufferedImage[] jumpFrames; // animatii jump
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
    private boolean isFalling;

    private int groundLevel;


    public Player() {
        //frame-urile pentru idle sunt 6, run 8 si jump 12

        idleFrames = new BufferedImage[6];
        runFrames = new BufferedImage[8];
        jumpFrames = new BufferedImage[12];

        for (int i = 0; i < 6; i++) {
            idleFrames[i] = playerIdle.cropMainChar(i, 0, frameWidth, frameHeight);
        }
        for (int i = 0; i < 8; i++) {
            runFrames[i] = playerRunLeft.cropMainChar(i, 0, frameWidth, frameHeight);
        }
        for (int i = 0; i < 12; i++) {
            jumpFrames[i] = playerRunRight.cropMainChar(i, 0, frameWidth, frameHeight);
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
        } else if (isRunning && currentFrame >= 0 && currentFrame < runFrames.length) {
            g.drawImage(runFrames[currentFrame], x, y, null);
        } else if (isJumping && currentFrame >= 0 && currentFrame < jumpFrames.length) {
            g.drawImage(jumpFrames[currentFrame], x, y, null);
        }
    }

    private BufferedImage getCurrentAnimationFrame()
    {
        if (isJumping && currentFrame >= 0 && currentFrame < jumpFrames.length) {
            return jumpFrames[currentFrame];
        } else if (isRunning && currentFrame >= 0 && currentFrame < runFrames.length) {
            return runFrames[currentFrame];
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

    public void jump()
    {
        //System.out.println("Jump - isJumping: " + isJumping + ", isRunning: " + isRunning); //folosit pt debug, animatia de jump nu mergea deloc
        if (!isJumping && !isRunning) {
            // Dacă nu este deja în aer și nu este în mișcare orizontală
            isIdle = false; // Personajul nu mai este în stare de idle
            isRunning = false; // Nu este în stare de running
            isJumping = true; // Este în stare de jump
            y -= jumpSpeed; // Modific poziția y pentru a indica că personajul sare în sus
        }
        if (currentFrame == 0 && isJumping) {
            // Săritura s-a încheiat, aduceți personajul înapoi la sol
            y += jumpSpeed; // Modificați poziția y pentru a aduce personajul înapoi la sol
            isJumping = false; // Personajul nu mai sare
            isIdle = true; // Personajul revine în starea de idle
        }
    }

    public void stopRunning()
    {
        isRunning = false;
        isIdle = true;
    }
}
