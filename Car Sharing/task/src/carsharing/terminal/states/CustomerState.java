package carsharing.terminal.states;

import carsharing.DbUtil;
import carsharing.terminal.TerminalContext;

public class CustomerState implements State {
    String currentCustomer;
    public CustomerState(String customer) {
        currentCustomer = customer;
    }

    @Override
    public boolean printMenu() {
        System.out.println(
                "1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back"
        );
        return true;
    }

    @Override
    public void doAction(int actionType) {
        switch (actionType) {
            case 1:
                CompaniesListState companiesListState = CompaniesListState.getInstance();
                companiesListState.setCustomer(currentCustomer);
                TerminalContext.getInstance().setTerminalState(companiesListState); break;
            case 2: tryToReturnCar(); break;
            case 3: getRentedCar(); break;
            case 0: TerminalContext.getInstance().setTerminalState(MainMenuState.getInstance()); break;
            default: break;

        }
    }

    private void tryToReturnCar() {
        if (DbUtil.returnCar(currentCustomer)) {
            System.out.println("You've returned a rented car!");
        } else {
            System.out.println("You didn't rent a car!");
        }
    }


    private void getRentedCar() {
        String rentedCar = DbUtil.getRentedCar(currentCustomer);
        String rentedCarCompany = DbUtil.getRentedCarCompany(currentCustomer);
        if (rentedCar != null) {
            System.out.println(
                    "Your rented car:\n" +
                    rentedCar +
                    "\nCompany:\n" +
                    rentedCarCompany);
        } else {
            System.out.println("You didn't rent a car!");
        }
    }
}
