package carsharing;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static DbUtil db = new DbUtil();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length != 0 && args[0].equals("-databaseFileName")) {
            db.setDatabaseFileName(args[1]);
        }
        db.createTables();

        int mainMenuSelection = 1;
        while( mainMenuSelection != 0) {
            printMainMenu();
            mainMenuSelection = scanner.nextInt();
            scanner.nextLine();
            if (mainMenuSelection == 1) {
                int managerOptionsSelection = 1;
                while(managerOptionsSelection != 0) {
                    printManagerOptions();
                    managerOptionsSelection = scanner.nextInt();
                    scanner.nextLine();
                    if (managerOptionsSelection == 1) {
                        printCompanies();
                    } else if (managerOptionsSelection == 2) {
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
        if (companies.size() != 0) {
            int[] number = {1};
            System.out.println("Choose the company:");
            companies.forEach(c -> System.out.println(number[0]++ + ". " + c));
            System.out.println("0. Back");
            int companyNumber = scanner.nextInt();
            scanner.nextLine();
            if (companyNumber != 0) {
                System.out.println("'" + companies.get(companyNumber - 1) + "' company");
                printCompanyMenu();
                selectInCompanyMenu(companies.get(companyNumber - 1));
            }
        } else {
            System.out.println("The company list is empty!");
        }
    }

    private static void createCar(String companyName) {
        System.out.println("Enter the car name:");
        String carName = scanner.nextLine();
        db.addCar(companyName, carName);
        System.out.println("The car was added!");
        printCompanyMenu();
        selectInCompanyMenu(companyName);

    }

    private static void printCarList(String companyName) {
        List<String> cars = db.getCompanyCars(companyName);
        if (cars.size() != 0) {
            int[] number = {1};
            System.out.println("Car list:");
            cars.forEach(c -> System.out.println(number[0]++ + ". " + c));

        } else {
            System.out.println("The car list is empty!");
        }
        printCompanyMenu();
        selectInCompanyMenu(companyName);
    }

    private static void printCompanyMenu() {
        System.out.println(
                "1. Car list\n" +
                "2. Create a car\n" +
                "0. Back");
    }

    private static void selectInCompanyMenu(String companyName) {
        int companyMenuSelection = scanner.nextInt();
        scanner.nextLine();
        if (companyMenuSelection == 1) {
            printCarList(companyName);
        } else if (companyMenuSelection == 2) {
            createCar(companyName);
        }
    }

    private static void printManagerOptions() {
        System.out.println(
                "1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");
    }

    private static void printMainMenu() {
        System.out.println(
                "1. Log in as a manager\n" +
                "0. Exit"
        );
    }




}
