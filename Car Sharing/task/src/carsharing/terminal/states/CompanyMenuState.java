package carsharing.terminal.states;

import carsharing.DbUtil;
import carsharing.terminal.TerminalContext;

import java.util.List;
import java.util.Scanner;

public class CompanyMenuState implements State {
    private static String currentCompany;


    public CompanyMenuState(String companyName) {
        currentCompany = companyName;
    }

    @Override
    public boolean printMenu() {
        System.out.println("'" + currentCompany + "' company");
        System.out.println(
                "1. Car list\n" +
                "2. Create a car\n" +
                "0. Back");
        return true;
    }

    @Override
    public void doAction(int actionType) {
        switch (actionType) {
            case 1: TerminalContext.getInstance().setTerminalState(new CarListState(currentCompany)); break;

            case 2:  createCar(); break;

            case 0: TerminalContext.getInstance().setTerminalState(CompaniesListState.getInstance()); break;

        }
    }

    private void createCar() {
        System.out.println("Enter the car name:");
        String carName = new Scanner(System.in).nextLine();
        DbUtil.addCar(currentCompany, carName);
        System.out.println("The car was added!");
    }


}


