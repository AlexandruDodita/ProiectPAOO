package PaooGame.Tiles;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.Assets.gate2;

public class Gate2 extends Gate{
    private final BufferedImage image=gate2;

    @Override
    public BufferedImage getImage(){
        return image;
    }
}