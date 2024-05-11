package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

//momentan folosit pentru verificarea/testarea coliziunilor
//posibil a fi folosit mai tarziu pentru construirea hartii
public class invisTile extends Tile {


    public invisTile(int id)
    {
        /// Apel al constructorului clasei de baza
        super(Assets.woodBox, id);
    }

}
