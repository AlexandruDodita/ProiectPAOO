package PaooGame.Graphics;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

import PaooGame.Entity.Entity;
import PaooGame.Game;
import PaooGame.Tiles.*;
import PaooGame.DatabaseManager;
import org.w3c.dom.html.HTMLFieldSetElement;

import java.awt.*;

public class MapBuilder {
    final public static int mapWidth = 64; //trebuie fatait un pic sa se vada exact under se termina , pare a fi o greseala de calcul?
    final public static int mapHeight = 64;
    private static Entity ent;
    private static Entity ent2;
    private static Entity friendlyMap;
    public static TileFactory  map[][]= new TileFactory[mapWidth][mapHeight];
    public static void  draw(Graphics g){
        for(int i=0;i<mapWidth;i++){
            for(int j=0;j<mapHeight;j++){
                if(map[i][j]!=null)
                    g.drawImage(map[i][j].getImage(),i*65,j*67,65,67,null);

                /*g.setColor(Color.RED);
                g.drawRect(i * 65, j * 67, 65, 67); //Folosit pentru a vedea hitbox-ul unui tile. A fi folosit in parelel cu echivalentul din Player.java
                 */
            }
        }
        switch(Game.state) {
            case LEVEL_ONE:
                if(ent!=null) {
                    ent.setX(800);
                    ent.setY(500);
                    friendlyMap=null;
                    ent2=null;
                    //System.out.println("ent2 " + ent2.getX() + " " + ent2.getY());
                }
                break;
            case LEVEL_TWO:
                if(ent!=null) {
                    ent.setX(2100);
                    ent.setY(1175);
                    if(ent2!=null) {
                        ent2.setX(1000);
                        ent2.setY(700);
                    }
                    friendlyMap=null;
                }
                break;
            case LEVEL_THREE:
                if(ent!=null) {
                    ent.setX(2000);
                    ent.setY(1170);
                    if(friendlyMap!=null) {
                        friendlyMap.setX(1000);
                        friendlyMap.setY(700);
                    }

                }
                break;
        }
    }
    public static StoneFloor sf=new StoneFloor(0);
    public static StoneWall sw = new StoneWall(1);
    public static Gate1 g1=new Gate1();
    public static Gate2 g2=new Gate2();
    public static FieldsTile1 ft1=new FieldsTile1(2);
    public static GrassTile grass=new GrassTile();
    public static BuildingWall wall=new BuildingWall();
    public static DecorA decorA=new DecorA();
    public static DecorB decorB=new DecorB();
    public static void  mapBuilder(){
        //DatabaseManager dbManager = new DatabaseManager();
//        for(int i=0;i<mapWidth;i++){
//            for(int j=0;j<mapHeight;j++){
//                map[i][j]=w;
//                //dbManager.insertTile(i, j, map[i][j].getClass().getSimpleName());
//            }
//        }
        String filePath="ProiectPAOO/res/Maps/Map1.txt";
        String filePath2="ProiectPAOO/res/Maps/Map2.txt";
        String filePath3="ProiectPAOO/res/Maps/Map3.txt";
        Scanner scanner = null;
        try {
            switch(Game.state) {
                case LEVEL_ONE:
                    scanner = new Scanner(new File(filePath));
                    break;
                case LEVEL_TWO:
                    scanner = new Scanner(new File(filePath2));
                    break;
                case LEVEL_THREE:
                    scanner = new Scanner(new File(filePath3));
                    break;
            }
            for (int i = 0; i < mapHeight-1; i++) {

                    String line = scanner.nextLine();
                    String[] values = line.split(" ");
                    for (int j = 0; j < mapWidth-1; j++) {
                        int x = Integer.parseInt(values[j]);
                        switch (x) {
                            case 1:
                                map[j][i] = sf;
                                break;
                            case 2:
                                map[j][i] = sw;
                                break;
                            case 3:
                                map[j][i]=g1;
                                break;
                            case 4:
                                map[j][i]=g2;
                                break;
                            case 5:
                                map[j][i]=ft1;
                                break;
                            case 6:
                                map[j][i]=grass;
                                break;
                            case 7:
                                map[j][i]=wall;
                                break;
                            case 8:
                                map[j][i]=decorA;
                                break;
                            case 9:
                                map[j][i]=decorB;
                                break;
                            default:
                                map[j][i] = null;
                                break;
                        }
                    }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        ent=Game.getEntity();
        if(ent==null){
            ent=new Entity(Entity.EntityType.ENEMY,100);
        }
        ent2=Game.getEntity2();
        if(ent2==null){
            ent2=new Entity(Entity.EntityType.ENEMY,100);
        }
        friendlyMap=Game.getFriendly();
        if(friendlyMap==null){
            friendlyMap=new Entity(Entity.EntityType.FRIENDLY,100);
        }
    }
}
