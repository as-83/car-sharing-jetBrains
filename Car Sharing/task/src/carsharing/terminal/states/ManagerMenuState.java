package carsharing.terminal.states;

import carsharing.DbUtil;
import carsharing.terminal.TerminalContext;

import java.util.Scanner;

public class ManagerMenuState implements State {
    private TerminalContext terminal = TerminalContext.getInstance();

    private static ManagerMenuState managerMenuState = new ManagerMenuState();
    private ManagerMenuState(){

    }

    public static ManagerMenuState getInstance(){
        if (managerMenuState == null) {
            managerMenuState = new ManagerMenuState();
        }
        return managerMenuState;
    }


    @Override
    public boolean printMenu() {
        System.out.println(
                "1. Company list\n" +
                "2. Create a company\n" +
                "0. Back"
        );
        return true;
    }

    @Override
    public void doAction(int actionType) {

        switch (actionType) {
            case 1: terminal.setTerminalState(CompaniesListState.getInstance()); break;

            case 2:  createCompany(); break;
            case 0: terminal.setTerminalState(MainMenuState.getInstance()); break;

        }

    }

    private void createCompany() {
        System.out.println("Enter the company name:");
        String companyTitle = new Scanner(System.in).nextLine();
        DbUtil.addCompany(companyTitle);
        System.out.println("The company was created!");
    }
}
