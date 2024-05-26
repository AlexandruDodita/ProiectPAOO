package PaooGame.Tiles;

import java.awt.image.BufferedImage;
import PaooGame.Graphics.Assets;

import static PaooGame.Graphics.Assets.decorB;

public class DecorB implements TileFactory {
    int id;
    private final SolidState state=SolidState.NOT_SOLID;
    private final BufferedImage image=decorB;
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
