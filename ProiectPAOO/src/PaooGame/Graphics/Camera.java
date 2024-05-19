package PaooGame.Graphics;

import java.awt.*;

public class Camera {
    private static int CameraX=0,CameraY=0;
    public static int getX(){
        return CameraX;
    }
    public static int getY(){
        return CameraY;
    }
    public static void setX(int x){
        CameraX=x;
    }
    public static void setY(int y){
        CameraY=y;
    }
    public static void moveCamera(Graphics g){
        g.translate(CameraX,CameraY);
    }
}
