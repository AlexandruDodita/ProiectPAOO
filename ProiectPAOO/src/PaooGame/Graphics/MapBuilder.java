package PaooGame.Graphics;

import PaooGame.Tiles.StoneFloor;
import PaooGame.Tiles.StoneWall;
import PaooGame.Tiles.TileFactory;
import PaooGame.DatabaseManager;

import java.awt.*;

public class MapBuilder {
    final public static int mapWidth = 23; //trebuie fatait un pic sa se vada exact under se termina , pare a fi o greseala de calcul?
    final public static int mapHeight = 15;
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
    }
    public static StoneFloor sf=new StoneFloor(0);
    public static StoneWall sw = new StoneWall(1);
    public static void  mapBuilder(){
        //DatabaseManager dbManager = new DatabaseManager();
//        for(int i=0;i<mapWidth;i++){
//            for(int j=0;j<mapHeight;j++){
//                map[i][j]=w;
//                //dbManager.insertTile(i, j, map[i][j].getClass().getSimpleName());
//            }
//        }
        //hallway 1
        for(int i=0;i<2;i++){
            for(int j=11;j<15;j++){
                if(j==11){
                    map[i][j]=sw;
                }else
                    map[i][j]=sf;
            }
        }
        //room 1
        for(int i=2;i<8;i++){
            for(int j=8;j<15;j++){
                if(j==8||i==7||j==14){
                    map[i][j]=sw;
                }else{
                    map[i][j]=sf;
                }
            }
        }
        //extra walls for room 1
        for(int j=8;j<15;j++){
            if(j>=12){
                continue;
            }else{
                map[1][j]=sw;
            }
        }
        //hallway 2
        map[7][10]=sf;
        map[7][11]=sf;
        for(int i=8;i<13;i++){
            for(int j=8;j<13;j++){
                if(j==9||j==12||i==12) {
                    map[i][j] = sw;
                }
                else{
                    map[i][j]=sf;
                }
            }
        }
        map[10][9]=sf;
        map[11][9]=sf;
        //room 2
        for(int i=6;i<16;i++){
            for(int j=0;j<=8;j++){
                if(j==0||i==6||i==15){
                    map[i][j]=sw;
                }else if(j==8 && (i<10||i>11)){
                    map[i][j]=sw;
                }else{
                    map[i][j]=sf;
                }
            }
        }
    }
}
