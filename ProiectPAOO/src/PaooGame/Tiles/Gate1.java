package PaooGame.Tiles;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.Assets.gate1;

public class Gate1 extends Gate{
    private final BufferedImage image=gate1;

    @Override
    public BufferedImage getImage(){
        return image;
    }
}
