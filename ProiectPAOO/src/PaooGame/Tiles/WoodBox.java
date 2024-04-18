package PaooGame.Tiles;



import PaooGame.Graphics.Assets;


public class WoodBox extends Tile
{

    public WoodBox(int id)
    {
            /// Apel al constructorului clasei de baza
        super(Assets.woodBox, id);
    }

    @Override
    public boolean IsSolid()
    {
        return true;
    }
}