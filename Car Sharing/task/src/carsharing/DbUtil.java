package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DbUtil {
    private static Connection connection = null;
    private static String databaseFilePath = "./src/carsharing/db/";//jdbc:h2:./src/carsharing/db/carsharing
    private static String databaseFileName = "carsharing";//jdbc:h2:./src/carsharing/db/carsharing
    private static List<String> cars = new ArrayList<>();

    public void setDatabaseFileName(String databaseFileName) {
        DbUtil.databaseFileName = databaseFileName;
    }

    public Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        try {
            connection = DriverManager.getConnection("jdbc:h2:" + databaseFilePath + databaseFileName);
        } catch (SQLException se) {
            System.out.println("Connection getting error");
            se.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            connection = null;
        }
    }

    public void addCompany(String companyTitle) {
        try {
            getConnection().createStatement().execute("INSERT INTO COMPANY (name) " +
                    "VALUES ('" +
                    companyTitle +
                    "');");
            closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<String> getAllCompanies() {
        ResultSet resultSet = null;
        List<String> companies = new ArrayList<>();
        try {
            resultSet = getConnection().createStatement().executeQuery("select * from COMPANY " +
                    "ORDER BY ID");

            while(resultSet.next()) {
                companies.add(resultSet.getString(2));
                //System.out.println(resultSet.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeConnection();
        return companies;
    }

    public void createTables() {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE " +
                            "IF NOT EXISTS " +
                            "COMPANY(id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                            "name VARCHAR NOT NULL UNIQUE )");
            statement.executeUpdate(
                    "CREATE TABLE " +
                            "IF NOT EXISTS " +
                            "CAR (id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                            "name VARCHAR NOT NULL UNIQUE," +
                            "company_id INT NOT NULL, " +
                            "foreign key (company_id) references COMPANY(ID))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public void addCar(String companyName, String carName) {
        try {
            int companyId = getCompanyIdByName(companyName);
            getConnection().createStatement().execute("INSERT INTO CAR (name, company_id) " +
                    "VALUES ('" +
                    carName +
                    "', " +
                    companyId +
                    ");");
            closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private int getCompanyIdByName(String companyName) {
        int companyId = 0;
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery("select id from COMPANY " +
                    "where name = '" +
                    companyName +
                    "';");
            if (resultSet.next()) {
                companyId = resultSet.getInt("id");
            }
            closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return companyId;
    }

    public List<String> getCompanyCars(String companyName) {
        ResultSet resultSet = null;
        List<String> cars = new ArrayList<>();
        try {
            resultSet = getConnection().createStatement().executeQuery("select c.id, c.name from " +
                    "COMPANY AS CM INNER JOIN CAR AS C " +
                    "ON CM.ID = C.COMPANY_ID " +
                    "WHERE CM.NAME = '" +
                    companyName +
                    "' " +
                    "ORDER BY C.ID");

            while(resultSet.next()) {
                cars.add(resultSet.getString(2));
                //System.out.println(resultSet.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeConnection();
        return cars;
    }
}
