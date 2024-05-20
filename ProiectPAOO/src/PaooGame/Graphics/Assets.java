package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{

    public static BufferedImage woodBox;
    public static BufferedImage floorOne;
    public static BufferedImage background;
    public static SpriteSheet playerIdle;
    public static SpriteSheet playerRunLeft;
    public static SpriteSheet playerRunRight;
    public static BufferedImage wallOne;
    public static BufferedImage gate1;
    public static BufferedImage gate2;



    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
      //  SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/PaooGameSpriteSheet.png"));
        playerIdle=new SpriteSheet(ImageLoader.LoadImage("/textures/playerIdle.png"));
        playerRunLeft=new SpriteSheet(ImageLoader.LoadImage("/textures/playerRunLeft.png"));
        playerRunRight=new SpriteSheet(ImageLoader.LoadImage("/textures/playerRunRight.png"));
        //SpriteSheet props=new SpriteSheet(ImageLoader.LoadImage("/textures/Props.png"));
        SpriteSheet floor=new SpriteSheet(ImageLoader.LoadImage("/textures/floorTiles.png"));
        SpriteSheet walls = new SpriteSheet(ImageLoader.LoadImage("/textures/walltile.png"));
        SpriteSheet gate=new SpriteSheet(ImageLoader.LoadImage("/textures/gate.png"));

        //woodBox=props.crop(2,0);
        floorOne=floor.cropS1(0,0);
        wallOne=walls.cropS1(0,0);
        gate1=gate.cropS1(0,0);
        gate2=gate.cropS1(1,0);
        background=ImageLoader.LoadImage("/textures/background.png"); //background-ul e temporar
        MapBuilder.mapBuilder();
    }
}
