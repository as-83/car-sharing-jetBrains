package carsharing.terminal.states;

import carsharing.terminal.TerminalContext;

public class CreateCustomerMenuState implements State {
    private TerminalContext terminal = TerminalContext.getInstance();


    private static CreateCustomerMenuState customerMenuState = new CreateCustomerMenuState();
    private CreateCustomerMenuState(){

    }

    public static CreateCustomerMenuState getInstance(){
        if (customerMenuState == null) {
            customerMenuState = new CreateCustomerMenuState();
        }
        return customerMenuState;
    }

    @Override
    public boolean printMenu() {
        System.out.println(
                "Customer list:\n" +
                "1. First customer\n" +
                "2. Second customer\n" +
                "0. Back"
        );
        return true;
    }

    @Override
    public void doAction(int actionType) {

        switch (actionType) {
            case 1: System.out.println("First customer menu");; break;
            case 2: System.out.println("Second customer menu");; break;
            case 0: terminal.setTerminalState(MainMenuState.getInstance()); break;


        }

    }
}
