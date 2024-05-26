package PaooGame.Tiles;

import java.awt.image.BufferedImage;
import PaooGame.Graphics.Assets;


public class BuildingWall implements TileFactory{
    private int id;
    private final SolidState state=SolidState.SOLID;
    private final BufferedImage image=Assets.wallTwo;
    @Override
    public void setID(int newID) {
        id=newID;
    }

    @Override
    public SolidState getSolidState() {
        return state;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }
}
