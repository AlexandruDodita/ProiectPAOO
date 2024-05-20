package PaooGame.Tiles;

import java.awt.image.BufferedImage;

public class Gate implements TileFactory{
    private int id;
    private SolidState state=SolidState.NOT_SOLID;

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
        return null;
    }

    public void modifyState(SolidState newState){
        state=newState;
    }
}
