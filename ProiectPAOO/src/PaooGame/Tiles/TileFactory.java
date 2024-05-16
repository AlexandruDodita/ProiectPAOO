package PaooGame.Tiles;

import java.awt.image.BufferedImage;

/*Bazat pe Sablonul de Proiectare Flyweight*/

public interface TileFactory {
    public void setID(int newID);
    public SolidState getSolidState();
    public int getID();
    public BufferedImage getImage();

}

