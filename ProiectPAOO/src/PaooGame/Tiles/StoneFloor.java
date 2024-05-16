package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

import java.awt.image.BufferedImage;
public class StoneFloor implements TileFactory{
    private int id;
    final private SolidState state=SolidState.NOT_SOLID;
    final private BufferedImage texture= Assets.floorOne;
    public StoneFloor(int newID){
        id=newID;
    }
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
    public BufferedImage getImage(){
        return texture;
    }
}
