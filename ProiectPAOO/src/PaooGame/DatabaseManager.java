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
    private static final String DB_URL = "jdbc:sqlite:identifier.sqlite";

    // Connect to the database
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Insert a tile into the database
    public void insertTile(int x, int y, String type) {
        String sql = "INSERT INTO tiles(x, y, type) VALUES(?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, x);
            pstmt.setInt(2, y);
            pstmt.setString(3, type);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
