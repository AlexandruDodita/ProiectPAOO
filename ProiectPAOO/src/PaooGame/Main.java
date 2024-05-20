package PaooGame;

import PaooGame.GameWindow.GameWindow;
import org.sqlite.core.DB;

public class Main
{
    public static void main(String[] args)
    {
        Game paooGame = new Game("PaooGame", 1920, 1080);
        //DatabaseManager.connect(); //NU FUNCTIONEAZA, DA ERORI PE CARE NU LE INTELEG
        paooGame.StartGame();
    }
}
