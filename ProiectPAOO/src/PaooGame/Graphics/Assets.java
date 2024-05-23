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
    public static BufferedImage battleBackground;
    public static SpriteSheet playerIdle;
    public static SpriteSheet playerRunLeft;
    public static SpriteSheet playerRunRight;
    public static SpriteSheet EnemyIdle;
    public static BufferedImage wallOne;
    public static BufferedImage gate1;
    public static BufferedImage gate2;

    public static BufferedImage fieldTile1;
    public static BufferedImage grassTile;


    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
      //  SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/PaooGameSpriteSheet.png"));
        playerIdle=new SpriteSheet(ImageLoader.LoadImage("/textures/EntitySpritesheets/playerIdle.png"));
        playerRunLeft=new SpriteSheet(ImageLoader.LoadImage("/textures/EntitySpritesheets/playerRunLeft.png"));
        playerRunRight=new SpriteSheet(ImageLoader.LoadImage("/textures/EntitySpritesheets/playerRunRight.png"));
        EnemyIdle=new SpriteSheet(ImageLoader.LoadImage("/textures/EntitySpritesheets/Idle.png"));
        //SpriteSheet props=new SpriteSheet(ImageLoader.LoadImage("/textures/Props.png"));
        SpriteSheet floor=new SpriteSheet(ImageLoader.LoadImage("/textures/tiles/floorTiles.png"));
        SpriteSheet walls = new SpriteSheet(ImageLoader.LoadImage("/textures/tiles/walltile.png"));
        SpriteSheet gate=new SpriteSheet(ImageLoader.LoadImage("/textures/tiles/gate.png"));

        //woodBox=props.crop(2,0);
        floorOne=floor.cropS1(0,0);
        wallOne=walls.cropS1(0,0);
        gate1=gate.cropS1(0,0);
        gate2=gate.cropS1(1,0);
        background=ImageLoader.LoadImage("/textures/backgrounds/background.png");
        battleBackground=ImageLoader.LoadImage("/textures/backgrounds/Battleground2.png");
        fieldTile1=ImageLoader.LoadImage("/textures/tiles/FieldsCollection/FieldsTile_01.png");
        grassTile=ImageLoader.LoadImage("/textures/tiles/Ground_Tile_01_B.png");
    }
}
