package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

import java.awt.image.BufferedImage;

import static PaooGame.Graphics.Assets.wallOne;

//momentan folosit pentru verificarea/testarea coliziunilor
//posibil a fi folosit mai tarziu pentru construirea hartii
public class invisTile implements TileFactory {
    private int id;
    final private SolidState state=SolidState.SOLID;
    final private BufferedImage texture = wallOne;
    public invisTile(int newID){
        id=newID;
    }
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
