package PaooGame;

import PaooGame.GameWindow.GameWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.Assets.*;

import PaooGame.Graphics.SpriteSheet.*; //probleme cu folosirea import-urilor
import PaooGame.Tiles.*;

public class Player {
    private int x, y; // pozitia personajului - nefolosita momentan
    private int speed = 7;
    private BufferedImage[] idleFrames;
    private BufferedImage[] moveLeftFrames;
    private BufferedImage[] moveRightFrames;
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

    public void update(Tile[] tiles) { //construit pentru a trata coliziunile, momentan nu functioneaza conform asteptarilor

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


        if (isMovingLeft || isMovingRight) {
            checkXCollision(tiles); // Verificăm coliziunile pe axa X când personajul se mișcă
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
    public boolean checkCollision(Tile[] tiles) {
        Rectangle playerBounds = new Rectangle(x, y, frameWidth, frameHeight);
        System.out.println("Player bounds: " + playerBounds);

        for (Tile tile : tiles) {
            if (!tile.IsSolid()) {
                continue;
            }

            Rectangle tileBounds = new Rectangle(tile.GetId() * Tile.TILE_WIDTH, 0, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
            System.out.println("Tile bounds: " + tileBounds);

            if (playerBounds.intersects(tileBounds)) {
                System.out.println("Collision detected with tile " + tile.GetId());
                if (tileBounds.getX() == x) { // Verificam dacă coliziunea se produce pe axa X
                    return true; // Returnam true doar dacă coliziunea se produce pe axa X
                }
            }
        }

        return false;
    }

    private boolean isOnTileAxis() { //nefolosit inca
        // Verificam dacă personajul se află pe un tile pe axa X
        return x % Tile.TILE_WIDTH == 0;
    }

    public boolean checkXCollision(Tile[] tiles) {
        Rectangle playerBounds = new Rectangle(x, y, frameWidth, frameHeight);

        if (isMovingLeft) {
            playerBounds = new Rectangle(x - speed, y, frameWidth, frameHeight);
        } else if (isMovingRight) {
            playerBounds = new Rectangle(x + speed, y, frameWidth, frameHeight);
        }
        System.out.println("Player bounds: " + playerBounds);

        for (Tile tile : tiles) {
            if (!tile.IsSolid()) {
                continue;
            }

            Rectangle tileBounds = new Rectangle(tile.GetId() * Tile.TILE_WIDTH, 0, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
            System.out.println("Tile bounds: " + tileBounds);

            if (playerBounds.intersects(tileBounds)) {
                if (isMovingLeft) {
                    return true;
                } else if (isMovingRight) {
                    return true;
                }
            }
        }

        // Dacă nu s-a detectat nicio coliziune, return false
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
    public void moveLeft()
    {
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
