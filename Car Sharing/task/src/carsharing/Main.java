package carsharing;

import org.w3c.dom.ls.LSOutput;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static DbUtil db = new DbUtil();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String databaseFileName = "carsharing";
        if (args.length != 0 && args[0].equals("-databaseFileName")) {
            databaseFileName = args[1];
        }
        Connection connection = db.getConnection(databaseFileName);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE " +
                    //"IF NOT EXISTS " +
                    "COMPANY(id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR NOT NULL UNIQUE )");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
        int mainMenuSelection = 1;
        while( mainMenuSelection != 0) {
            printMainMenu();
            mainMenuSelection = scanner.nextInt();
            scanner.nextLine();
            if (mainMenuSelection == 1) {
                int optionsSelection = 1;
                while(optionsSelection != 0) {
                    printOptions();
                    optionsSelection = scanner.nextInt();
                    scanner.nextLine();
                    if (optionsSelection == 1) {
                        printCompanies();
                    } else if (optionsSelection == 2) {
                        createCompany();
                    }
                }


            }
        }


    }

    private static void createCompany() {
        System.out.println("Enter the company name:");
        String companyTitle = scanner.nextLine();
        db.addCompany(companyTitle);
        System.out.println("The company was created!");

    }

    private static void printCompanies() {
        List<String> companies = db.getAllCompanies();
        companies.forEach(c -> System.out.println(c));
    }

    private static void printOptions() {
        System.out.println(
                "1. Company list\n" +
                "2. Create a company\n" +
                "0. Back"
        );
    }

    private static void printMainMenu() {
        System.out.println(
                "1. Log in as a manager\n" +
                "0. Exit"
        );
    }




}
