package carsharing.terminal.states;

import carsharing.DbUtil;
import carsharing.terminal.TerminalContext;

public class CustomerState implements State {

    public CustomerState(String customer) {

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
            case 1: TerminalContext.getInstance().setTerminalState(CompaniesListState.getInstance()); break;
            case 2: tryToReturnCar(); break;
            case 3: getRentedCar(); break;
            case 0: TerminalContext.getInstance().setTerminalState(MainMenuState.getInstance()); break;
            default: break;

        }
    }

    private void tryToReturnCar() {
        if (DbUtil.returnCar(TerminalContext.getInstance().getCurrentCustomer())) {
            System.out.println("You've returned a rented car!");
        } else {
            System.out.println("You didn't rent a car!");
        }
    }


    private void getRentedCar() {
        String[] rentedCarAndCompany = DbUtil.getRentedCar(TerminalContext.getInstance().getCurrentCustomer());
        if (!rentedCarAndCompany[0].isEmpty()) {
            System.out.println(
                    "Your rented car:\n" +
                    rentedCarAndCompany[1] +
                    "\nCompany:\n" +
                    rentedCarAndCompany[0]);
        } else {
            System.out.println("You didn't rent a car!");
        }
    }
}
