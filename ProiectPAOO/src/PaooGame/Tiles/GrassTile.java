package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

import java.awt.image.BufferedImage;

import static PaooGame.Graphics.Assets.grassTile;

public class GrassTile implements TileFactory {
    private int id;
    private final BufferedImage texture=grassTile;
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
        return 0;
    }

    @Override
    public BufferedImage getImage() {
        return texture;
    }
}