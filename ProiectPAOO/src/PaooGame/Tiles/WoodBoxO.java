package PaooGame.Tiles;



import PaooGame.Graphics.Assets;


public class WoodBoxO extends Tile
{

    public WoodBoxO(int id)
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