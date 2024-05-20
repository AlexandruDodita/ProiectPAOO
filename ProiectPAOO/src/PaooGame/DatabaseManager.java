package PaooGame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*Clasa DatabaseManager momentan nu functioneaza conform asteptarilor
* Pare sa fie separata complet fata de database-ul din extensia intellij-ului
* Ramane sa fie implementata pentru incarcarea unor atribute mai tarziu.
* Scopul initial era incarcarea hartii.
* */
public class DatabaseManager {
    public static Connection connect(){
        Connection con=null;
        try{
            Class.forName("org.sqlite.JDBC");
            con=DriverManager.getConnection("jdbc:sqlite:C:/Users/alexd/IdeaProjects/ProiectPAOO/database.db");
            System.out.println("Connected!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return con;
    }
}
