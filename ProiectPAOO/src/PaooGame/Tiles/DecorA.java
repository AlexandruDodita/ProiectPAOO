package PaooGame.Tiles;

import java.awt.image.BufferedImage;
import PaooGame.Graphics.Assets;

import static PaooGame.Graphics.Assets.decorA;

public class DecorA implements TileFactory {
    int id;
    private final SolidState state=SolidState.NOT_SOLID;
    private final BufferedImage image=decorA;
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
