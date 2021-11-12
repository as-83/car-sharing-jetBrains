package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUtil {
    private static Connection connection = null;
    private static String databaseFilePath = "./src/carsharing/db/";//jdbc:h2:./src/carsharing/db/carsharing
    private static String databaseFileName = "carsharing";//jdbc:h2:./src/carsharing/db/carsharing
    private static List<String> cars = new ArrayList<>();

    public static void setDatabaseFileName(String databaseFileName) {
        DbUtil.databaseFileName = databaseFileName;
    }

    public static Connection getConnection() {
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

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            connection = null;
        }
    }

    public static void addCompany(String companyTitle) {
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

    public static List<String> getAllCompanies() {
        return getAllEntitiesNames("COMPANY ");
    }

    public static void createTables() {
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

            statement.executeUpdate(
                    "CREATE TABLE " +
                            "IF NOT EXISTS " +
                            "CUSTOMERR (id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                            "name VARCHAR NOT NULL UNIQUE," +
                            "RENTED_CAR_ID INT, " +
                            "foreign key (RENTED_CAR_ID) references CAR(ID))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public static void addCar(String companyName, String carName) {
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

    private static int getCompanyIdByName(String companyName) {
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

    public static List<String> getCompanyCars(String companyName) {
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

    public static boolean rentCar(String currentCustomer, String companyName, String carName) {
        int rentedCarId = getCarId(carName, companyName);

        try {
            getConnection().createStatement().execute("UPDATE CUSTOMER " +
                    "SET RENTED_CAR_ID = " +
                    rentedCarId +
                    " WHERE  NAME = '" +
                    currentCustomer +
                    "';");
            closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;

    }

    private static int getCarId(String carName, String companyName) {
        int carId = 0;
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery("select c.id from Car c" +
                    " inner join company cm " +
                    "on CM.ID = C.COMPANY_ID" +
                    "where cm.name = '" +
                    companyName +
                    "' " +
                    "and c.name = '" +
                    carName +
                    "';");
            if (resultSet.next()) {
                carId = resultSet.getInt(1);
            }
            closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return carId;
    }

    public static List<String> getAllCustomers() {
        return getAllEntitiesNames("car ");
    }

    public static List<String> getAllEntitiesNames(String tableName) {
        String query = "select * from " + tableName + "ORDER BY ID";
        ResultSet resultSet = null;
        List<String> companies = new ArrayList<>();
        try {
            resultSet = getConnection().createStatement().executeQuery(query);

            while(resultSet.next()) {
                companies.add(resultSet.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeConnection();
        return companies;
    }

    public static void addCustomer(String name) {
        try {
            getConnection().createStatement().execute("INSERT INTO CUSTOMER (name) " +
                    "VALUES ('" +
                    name +
                    "');");
            closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String[] getRentedCar(String currentCustomer) {
        String[] companyAndCar = new String[2];
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery("select cm.name, c.name from Car c" +
                    " inner join company cm " +
                    "on CM.ID = C.COMPANY_ID" +
                    " inner join customer cu " +
                    "on cu.rented_car_id = C.ID " +
                    "where cu.name = '" +
                    currentCustomer +
                    "';");
            if (resultSet.next()) {
                companyAndCar[0] = resultSet.getString(1);
                companyAndCar[2] = resultSet.getString(2);
            }
            closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return companyAndCar;
    }

    public static boolean returnCar(String currentCustomer) {
        try {
            /*ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT RENRTED_CAR_ID FROM CUSTOMERS " +
                    " WHERE  NAME = '" +
                    currentCustomer +
                    "';");*/
            closeConnection();
            boolean returned = getConnection().createStatement().execute("UPDATE CUSTOMER " +
                    "SET RENTED_CAR_ID = NULL" +
                    " WHERE  NAME = '" +
                    currentCustomer +
                    "' " +
                    "AND RENTED_CAR_ID = 'NULL';");
            closeConnection();
            return returned;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
