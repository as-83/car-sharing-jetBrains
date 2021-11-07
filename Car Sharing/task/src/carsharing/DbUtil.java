package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    private static Connection connection = null;
    private static String databaseFilePath = "./src/carsharing/db/";//jdbc:h2:./src/carsharing/db/carsharing

    public Connection getConnection(String databaseFileName) {
        if (connection != null) {
            return connection;
        }
        try {
            databaseFilePath = databaseFilePath + databaseFileName;
            connection = DriverManager.getConnection("jdbc:h2:" + databaseFilePath);
        } catch (SQLException se) {
            System.out.println("Connection getting error");
            se.printStackTrace();
        }
        System.out.println("Connection OK");
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection CLOSED");
            } catch (SQLException se) {
                se.printStackTrace();
            }
            connection = null;
        }
    }
}
