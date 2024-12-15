package PaooGame.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

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
    public static SpriteSheet playerRun;
    public static SpriteSheet playerAttackSword;
    public static SpriteSheet EnemyIdle;
    public static BufferedImage wallOne;
    public static BufferedImage gate1;
    public static BufferedImage gate2;

    public static BufferedImage fieldTile1;
    public static BufferedImage grassTile;

    public static BufferedImage wallTwo;
    public static BufferedImage decorA;
    public static BufferedImage decorB;


    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void buildCharacter(int width, int height, String characterPath, String weaponPath, String productName){
        try {
            DynamicAssetBuilder builder = new DynamicAssetBuilder(width, height);
            builder.addLayer(characterPath);
            builder.addLayer(weaponPath);

            builder.buildAsset("/textures/EntitySpritesheets/playerAnim/", productName); //the path where it goes to is irrelevant,
            //as it is saved in the bytecode area of the project and thus visibile to the compiler when trying to access it above.

            System.out.println("Character image build successfully!");
        }catch (IOException e){
            System.err.println("Error loading/saving image: " + e.getMessage());
        }catch(IllegalArgumentException e){
            System.err.println("Invalid layer dimensions: " + e.getMessage());
        }
    }
    public static void Init()
    {
        buildCharacter(64,256,"/textures/EntitySpritesheets/playerAnim/charVersionOne/idle.png","/textures/EntitySpritesheets/playerAnim/weapons/idleLongswordDefault.png", "idleDefault.png");
        buildCharacter(512,256, "/textures/EntitySpritesheets/playerAnim/charVersionOne/walk.png","/textures/EntitySpritesheets/playerAnim/weapons/walkLongswordDefault.png", "walkDefault.png");

            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
      //  SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/PaooGameSpriteSheet.png"));
        playerIdle=new SpriteSheet(ImageLoader.LoadImage("/textures/EntitySpritesheets/playerAnim/idleDefault.png"));
        playerRun=new SpriteSheet(ImageLoader.LoadImage("/textures/EntitySpritesheets/playerAnim/walkDefault.png"));
        playerAttackSword=new SpriteSheet(ImageLoader.LoadImage("/textures/EntitySpritesheets/playerAnim/charVersionOne/slash.png"));
      //  playerRunRight=new SpriteSheet(ImageLoader.LoadImage("/textures/EntitySpritesheets/playerAnim/playerRunRight.png"));
        EnemyIdle=new SpriteSheet(ImageLoader.LoadImage("/textures/EntitySpritesheets/otherAnim/Idle.png"));
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
        wallTwo= ImageLoader.LoadImage("/textures/tiles/decor/Building_B_01.png");
        decorA=ImageLoader.LoadImage("/textures/tiles/decor/Decor_Tile_A_05.png");
        decorB=ImageLoader.LoadImage("/textures/tiles/decor/Decor_Tile_B_05.png");


        //used for testing the new dynamic character/asset builder

    }
}
