package carsharing;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    private static DbUtil db = new DbUtil();

    public static void main(String[] args) {
        String databaseFileName = "carsharing";
        if (args.length != 0 && args[0].equals("-databaseFileName")) {
            databaseFileName = args[1];
        }
        Connection connection = db.getConnection(databaseFileName);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS COMPANY(" +
                    "id INTEGER PRIMARY KEY," +
                    "name VARCHAR NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
    }
}
