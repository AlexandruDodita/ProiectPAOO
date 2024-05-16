package PaooGame.Graphics;

import PaooGame.Tiles.StoneFloor;
import PaooGame.Tiles.TileFactory;

import java.awt.*;

public class MapBuilder {
    final private static int mapWidth = 45; //trebuie fatait un pic sa se vada exact under se termina , pare a fi o greseala de calcul?
    final private static int mapHeight = 39;
    private static StoneFloor  map[][]= new StoneFloor[mapWidth][mapHeight]; //tip StoneFloor e temporar, trebuie inlocuit cu o extindere a lor pentru a functiona.
    public static void  draw(Graphics g){
        for(int i=0;i<mapWidth;i++){
            for(int j=0;j<mapHeight;j++){
                g.drawImage(map[i][j].getImage(),i*65,j*67,65,67,null);
            }
        }
    }
    public static void  mapBuilder(){
        int k=0;
        for(int i=0;i<mapWidth;i++){
            for(int j=0;j<mapHeight;j++){
                StoneFloor s=new StoneFloor(k++);
                map[i][j]=s;
            }
        }
    }
}
