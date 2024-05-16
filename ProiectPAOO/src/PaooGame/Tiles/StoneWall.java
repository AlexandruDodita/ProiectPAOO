package PaooGame.Tiles;

import PaooGame.Tiles.TileFactory;

import java.awt.image.BufferedImage;

public class StoneWall implements TileFactory {
    private int id;
    final private SolidState state=SolidState.SOLID;
    private BufferedImage texture;

    @Override
    public void setID(int newId){
        id=newId;
    }

    @Override
    public SolidState getSolidState(){
        return state;
    }

    @Override
    public int getID(){
        return id;
    }


    @Override
    public BufferedImage getImage(){
        return texture;
    }
}
