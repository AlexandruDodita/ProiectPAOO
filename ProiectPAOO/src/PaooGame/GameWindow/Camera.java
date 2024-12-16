package PaooGame.GameWindow;

import PaooGame.Entity.Player;

public class Camera {
    private float x, y;
    private int screenWidth, screenHeight;
    private int worldWidth, worldHeight;

    public Camera(int screenWidth, int screenHeight, int worldWidth, int worldHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void setX(float x) {
        this.x = clamp(x, 0, worldWidth - screenWidth);
    }

    public void setY(float y) {
        this.y = clamp(y, 0, worldHeight - screenHeight);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    private float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public void centerOnEntity(Player e) {
        float targetX = e.getX() - (screenWidth - e.getTextureWidth()) / 2.0f;
        float targetY = e.getY() - (screenHeight - e.getTextureHeight()) / 2.0f;
        setX(targetX);
        setY(targetY);
    }
    public void backToZero(){
        setX(0);
        setY(0);
    }
}

